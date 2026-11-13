package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void validateArticle(Article article) throws ValidationException {
        if (article.getTitle() == null || article.getTitle().trim().isEmpty()) {
            throw new ValidationException("Title is required");
        }
        if (article.getTitle().length() > 255) {
            throw new ValidationException("Title can't be longer than 255 characters");
        }
        if (article.getText() == null || article.getText().trim().isEmpty()) {
            throw new ValidationException("Text is required");
        }
        if (article.getText().length() > 10000) {
            throw new ValidationException("Text can't be longer than 10000 characters");
        }
    }

    public void save(Article article) {
        articleRepository.save(article);
    }

    public List<Article> findAllVisible() {
        return articleRepository.findAllVisible();
    }

    public List<Article> findByUser(User user) {
        return articleRepository.findByUserId(user.getId());
    }

    public void setHidden(User user, long articleId, boolean hidden) throws ValidationException {
        Article article = articleRepository.find(articleId);
        if (article == null || article.getUserId() != user.getId()) {
            throw new ValidationException("Article not found");
        }
        articleRepository.updateHidden(articleId, hidden);
    }
}
