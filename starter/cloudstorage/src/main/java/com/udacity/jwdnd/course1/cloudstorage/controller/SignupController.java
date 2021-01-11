package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupView(Model model){
        model.addAttribute("showError", false);
        model.addAttribute("signupSuccessful", false);
        return "signup";
    }

    @PostMapping
    public String registerUser(@ModelAttribute User user, Model model){
        //String resultPage;
        if (userService.isExistingUser(user.getUsername())) {
            model.addAttribute("showError", true);
            model.addAttribute("errorMessage", "User already exists");
            //resultPage = "signup";
        } else  {
            Integer id = userService.createUser(user);
            model.addAttribute("signupSuccessful", id > 0);
            model.addAttribute("showError", id < 0);
            model.addAttribute("errorMessage", "Signup failed. Please retry.");
            //resultPage = id > 0 ? "login" : "signup";
        }
        return "signup";
    }
}
