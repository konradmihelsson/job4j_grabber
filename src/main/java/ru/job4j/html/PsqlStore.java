package ru.job4j.html;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection connection;

    public PsqlStore(Properties config) {
        try {
            Class.forName(config.getProperty("driver-class-name"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            this.connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Properties config = new Properties();
        try (InputStream in = PsqlStore.class.getClassLoader().getResourceAsStream("grabber.properties")) {
            config.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Store store = new PsqlStore(config);
        Post post1 = new Post("Title1", "Desc1", "http://127.0.0.1/link1", LocalDateTime.now());
        Post post2 = new Post("Title2", "Desc2", "http://127.0.0.1/link2", LocalDateTime.now());
        store.save(post1);
        store.save(post2);
        store.getAll().stream().map(Post::getTitle).forEach(System.out::println);
        System.out.println(store.findById("http://127.0.0.1/link1").getTitle());
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
    }

    @Override
    public void save(Post post) {
        String query = "insert into post (name, description, link, created) values (?, ?, ?, ?)";
        try (PreparedStatement ps = this.connection.prepareStatement(query)) {
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getDescription());
            ps.setString(3, post.getLink());
            ps.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            ps.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> result = new ArrayList<>();
        String query = "select name, description, link, created from post";
        try (PreparedStatement ps = this.connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new Post(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getTimestamp(4).toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Post findById(String id) {
        Post result = null;
        String query = "select name, description, link, created from post where link = ?";
        try (PreparedStatement ps = this.connection.prepareStatement(query)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = new Post(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getTimestamp(4).toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
