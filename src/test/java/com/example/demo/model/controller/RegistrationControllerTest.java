package com.example.demo.model.controller;

import com.example.demo.model.entity.User;
import com.example.demo.model.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationController.class)
@AutoConfigureMockMvc(secure = false)
public class RegistrationControllerTest {
    private static final String URL = "/registration";

    private static final long CORRECT_ID = 1;

    private static final String CORRECT_PASS = "pass123";

    private static final String RIGHT_LOGIN = "login";

    private static final String WRONG_LOGIN = "loginыаываыва121";

    private static final String CORRECT_NAME = "name";

    private User user;

    @Autowired
    private MockMvc server;

    @MockBean
    private UserService userService;

    @Before
    public void setUp() {
        user = new User();
        user.setId(CORRECT_ID);
        user.setName(CORRECT_NAME);
        user.setLogin(RIGHT_LOGIN);
        user.setPassword(CORRECT_PASS);
        user.setUserPhoneBook(new ArrayList<>());

        when(userService.saveOrUpdate(user)).thenReturn(user);
    }

    @Test
    public void successWhenAccessToRegistrationPage() throws Exception {
        server
                .perform(get(URL))
                .andExpect(status().isOk());
    }

    @Test
    public void successWhenSaveOrUpdateUserWithRightLogin() throws Exception {
        server
                .perform(post(URL)
                        .param("login", RIGHT_LOGIN)
                        .param("password", CORRECT_PASS)
                        .param("name", CORRECT_NAME))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void successWhenSaveOrUpdateUserWithWrongLogin() throws Exception {
        server
                .perform(post(URL)
                        .param("login", WRONG_LOGIN)
                        .param("password", CORRECT_PASS)
                        .param("name", CORRECT_NAME))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
