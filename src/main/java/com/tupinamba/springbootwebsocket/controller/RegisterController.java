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
import javax.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("message", "无效输入（用户名已存在）");
            return "register";
        }

        String registrationResult = userService.registerUser(user.getUsername(), user.getPassword(), user.getEmail());
        model.addAttribute("message",registrationResult);

        if ("Registration successful!".equals(registrationResult)) {
            return "redirect:/login"; // Redirect to login page on success
        } else if("Username already taken.".equals(registrationResult)){
            return "register"; // Return to register page on failure
        }
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User()); // 确保视图中有一个空的User对象
        return "register"; // 显示注册表单
    }
}