package com.example.demo.model.service.impl;

import com.example.demo.model.entity.PhoneBookRow;
import com.example.demo.model.entity.User;
import com.example.demo.model.repository.SimpleJPAUserRepository;
import com.example.demo.model.service.UserService;
import com.example.demo.security.dto.UserDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final SimpleJPAUserRepository repository;

    @Autowired
    public UserServiceImpl(SimpleJPAUserRepository userRepository) {this.repository = userRepository;}

    @Override
    public User saveOrUpdate(User user) {
        return repository.saveAndFlush(user);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<User> getById(long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return repository.findByLogin(login);
    }

    @Override
    public Optional<User> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Optional<User> addUserPhoneBookRow(User user, PhoneBookRow phoneBookRow) {
        if (user != null && phoneBookRow != null) {
            user.getUserPhoneBook().add(phoneBookRow);
            return Optional.of(repository.saveAndFlush(user));
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(String login) {
        Optional<User> optionalUser = findByLogin(login);
        if (optionalUser.isPresent()) {
            repository.delete(optionalUser.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean isExists(String login) { return repository.existsByLogin(login); }

    //security
    @Override
    public Optional<UserDetailDto> getUserDetailDto(User user) {

        Optional<User> userDB = repository.findByLogin(user.getLogin());
        Optional<UserDetailDto> result = Optional.empty();

        if (userDB.isPresent()){
            if(userDB.get().getPassword().equals(user.getPassword())){
                UserDetailDto userDetailDto = new UserDetailDto();
                userDetailDto.setEnabled(true);
                userDetailDto.setUsername(user.getLogin());
                userDetailDto.setPassword(user.getPassword());
                result = Optional.of(userDetailDto);
            }
        }
        return result;
    }
}