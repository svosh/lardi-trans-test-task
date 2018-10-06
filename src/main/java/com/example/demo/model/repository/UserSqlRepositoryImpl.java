package com.example.demo.model.repository;

import com.example.demo.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.Optional;

@Repository
public class UserSqlRepositoryImpl extends SimpleJpaRepository<User, Long> implements SimpleJPAUserRepository{

    private EntityManager em;

    @Autowired
    public UserSqlRepositoryImpl(Class<User> domainUserClass, EntityManager em) {
        super(domainUserClass, em);
        this.em = em;
    }

    @Override
    public User saveAndFlush(User user) {
        return super.saveAndFlush(user);
    }

    @Override
    public Optional<User> findById(long id) {
        return super.findById(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return Optional.of(em.createQuery(
                "SELECT u FROM User u WHERE u.login LIKE :userLogin", User.class)
                .setParameter("userLogin", login).getSingleResult());

    }

    @Override
    public Optional<User> findByName(String name) {
        return Optional.of(em.createQuery(
                "SELECT u FROM User u WHERE u.name LIKE :userName", User.class)
                .setParameter("userName", name).getSingleResult());
    }

    @Override
    public boolean existsByLogin(String login) {
        return em.createQuery(
                "SELECT u FROM User u WHERE u.login LIKE :userLogin", User.class)
                .setParameter("userLogin", login).getMaxResults() >0;
    }
}