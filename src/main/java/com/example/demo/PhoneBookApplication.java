package com.example.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.io.File;

@SpringBootApplication
public class PhoneBookApplication {

    public static void main(String[] args) {
        String prop = System.getProperty("lardi.conf");
        SpringApplicationBuilder builder = new SpringApplicationBuilder(PhoneBookApplication.class);

        if (prop != null && !prop.equals("") && new File(prop).exists()) {
            builder.properties("spring.config.location=" + prop);
        }
        builder.run(args);
    }
}
