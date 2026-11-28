package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class UsersPage extends Page {
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }

    @PostMapping("/users/all")
    public String changeDisabled(
            @RequestParam("id") Long id,
            @RequestParam("disabled") boolean disabled,
            HttpSession httpSession) {

        User currentUser = getUser(httpSession);

        if (currentUser == null
                || !currentUser.isAdmin()
                || currentUser.isDisabled()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);}

        userService.setDisabled(id, disabled);

        return "redirect:/users/all";}
}