/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nhn.controllers;

import com.nhn.pojo.User;
import com.nhn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Lightning
 */
@Controller
public class AdminAccount {

    @Autowired
    UserService userService;

    @RequestMapping("/admin/admin-account")
    public String index(Model model) {
        List<User> users = userService.getUsers("");
        model.addAttribute("users", users);

//        User firstUser = userService.getUserById(7);
//        model.addAttribute("firstUser", firstUser);

        return "admin-account";
    }

    @RequestMapping("/admin/admin-account/add-account")
    public String addAccount(Model model) {
        model.addAttribute("user", new User());
        return "add-account";
    }

    @PostMapping("/admin/admin-account/add-account")
    @Transactional
    public String addAccountPost(Model model, @ModelAttribute(value = "user") User user) {
        String errMsg = "";

        model.addAttribute("username", user.getUsername());
        model.addAttribute("password", user.getPassword());
        model.addAttribute("confirmPassword", user.getConfirmPassword());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("phone", user.getPhone());
        model.addAttribute("userType", user.getUserType());

        if (user.getPassword().equals(user.getConfirmPassword())) {
            errMsg = "Mat khau khop";
            this.userService.addUser(user);
            return "redirect:/admin/admin-account/add-account";
        } else
            errMsg = "Mat khau KHONG khop!";

        model.addAttribute("errMsg", errMsg);

        return "add-account";
    }

    @RequestMapping("/admin/admin-account/edit-account")
    public String editAccount(Model model) {
        model.addAttribute("user", new User());
        return "edit-account";
    }

    @RequestMapping("/admin/admin-account/edit-account/{account_id}")
    public String editAccountGet(Model model,
                              @PathVariable(value = "account_id") int accountId) {
        model.addAttribute("user", this.userService.getUserById(accountId));
//        model.addAttribute("user", new User());
        model.addAttribute("id", accountId);
        return "edit-account";
    }
}
