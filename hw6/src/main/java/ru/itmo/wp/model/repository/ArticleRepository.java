package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;

import java.util.List;

public interface ArticleRepository {
    void save(Article article);

    List<Article> findVisible();

    List<Article> findByUserId(long userId);

    boolean updateHidden(long userId, long articleId, boolean hidden);
}
