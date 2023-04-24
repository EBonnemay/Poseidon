package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;

import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

/**
 * This class is a controller dedicated to User entities ; it handles admin requests related to User :
 * displaying several pages (list, update form, add form), retrieving User data when necessary,
 * and handling crud requests by calling User Service.
 *
 * @author Emmanuelle Bonnemay
 * created on 23/04/2023
 *
 */
@Controller
public class UserController {
    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");
    //@Autowired

    private UserService userService;

    User user = new User();
    public UserController(UserService userService){
        this.userService=userService;

    }
    @RequestMapping("/user/list")
    public String homeDisplayUserList(Model model) {
        log.info("REQUEST /user/list");
        model.addAttribute("users", userService.findAllUsers());
        log.debug("attribute list of users added");
        return "user/list";
    }
    @GetMapping("/user/add")
    public String displayAddUserForm(User user) {
        log.debug("in addUser Display method");
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validateUser(@Valid User user, BindingResult result, Model model) {

        log.info("POST /user/validate for username " + user.getUsername());
        try {
            if (result.hasErrors()) {
                log.error("user to create has errors");
                throw (new Exception());
            }
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userService.validateNewUser(user);
        } catch (Exception e) {
            log.error("user could not be created");
            return "user/add";
        }
    return "redirect:/user/list";
    }

    @GetMapping("/user/update/{id}")
    public String displayUpdateUserForm(@PathVariable("id") Integer userId, Model model) {
        try{
            log.info("GET /user/update/{id} with id "+ userId);
            User user = userService.getById(userId);
            user.setPassword("");
            model.addAttribute("user", user);
            return "user/update";
        }catch(Exception e){
            log.error("user update form with id "+ userId +" could not be displayed");
            return "user/list";
        }

    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        log.info("POST /user/update/{id} with id "+ id );

        try{
            if (result.hasErrors()) {
                log.error("user to update has errors");
                throw new Exception();
            }
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));

            userService.updateUser(id, user);
            log.info ("user with id "+ user.getId() + " is saved" );
        }catch(Exception e){
            log.error("user with id "+id+" could not be update");
            return "redirect:/user/update/"+id+"";
        }
    return "redirect:/user/list";
    }


    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        try{
            log.info("GET /user/delete/{id}");
            userService.deleteUser(id);
            log.info("user deleted");
            return "redirect:/user/list";

        }catch(Exception e){
            log.error("user with id "+id+" could not be deleted");
            return "user/list";
        }
    }
}
