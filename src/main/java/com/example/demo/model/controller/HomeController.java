package com.example.demo.model.controller;

import com.example.demo.model.entity.PhoneBookRow;
import com.example.demo.model.entity.User;
import com.example.demo.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
public class HomeController {

    private static final String HOME_PAGE = "home-page";

    private static final String NEW_CONTACT_PAGE = "new-contact-page";

    private static final String USER_ATTRIBUTE_NAME = "user";

    private static final String USER_NAME_ATTRIBUTE_NAME = "userName";

    private static final String PHONE_BOOK_ROW_ATTRIBUTE_NAME = "phoneBookRow";

    UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView getHome(HttpServletRequest request) {

        String userName = request.getParameter(USER_NAME_ATTRIBUTE_NAME);

        if (userName == null) {
            LoginController loginController = new LoginController();
            return loginController.getLogin();
        }
        User user = userService.findByLogin(userName).get();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(USER_ATTRIBUTE_NAME, user);
        modelAndView.setViewName(HOME_PAGE);
        return modelAndView;
    }

    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public ModelAndView doRegistration(@ModelAttribute(USER_ATTRIBUTE_NAME) @Valid User user,
                                       BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {

            modelAndView.addObject(USER_ATTRIBUTE_NAME, user);
            modelAndView.setViewName(HOME_PAGE);
            return modelAndView;
        }

        setUserToBookPhoneRows(user);
        User registerNewUser = userService.saveOrUpdate(user);

        modelAndView.addObject(USER_ATTRIBUTE_NAME, registerNewUser);
        modelAndView.setViewName(HOME_PAGE);
        return modelAndView;
    }

    @RequestMapping(value = "/home/add-new-contact", method = RequestMethod.GET)
    public ModelAndView addNewContact(HttpServletRequest request) {

        String userName = request.getParameter(USER_NAME_ATTRIBUTE_NAME);
        PhoneBookRow phoneBookRow = new PhoneBookRow();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(PHONE_BOOK_ROW_ATTRIBUTE_NAME, phoneBookRow);
        modelAndView.addObject(USER_NAME_ATTRIBUTE_NAME, userName);
        modelAndView.setViewName(NEW_CONTACT_PAGE);
        return modelAndView;
    }

    @RequestMapping(value = "/home/add-new-contact", method = RequestMethod.POST)
    public ModelAndView addNewContact(@ModelAttribute(PHONE_BOOK_ROW_ATTRIBUTE_NAME) @Valid PhoneBookRow phoneBookRow,
                                      @ModelAttribute(USER_NAME_ATTRIBUTE_NAME) @Valid String userName,
                                      BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findByLogin(userName).get();

        if (bindingResult.hasErrors()) {
            modelAndView.addObject(USER_ATTRIBUTE_NAME, user);
            modelAndView.setViewName(NEW_CONTACT_PAGE);
            return modelAndView;
        }

        phoneBookRow.setUser(user);
        user.getUserPhoneBook().add(phoneBookRow);
        User registeredUser = userService.saveOrUpdate(user);

        modelAndView.addObject(USER_ATTRIBUTE_NAME, registeredUser);
        modelAndView.setViewName(HOME_PAGE);
        return modelAndView;
    }

    private void setUserToBookPhoneRows(User user) {
        List<PhoneBookRow> phoneBookRows = user.getUserPhoneBook();
        for(PhoneBookRow currentRow:phoneBookRows){
            currentRow.setUser(user);
        }
    }
}