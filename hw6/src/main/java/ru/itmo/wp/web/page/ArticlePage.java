package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ArticlePage extends Page {
    private final ArticleService articleService = new ArticleService();

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        requireUser();
    }

    @Json
    private void create(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = requireUser();
        requirePost();
        articleService.create(user.getId(), request.getParameter("title"), request.getParameter("text"));
        throw new RedirectException("/index");
    }
}
