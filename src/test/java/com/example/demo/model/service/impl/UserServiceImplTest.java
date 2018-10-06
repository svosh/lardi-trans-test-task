package com.example.demo.model.service.impl;

import com.example.demo.model.entity.PhoneBookRow;
import com.example.demo.model.entity.User;
import com.example.demo.model.repository.SimpleJPAUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private SimpleJPAUserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    private PhoneBookRow phoneBookRow1;

    private long RIGHT_ID = 1;

    private static final String RIGHT_LOGIN = "login1";

    private static final String RIGHT_NAME = "name1";

    @Before
    public void setUp() {

        user = new User(RIGHT_ID,RIGHT_LOGIN,"password1",RIGHT_NAME, new ArrayList<>());

        phoneBookRow1 = new PhoneBookRow(0,
                user,
                "firstName1",
                "lastName1",
                "patronymic1",
                "email@email.em",
                "address1",
                "mobilePhoneNumber1",
                "homePhoneNumber1");

        when(userRepository.findByLogin(RIGHT_LOGIN)).thenReturn(Optional.ofNullable(user));
        when(userRepository.findByName(RIGHT_NAME)).thenReturn(Optional.ofNullable(user));
    }

    @Test
    public void whenSaveOrUpdateInvoked() {
        userService.saveOrUpdate(user);

        verify(userRepository).saveAndFlush(user);
    }

    @Test
    public void whenGetAllInvoked() {
        userService.getAll();

        verify(userRepository).findAll();
    }

    @Test
    public void whenGetByIdInvoked() {
        userService.getById(RIGHT_ID);

        verify(userRepository).findById(RIGHT_ID);
    }

    @Test
    public void whenFindByLoginInvoked() {
        userService.findByLogin(RIGHT_LOGIN);

        verify(userRepository).findByLogin(RIGHT_LOGIN);
    }

    @Test
    public void whenFindByNameInvoked() {
        userService.findByName(RIGHT_NAME);

        verify(userRepository).findByName(RIGHT_NAME);
    }

    @Test
    public void whenAddUserAdditionalFieldByUserInvokedForExistingUser() {
        when(userRepository.saveAndFlush(user)).thenReturn(user);

        userService.addUserPhoneBookRow(user, phoneBookRow1);

        verify(userRepository).saveAndFlush(user);

    }

    @Test
    public void whenDeleteUserByLoginInvokedForExistingUser() {

        userService.delete(user.getLogin());

        verify(userRepository).findByLogin(user.getLogin());
        verify(userRepository).delete(user);
    }

    @Test
    public void whenDeleteUserByLoginInvokedForNonExistingUser() {
        when(userRepository.findByLogin(user.getLogin())).thenReturn(Optional.empty());

        userService.delete(user.getLogin());

        verify(userRepository).findByLogin(user.getLogin());
    }

    @Test
    public void whenIsExistsByLoginInvoked() {
        userService.isExists(user.getLogin());

        verify(userRepository).existsByLogin(user.getLogin());
    }
}