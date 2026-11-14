package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public abstract class Page {
    protected final UserService userService = new UserService();
    private HttpServletRequest request;

    public void before(HttpServletRequest request, Map<String, Object> view) {
        this.request = request;
        if (!Boolean.TRUE.equals(request.getAttribute("jsonResponse"))) {
            view.put("user", getUser());

            HttpSession session = request.getSession(false);
            if (session != null) {
                Object message = session.getAttribute("message");
                view.put("message", message);
                session.removeAttribute("message");
            }
        }
    }

    public void after(HttpServletRequest request, Map<String, Object> view) {
        if (!Boolean.TRUE.equals(request.getAttribute("jsonResponse"))) {
            view.put("userCount", userService.findCount());
        }
    }

    protected void action(HttpServletRequest request, Map<String, Object> view) {
    }

    protected User getUser() {
        HttpSession session = request.getSession(false);
        return session == null ? null : (User) session.getAttribute("user");
    }

    protected User requireUser() {
        User user = getUser();
        if (user == null) {
            throw new RedirectException("/index");
        }
        return user;
    }

    protected void requirePost() throws ValidationException {
        if (!"POST".equals(request.getMethod())) {
            throw new ValidationException("Use POST for this action");
        }
    }

    protected void setUser(User user) {
        request.getSession().setAttribute("user", user);
    }

    protected void setMessage(String message) {
        request.getSession().setAttribute("message", message);
    }
}
