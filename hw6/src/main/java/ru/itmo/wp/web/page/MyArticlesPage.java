package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyArticlesPage extends Page {
    private final ArticleService articleService = new ArticleService();

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        User user = requireUser();
        view.put("articles", articleService.findByUserId(user.getId()));
    }

    @Json
    private void updateHidden(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = requireUser();
        requirePost();
        boolean hidden = articleService.updateHidden(user.getId(), request.getParameter("id"),
                request.getParameter("hidden"));
        view.put("hidden", hidden);
    }
}
