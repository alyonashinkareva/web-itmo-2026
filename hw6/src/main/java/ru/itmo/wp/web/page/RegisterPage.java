package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class RegisterPage extends Page {
    @Json
    private void register(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        requirePost();

        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setEmail(request.getParameter("email"));
        userService.register(user, request.getParameter("password"), request.getParameter("passwordConfirmation"));

        setMessage("You are successfully registered!");
        throw new RedirectException("/index");
    }
}
