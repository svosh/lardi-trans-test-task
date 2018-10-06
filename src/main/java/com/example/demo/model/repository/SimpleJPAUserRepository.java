package com.example.demo.model.repository;

import com.example.demo.model.entity.User;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface SimpleJPAUserRepository {

    public User saveAndFlush(User user);

    public List<User> findAll();

    public Optional<User> findById(long id);

    public Optional<User> findByLogin(String login);

    public Optional<User> findByName(String name);

    public void delete(User user);

    public boolean existsByLogin(String login);

}