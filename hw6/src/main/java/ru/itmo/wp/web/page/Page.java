package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public abstract class Page {
    private final UserService userService = new UserService();

    private HttpServletRequest request;

    public void before(HttpServletRequest request, Map<String, Object> view) {
        this.request = request;

        User user = getUser();
        if (user != null) {
            view.put("user", user);
        }

        String message = popMessage();
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
        }

        view.put("userCount", userService.findCount());
    }

    public void after(HttpServletRequest request, Map<String, Object> view) {
    }

    protected User getUser() {
        return (User) request.getSession().getAttribute("user");
    }

    protected User requireUser() {
        User user = getUser();
        if (user == null) {
            throw new RedirectException("/index");
        }
        return user;
    }

    protected void setUser(User user) {
        request.getSession().setAttribute("user", user);
    }

    protected void removeUser() {
        request.getSession().removeAttribute("user");
    }

    protected void setMessage(String message) {
        request.getSession().setAttribute("message", message);
    }

    private String popMessage() {
        String message = (String) request.getSession().getAttribute("message");
        request.getSession().removeAttribute("message");
        return message;
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
    }
}
