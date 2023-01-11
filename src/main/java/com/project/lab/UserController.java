package com.project.lab;

import com.project.lab.models.Debt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {
    @Autowired
    BudgetUserDetailsService userDetailsService;

    @GetMapping("/user")
    public CustomUserDetails getUser(Authentication authentication) {
        return (CustomUserDetails) authentication.getPrincipal();
    }

    @GetMapping("/new-user")
    public String showNewDebtPage(Model model) {
        CustomUserDetails user = new CustomUserDetails();
        return "new-user";
    }

    @PostMapping(value = "/save-user")
    public String saveDebt(@ModelAttribute("user") CustomUserDetails user) {
        userDetailsService.createNewUser(user);
        return "redirect:/";
    }
}
