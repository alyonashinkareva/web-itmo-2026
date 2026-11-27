package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.TalkRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TalkRepositoryImpl implements TalkRepository {
    private final DataSource dataSource = DatabaseUtils.getDataSource();

    @Override
    public void save(Talk talk) {
        String sql = "INSERT INTO `Talk` (sourceUserId, targetUserId, text, creationTime) VALUES (?, ?, ?, NOW())";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, talk.getSourceUserId());
            statement.setLong(2, talk.getTargetUserId());
            statement.setString(3, talk.getText());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Can't save message.", e);
        }
    }

    @Override
    public List<Talk> findByUserId(long userId) {
        String sql = "SELECT t.*, sender.login AS sourceLogin, recipient.login AS targetLogin "
                + "FROM `Talk` t "
                + "JOIN `User` sender ON sender.id = t.sourceUserId "
                + "JOIN `User` recipient ON recipient.id = t.targetUserId "
                + "WHERE t.sourceUserId = ? OR t.targetUserId = ? "
                + "ORDER BY t.creationTime, t.id";

        List<Talk> talks = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            statement.setLong(2, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Talk talk = new Talk();
                    talk.setId(resultSet.getLong("id"));
                    talk.setSourceUserId(resultSet.getLong("sourceUserId"));
                    talk.setTargetUserId(resultSet.getLong("targetUserId"));
                    talk.setSourceLogin(resultSet.getString("sourceLogin"));
                    talk.setTargetLogin(resultSet.getString("targetLogin"));
                    talk.setText(resultSet.getString("text"));
                    talk.setCreationTime(resultSet.getTimestamp("creationTime"));
                    talks.add(talk);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find messages.", e);
        }
        return talks;
    }
}
