package com.example.demo.model.controller;

import com.example.demo.model.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginController {

    private static final String LOGIN_PAGE = "login-page";

    private static final String OPERATION_MODE_ATTRIBUTE_NAME = "operationMode";

    private static final String OPERATION_MODE_LOGIN = "login";

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLogin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("User", new User());
        modelAndView.addObject(OPERATION_MODE_ATTRIBUTE_NAME, OPERATION_MODE_LOGIN);
        modelAndView.setViewName(LOGIN_PAGE);
        return modelAndView;
    }
}