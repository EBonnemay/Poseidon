package com.nnk.springboot.controllers;


import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
/**
 * This class is a controller dedicated to login page, displaying this page when /login GET request is done by user.
 *
 * @author Emmanuelle Bonnemay
 * created on 23/04/2023
 *
 */
@Controller
@RequestMapping("app")
public class LoginController {

    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");

    private UserService userService;


    private UserRepository userRepository;

    public LoginController(UserService userService, UserRepository userRepository){
        this.userService=userService;
        this.userRepository=userRepository;
    }
    @GetMapping("/login")
    public ModelAndView login() {
        log.info("GET /login");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }


    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        log.info("GET secure/article-details");
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }
}
