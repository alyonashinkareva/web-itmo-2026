package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class UsersPage extends Page {
    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        User user = getUser();
        User currentUser = user == null ? null : userService.find(user.getId());
        view.put("showAdmin", currentUser != null && currentUser.isAdmin());
        view.put("users", userService.findAll());
    }

    @Json
    private void updateAdmin(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = requireUser();
        requirePost();
        boolean admin = userService.updateAdmin(user.getId(), request.getParameter("id"), request.getParameter("admin"));
        view.put("admin", admin);
    }
}
