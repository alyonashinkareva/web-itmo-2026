package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    private static final String PASSWORD_SALT = "1174f9d7bc21e00e9a5fd0a783a44c9a9f73413c";

    private final UserRepository userRepository = new UserRepositoryImpl();

    public void register(User user, String password, String passwordConfirmation) throws ValidationException {
        if (Strings.isNullOrEmpty(user.getLogin())) {
            throw new ValidationException("Login is required");
        }
        if (!user.getLogin().matches("[a-z]+")) {
            throw new ValidationException("Login can contain only lowercase Latin letters");
        }
        if (user.getLogin().length() > 8) {
            throw new ValidationException("Login can't be longer than 8 letters");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ValidationException("Email is required");
        }
        String email = user.getEmail().trim();
        int at = email.indexOf('@');
        if (email.length() > 255 || at <= 0 || at != email.lastIndexOf('@') || at == email.length() - 1) {
            throw new ValidationException("Invalid email");
        }
        user.setEmail(email);

        if (Strings.isNullOrEmpty(password)) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4 characters");
        }
        if (password.length() > 64) {
            throw new ValidationException("Password can't be longer than 64 characters");
        }
        if (!password.equals(passwordConfirmation)) {
            throw new ValidationException("Passwords do not match");
        }

        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new ValidationException("Login is already in use");
        }
        if (userRepository.findByEmail(email) != null) {
            throw new ValidationException("Email is already in use");
        }

        try {
            userRepository.save(user, getPasswordSha(password));
        } catch (RepositoryException e) {
            if (e.getCause() instanceof SQLException && ((SQLException) e.getCause()).getErrorCode() == 1062) {
                throw new ValidationException("Login or email is already in use");
            }
            throw e;
        }
    }

    public User enter(String loginOrEmail, String password) throws ValidationException {
        if (loginOrEmail == null || loginOrEmail.isBlank()) {
            throw new ValidationException("Login or email is required");
        }
        if (Strings.isNullOrEmpty(password)) {
            throw new ValidationException("Password is required");
        }

        User user = userRepository.findByLoginOrEmailAndPasswordSha(loginOrEmail.trim(), getPasswordSha(password));
        if (user == null) {
            throw new ValidationException("Invalid login, email or password");
        }
        return user;
    }

    public User find(long id) {
        return userRepository.find(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public long findCount() {
        return userRepository.findCount();
    }

    public boolean updateAdmin(long actorId, String userId, String value) throws ValidationException {
        long id;
        try {
            id = Long.parseLong(userId);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid user");
        }

        if (!"true".equals(value) && !"false".equals(value)) {
            throw new ValidationException("Invalid admin status");
        }
        boolean admin = Boolean.parseBoolean(value);
        if (!userRepository.updateAdmin(actorId, id, admin)) {
            throw new ValidationException("Only admins can change the status of an existing user");
        }
        return admin;
    }

    private String getPasswordSha(String password) {
        return Hashing.sha256().hashBytes((PASSWORD_SALT + password).getBytes(StandardCharsets.UTF_8)).toString();
    }
}
