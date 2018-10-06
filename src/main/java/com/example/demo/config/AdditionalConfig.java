package com.example.demo.config;

import com.example.demo.model.entity.User;
import com.example.demo.model.repository.SimpleJPAUserRepository;
import com.example.demo.model.repository.UserJsonRepositoryImpl;
import com.example.demo.model.repository.UserSqlRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class AdditionalConfig {

    @Autowired
    EntityManager em;

    @Value("${dataStorageType}")
    String dataStorageType;

    @Value("${fileDataStoragePath}")
    String fileDataStoragePath;

    @Bean(name = "userRepository")
    public SimpleJPAUserRepository getRepository() {
        if (dataStorageType.equals("file"))
            return new UserJsonRepositoryImpl(getDataDirectory());
        else {
            return new UserSqlRepositoryImpl(getDomainUserClass(), em);
        }
    }

    @Bean(name = "domainUserClass")
    public Class<User> getDomainUserClass() {
        return User.class;
    }

    @Bean(name = "dataDirectory")
    public String getDataDirectory() {
        return fileDataStoragePath;
    }

}
