package ru.job4j.html;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private String title;
    private String description;
    private final User user;
    private final LocalDateTime created;
    private LocalDateTime edited;
    private final List<Post> comments = new ArrayList<>();

    public Post(String title, String description, LocalDateTime created, User user) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.created = created;
    }

    public String getTitle() {
        return title;
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

    public List<Post> getComments() {
        return comments;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEdited(LocalDateTime edited) {
        this.edited = edited;
    }
}
