package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.util.List;

public class TalkService {
    private final TalkRepository talkRepository = new TalkRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    public void validateTalk(Talk talk) throws ValidationException {
        if (userRepository.find(talk.getTargetUserId()) == null) {
            throw new ValidationException("Target user not found");
        }
        if (talk.getText() == null || talk.getText().trim().isEmpty()) {
            throw new ValidationException("Text is required");
        }
        if (talk.getText().length() > 1000) {
            throw new ValidationException("Text can't be longer than 1000 characters");
        }
    }

    public void save(Talk talk) {
        talkRepository.save(talk);
    }

    public List<Talk> findByUser(User user) {
        return talkRepository.findByUserId(user.getId());
    }
}
