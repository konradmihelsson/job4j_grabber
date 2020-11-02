package ru.job4j.html;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private String title;
    private String description;
    private String link;
    private final LocalDateTime created;
    private LocalDateTime edited;
    private final List<Post> comments = new ArrayList<>();

    public Post(String title, String description, String link, LocalDateTime created) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
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

    public void setLink(String link) {
        this.link = link;
    }
}
