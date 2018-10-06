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
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
@AutoConfigureMockMvc(secure = false)
public class HomeControllerTest {
    private static final String URL = "/home";

    private static final long CORRECT_ID = 1;

    private static final String CORRECT_PASS = "pass123";

    private static final String RIGHT_LOGIN = "login";

    private static final String CORRECT_NAME = "nameName";

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

        when(userService.findByLogin(RIGHT_LOGIN)).thenReturn(Optional.of(user));
        when(userService.saveOrUpdate(user)).thenReturn(user);
    }

    @Test
    public void successWhenAccessToHomePage() throws Exception {
        server
                .perform(get(URL + "?userName=" + RIGHT_LOGIN))
                .andExpect(status().isOk());
    }

    @Test
    public void successWhenSaveOrUpdateUserFromHomePage() throws Exception {
        server
                .perform(post(URL)
                        .param("login", RIGHT_LOGIN)
                        .param("password", CORRECT_PASS)
                        .param("name", CORRECT_NAME))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void successWhenAccessToNewContactPage() throws Exception {
        server
                .perform(get(URL + "/add-new-contact?userName=" + RIGHT_LOGIN))
                .andExpect(status().isOk());
    }

    @Test
    public void successWhenSaveOrUpdateUserFromNewContactPage() throws Exception {
        server
                .perform(post(URL + "/add-new-contact")
                        .param("firstName", "firstName")
                        .param("lastName", "lastName")
                        .param("patronymic", "patronymic")
                        .param("email", "test@test.com")
                        .param("address", "address")
                        .param("mobilePhoneNumber", "+380(50)1231212")
                        .param("homePhoneNumber", "+380(50)1231212")
                        .param("userName", RIGHT_LOGIN))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
