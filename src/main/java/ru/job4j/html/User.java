package ru.job4j.html;

import java.time.LocalDateTime;

public class User {
    private String name;
    private final LocalDateTime registered;
    private String email;

    public User(String name, LocalDateTime registered) {
        this.name = name;
        this.registered = registered;
    }

    public User(String name, LocalDateTime registered, String email) {
        this.name = name;
        this.registered = registered;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }
}
