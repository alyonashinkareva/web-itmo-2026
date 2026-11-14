package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void create(long userId, String title, String text) throws ValidationException {
        if (title == null || title.isBlank()) {
            throw new ValidationException("Title is required");
        }
        if (title.length() > 255) {
            throw new ValidationException("Title can't be longer than 255 characters");
        }
        if (text == null || text.isBlank()) {
            throw new ValidationException("Text is required");
        }
        if (text.length() > 10000) {
            throw new ValidationException("Text can't be longer than 10000 characters");
        }

        Article article = new Article();
        article.setUserId(userId);
        article.setTitle(title.trim());
        article.setText(text);
        articleRepository.save(article);
    }

    public List<Article> findVisible() {
        return articleRepository.findVisible();
    }

    public List<Article> findByUserId(long userId) {
        return articleRepository.findByUserId(userId);
    }

    public boolean updateHidden(long userId, String articleId, String value) throws ValidationException {
        long id;
        try {
            id = Long.parseLong(articleId);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid article");
        }

        if (!"true".equals(value) && !"false".equals(value)) {
            throw new ValidationException("Invalid article visibility");
        }
        boolean hidden = Boolean.parseBoolean(value);
        if (!articleRepository.updateHidden(userId, id, hidden)) {
            throw new ValidationException("You can only change visibility of your own articles");
        }
        return hidden;
    }
}
