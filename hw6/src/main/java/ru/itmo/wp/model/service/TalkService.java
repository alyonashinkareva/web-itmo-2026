package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;

import java.util.List;

public class TalkService {
    private final TalkRepository talkRepository = new TalkRepositoryImpl();
    private final UserService userService = new UserService();

    public void send(long sourceUserId, String targetUserId, String text) throws ValidationException {
        long targetId;
        try {
            targetId = Long.parseLong(targetUserId);
        } catch (NumberFormatException e) {
            throw new ValidationException("Choose a recipient");
        }
        if (userService.find(targetId) == null) {
            throw new ValidationException("Recipient does not exist");
        }
        if (text == null || text.isBlank()) {
            throw new ValidationException("Message is required");
        }
        if (text.length() > 10000) {
            throw new ValidationException("Message can't be longer than 10000 characters");
        }

        Talk talk = new Talk();
        talk.setSourceUserId(sourceUserId);
        talk.setTargetUserId(targetId);
        talk.setText(text);
        talkRepository.save(talk);
    }

    public List<Talk> findByUserId(long userId) {
        return talkRepository.findByUserId(userId);
    }
}
