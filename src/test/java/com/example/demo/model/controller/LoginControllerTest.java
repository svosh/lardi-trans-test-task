package com.example.demo.model.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(secure = false)
public class LoginControllerTest {
    private static final String URL = "/login";

    @Autowired
    private MockMvc server;

    @Test
    public void successWhenAccessToLoginPage() throws Exception {
        server
                .perform(get(URL))
                .andExpect(status().isOk());
    }
}
