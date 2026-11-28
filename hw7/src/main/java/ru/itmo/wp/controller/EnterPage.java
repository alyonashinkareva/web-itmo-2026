package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class EnterPage extends Page {
    private final UserService userService;

    public EnterPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/enter")
    public String enter(Model model) {
        model.addAttribute("enterForm", new UserCredentials());
        return "EnterPage";
    }

    @PostMapping("/enter")
    public String enter(@Valid @ModelAttribute("enterForm") UserCredentials enterForm,
                           BindingResult bindingResult,
                           HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "EnterPage";
        }

        User user = userService.findByLoginAndPassword(enterForm.getLogin(), enterForm.getPassword());
        if (user == null) {
            bindingResult.rejectValue("password", "password.invalid", "Invalid login or password");
            return "EnterPage";
        }
        if (user.isDisabled()) {
            bindingResult.rejectValue("password", "user.disabled", "User is disabled");
            return "EnterPage";
        }

        setUser(httpSession, user);
        setMessage(httpSession, "Hello, " + user.getLogin());
        return "redirect:/";
    }
}
