package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.ArticleRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepositoryImpl implements ArticleRepository {
    private final DataSource dataSource = DatabaseUtils.getDataSource();

    @Override
    public void save(Article article) {
        String sql = "INSERT INTO `Article` (userId, title, text, creationTime) VALUES (?, ?, ?, NOW())";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, article.getUserId());
            statement.setString(2, article.getTitle());
            statement.setString(3, article.getText());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Can't save article.", e);
        }
    }

    @Override
    public List<Article> findVisible() {
        String sql = "SELECT a.*, u.login AS authorLogin FROM `Article` a JOIN `User` u ON u.id = a.userId "
                + "WHERE a.hidden = FALSE ORDER BY a.creationTime DESC, a.id DESC";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            return findArticles(statement);
        } catch (SQLException e) {
            throw new RepositoryException("Can't find visible articles.", e);
        }
    }

    @Override
    public List<Article> findByUserId(long userId) {
        String sql = "SELECT a.*, u.login AS authorLogin FROM `Article` a JOIN `User` u ON u.id = a.userId "
                + "WHERE a.userId = ? ORDER BY a.creationTime, a.id";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            return findArticles(statement);
        } catch (SQLException e) {
            throw new RepositoryException("Can't find user's articles.", e);
        }
    }

    @Override
    public boolean updateHidden(long userId, long articleId, boolean hidden) {
        String sql = "UPDATE `Article` SET hidden = ? WHERE id = ? AND userId = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, hidden);
            statement.setLong(2, articleId);
            statement.setLong(3, userId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RepositoryException("Can't update article visibility.", e);
        }
    }

    private List<Article> findArticles(PreparedStatement statement) throws SQLException {
        List<Article> articles = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Article article = new Article();
                article.setId(resultSet.getLong("id"));
                article.setUserId(resultSet.getLong("userId"));
                article.setAuthorLogin(resultSet.getString("authorLogin"));
                article.setTitle(resultSet.getString("title"));
                article.setText(resultSet.getString("text"));
                article.setCreationTime(resultSet.getTimestamp("creationTime"));
                article.setHidden(resultSet.getBoolean("hidden"));
                articles.add(article);
            }
        }
        return articles;
    }
}
