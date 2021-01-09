package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping
    public String createOrUpdateCredential(Credential credential, Authentication authentication, Model model) {
        User user = userService.getUserByUsername(authentication.getName());
        if (user == null) {
            model.addAttribute("isSuccess", false);
            model.addAttribute("errorMessage", "You are not logged in");
        } else  {
            credential.setUserId(user.getUserid());
            int result = credentialService.saveCredential(credential);
            model.addAttribute("isSuccess", result > 0);
            model.addAttribute("errorMessage", "Could not save credential. Something went wrong");
        }

        return "result";
    }

    @GetMapping("/{id}/delete")
    public String deleteCredential(@PathVariable int id, Model model) {
        int count = credentialService.deleteCredential(id);
        model.addAttribute("isSuccess", count > 0);
        model.addAttribute("errorMessage", "Could not delete credential. Something went wrong");
        return "result";
    }
}
