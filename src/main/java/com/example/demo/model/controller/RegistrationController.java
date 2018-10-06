package com.example.demo.model.controller;

import com.example.demo.model.entity.User;
import com.example.demo.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    private static final String LOGIN_PAGE = "login-page";

    private static final String OPERATION_MODE_ATTRIBUTE_NAME = "operationMode";

    private static final String OPERATION_MODE_LOGIN = "login";

    private static final String OPERATION_MODE_REGISTRATION = "registration";

    private static final String USER_ATTRIBUTE_NAME = "User";

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView getRegistration() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(USER_ATTRIBUTE_NAME, new User());
        modelAndView.addObject(OPERATION_MODE_ATTRIBUTE_NAME, OPERATION_MODE_REGISTRATION);
        modelAndView.setViewName(LOGIN_PAGE);
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView doRegistration(Model model,
                    @ModelAttribute(USER_ATTRIBUTE_NAME)
                    @Valid
                    User userRegister,
                    BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {

            modelAndView.addObject(USER_ATTRIBUTE_NAME, userRegister);
            modelAndView.addObject(OPERATION_MODE_ATTRIBUTE_NAME, OPERATION_MODE_REGISTRATION);
            modelAndView.setViewName(LOGIN_PAGE);
            return modelAndView;
        }
        User registerNewUserOptional = userService.saveOrUpdate(userRegister);

        if (registerNewUserOptional == null) {
            modelAndView.addObject(OPERATION_MODE_ATTRIBUTE_NAME, OPERATION_MODE_LOGIN);
            modelAndView.setViewName(LOGIN_PAGE);
            return modelAndView;
        }

        modelAndView.addObject(OPERATION_MODE_ATTRIBUTE_NAME, OPERATION_MODE_LOGIN);
        modelAndView.setViewName(LOGIN_PAGE);
        return modelAndView;
    }
}