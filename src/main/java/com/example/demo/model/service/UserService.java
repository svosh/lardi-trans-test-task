package com.example.demo.model.service;

import com.example.demo.model.entity.PhoneBookRow;
import com.example.demo.model.entity.User;
import com.example.demo.security.dto.UserDetailDto;

import java.util.List;
import java.util.Optional;

public interface UserService{

    User saveOrUpdate(User user);

    List<User> getAll();

    Optional<User> getById(long id);

    Optional<User> findByLogin(String login);

    Optional<User> findByName(String name);

    Optional<User> addUserPhoneBookRow(User user, PhoneBookRow phoneBookRow);

    boolean delete(String login);

    boolean isExists(String login);

    // security
    Optional<UserDetailDto> getUserDetailDto(User user);
}