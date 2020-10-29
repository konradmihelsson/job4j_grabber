package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;

public class SqlRuPostDetail implements Runnable {

    public static void main(String[] args) {
        SqlRuPostDetail sqlRuPostDetail = new SqlRuPostDetail();
        sqlRuPostDetail.run();
    }

    @Override
    public void run() {
        try {
            Document doc = Jsoup
                    .connect("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t")
                    .get();
            Post post = getPostFromSite(doc);
            System.out.println(post.getTitle());
            System.out.println(post.getDescription());
            System.out.println(post.getCreated());
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public Post getPostFromSite(Document document) throws ParseException {
        Element postTable = document.getElementsByClass("msgTable").first();
        String title = postTable.child(0).child(0).child(0).text();
        String desc = postTable.child(0).child(1).child(1).text();
        String date = postTable.child(0).child(2).child(0).text();
        date = date.substring(0, date.indexOf(" ["));
        return new Post(title, desc, Converter.convertStringToDate(date),
                new User("Username", LocalDateTime.now()));
    }
}
