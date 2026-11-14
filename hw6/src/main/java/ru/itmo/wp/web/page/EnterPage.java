package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class EnterPage extends Page {
    private final EventService eventService = new EventService();

    @Json
    private void enter(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        requirePost();

        User user = userService.enter(request.getParameter("loginOrEmail"), request.getParameter("password"));
        eventService.save(user.getId(), Event.Type.ENTER);
        setUser(user);
        setMessage("Hello, " + user.getLogin());
        throw new RedirectException("/index");
    }
}
