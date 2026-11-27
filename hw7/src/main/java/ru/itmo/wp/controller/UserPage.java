package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.service.UserService;

@Controller
public class UserPage extends Page {
    private final UserService userService;

    public UserPage(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public String user(@PathVariable String id, Model model){
        try {
            model.addAttribute("userProfile", userService.findById(Long.parseLong(id)));
        } catch (NumberFormatException e){
            model.addAttribute("userProfile", null);
        }
        return "UserPage";
    }
}
