package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings({"unused"})
public class IndexPage extends Page {
    private final ArticleService articleService = new ArticleService();
    private final UserService userService = new UserService();

    @Json
    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("articles", articleService.findAllVisible());
        view.put("logins", userService.findAll().stream().collect(Collectors.toMap(User::getId, User::getLogin)));
    }
}
