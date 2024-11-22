package com.tupinamba.springbootwebsocket.controller;

import com.tupinamba.springbootwebsocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        if (userService.authenticate(username, password)) {
            // Successful login
            model.addAttribute("message", "Login successful!");
            return "redirect:/index.html"; // Redirect to welcome page
        } else {
            // Unsuccessful login
            model.addAttribute("message", "Invalid username or password.");
            return "login"; // Return to login page
        }
    }
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // 显示注册表单
    }
}