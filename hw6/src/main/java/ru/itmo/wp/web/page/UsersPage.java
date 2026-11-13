package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import com.google.common.primitives.Longs;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class UsersPage extends Page {
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
        view.put("isAdmin", userService.isAdmin(getUser()));
    }

    @Json
    private void setAdmin(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = requireUser();

        Long userId = Longs.tryParse(Strings.nullToEmpty(request.getParameter("userId")));
        if (userId == null) {
            throw new ValidationException("User not found");
        }
        boolean admin = Boolean.parseBoolean(request.getParameter("admin"));

        userService.setAdmin(user, userId, admin);
    }
}
