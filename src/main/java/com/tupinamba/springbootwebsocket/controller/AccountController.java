package com.tupinamba.springbootwebsocket.controller;

import com.tupinamba.springbootwebsocket.model.User;
import com.tupinamba.springbootwebsocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/account")
    public String showAccountPage(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "account";
    }

    @PostMapping("/updateAccount")
    public String updateAccount(@ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("message", "Validation errors occurred");
            return "account";
        }

        String updateResult = userService.updateUser(user);
        model.addAttribute("message", updateResult);
        return "account"; // 返回账户页面并显示更新结果
    }
}