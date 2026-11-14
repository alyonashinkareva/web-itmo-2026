package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TalksPage extends Page {
    private final TalkService talkService = new TalkService();

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        User user = requireUser();
        view.put("users", userService.findAll());
        view.put("talks", talkService.findByUserId(user.getId()));
    }

    private void send(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = requireUser();
        String targetUserId = request.getParameter("targetUserId");
        String text = request.getParameter("text");
        try {
            requirePost();
            talkService.send(user.getId(), targetUserId, text);
        } catch (ValidationException e) {
            action(request, view);
            view.put("targetUserId", targetUserId);
            view.put("text", text);
            throw e;
        }
        throw new RedirectException("/talks");
    }
}
