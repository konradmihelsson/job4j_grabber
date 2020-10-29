package ru.job4j.html;

import java.time.LocalDateTime;

public class Comment {
    private String description;
    private final User user;
    private final LocalDateTime created;
    private LocalDateTime edited;

    public Comment(String description, User user) {
        this.description = description;
        this.user = user;
        this.created = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getEdited() {
        return edited;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEdited(LocalDateTime edited) {
        this.edited = edited;
    }
}
