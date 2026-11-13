package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import com.google.common.primitives.Longs;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings({"unused"})
public class TalksPage extends Page {
    private final UserService userService = new UserService();
    private final TalkService talkService = new TalkService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        User user = requireUser();

        List<User> users = userService.findAll();
        view.put("users", users);
        view.put("usersById", users.stream().collect(
            Collectors.toMap(u -> String.valueOf(u.getId()), Function.identity())));
        view.put("talks", talkService.findByUser(user));
    }

    @Json
    private void send(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = requireUser();

        Long targetUserId = Longs.tryParse(Strings.nullToEmpty(request.getParameter("targetUserId")));
        if (targetUserId == null) {
            throw new ValidationException("Target user is required");
        }

        Talk talk = new Talk();
        talk.setSourceUserId(user.getId());
        talk.setTargetUserId(targetUserId);
        talk.setText(request.getParameter("text"));

        talkService.validateTalk(talk);
        talkService.save(talk);

        throw new RedirectException("/talks");
    }
}
