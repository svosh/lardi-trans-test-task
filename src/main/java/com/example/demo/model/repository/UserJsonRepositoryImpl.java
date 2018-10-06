package com.example.demo.model.repository;

import com.example.demo.model.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserJsonRepositoryImpl implements SimpleJPAUserRepository {

    private String dataDirectoryPath;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public UserJsonRepositoryImpl(String dataDirectory) {
        this.dataDirectoryPath = dataDirectory;
    }

    @Override
    public User saveAndFlush(User user) {

        if (user.getId() == 0L) {
            user.setId(getNewUserId());
        }
        try {
            objectMapper.writeValue(new File(dataDirectoryPath.concat(user.getLogin().concat(".json"))), user);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        File directory = new File(dataDirectoryPath);
        File[] files = directory.listFiles();
        List<User> users = new LinkedList<>();

        for (File file : files) {
            try {
                users.add(objectMapper.readValue(file, User.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return users;
    }

    @Override
    public Optional<User> findById(long id) {

        List<User> users = findAll();
        User userResult = null;

        for (User user : users) {
            if (user.getId() == id) {
                userResult = user;
                break;
            }
        }

        if (userResult == null) {
            return Optional.empty();
        } else {
            return Optional.of(userResult);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {

        File file = new File(dataDirectoryPath.concat(login).concat(".json"));
        if (file.exists()) {
            try {
                return Optional.of(objectMapper.readValue(file, User.class));
            } catch (IOException e) {
                e.printStackTrace();
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByName(String name) {

        List<User> users = findAll();
        User userResult = null;

        for (User user : users) {
            if (user.getName() == name) {
                userResult = user;
                break;
            }
        }

        if (userResult == null) {
            return Optional.empty();
        } else {
            return Optional.of(userResult);
        }
    }

    @Override
    public void delete(User user) {

        File file = new File(dataDirectoryPath.concat(user.getLogin()).concat(".json"));
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public boolean existsByLogin(String login) {
        File file = new File(dataDirectoryPath.concat(login).concat(".json"));
        return file.exists();
    }

    private Long getNewUserId() {
        File directory = new File(dataDirectoryPath);
        return directory.listFiles().length + 1L;
    }
}
