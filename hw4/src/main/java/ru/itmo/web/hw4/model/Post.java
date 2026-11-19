package ru.itmo.web.hw4.model;

public class Post {
    private final long id;
    private final String title;
    private final String text;
    private final long user_id;

    public Post(long id, String title, String text, long userId) {
        this.id = id;
        this.title = title;
        this.text = text;
        user_id = userId;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public long getUser_id() {
        return user_id;
    }
}
