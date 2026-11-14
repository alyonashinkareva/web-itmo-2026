package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.EventRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EventRepositoryImpl implements EventRepository {
    private final DataSource dataSource = DatabaseUtils.getDataSource();

    @Override
    public void save(Event event) {
        String sql = "INSERT INTO `Event` (userId, type, creationTime) VALUES (?, ?, NOW())";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, event.getUserId());
            statement.setString(2, event.getType().name());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Can't save event.", e);
        }
    }
}
