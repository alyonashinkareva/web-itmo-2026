package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import com.google.common.primitives.Longs;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class MyArticlesPage extends Page {
    private final ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        User user = requireUser();
        view.put("articles", articleService.findByUser(user));
    }

    @Json
    private void setHidden(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = requireUser();

        Long articleId = Longs.tryParse(Strings.nullToEmpty(request.getParameter("articleId")));
        if (articleId == null) {
            throw new ValidationException("Article not found");
        }
        boolean hidden = Boolean.parseBoolean(request.getParameter("hidden"));

        articleService.setHidden(user, articleId, hidden);
    }
}
