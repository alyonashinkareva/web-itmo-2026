package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public abstract class Page {
    private final UserService userService = new UserService();

    public void before(HttpServletRequest request, Map<String, Object> view) {
        User user = getUser(request);
        if (user != null) {
            view.put("user", user);
        }

        view.put("userCount", userService.findCount());

        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    public void after(HttpServletRequest request, Map<String, Object> view) {
    }

    protected void action(HttpServletRequest request, Map<String, Object> view) {
    }

    protected User getUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }

    protected void setUser(HttpServletRequest request, User user) {
        if (user == null) {
            request.getSession().removeAttribute("user");
        } else {
            request.getSession().setAttribute("user", user);
        }
    }

    protected void setMessage(HttpServletRequest request, String message) {
        request.getSession().setAttribute("message", message);
    }
}
