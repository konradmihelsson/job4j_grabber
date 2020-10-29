package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    public static void main(String[] args) {
        SqlRuParse sqlRuParse = new SqlRuParse();
        sqlRuParse.list("https://www.sql.ru/forum/job-offers")
                .stream().map(v -> v.getTitle().concat(" || ")
                .concat(v.getCreated().toString())).forEach(System.out::println);
    }

    @Override
    public List<Post> list(String link) {
        List<Post> result = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(link).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                result.add(detail(td.child(0).attr("href")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Post detail(String link) {
        Post post = null;
        try {
            Document doc = Jsoup.connect(link).get();
            post = getPostFromSite(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return post;
    }

    private Post getPostFromSite(Document document) {
        Post result = null;
        Element postTable = document.getElementsByClass("msgTable").first();
        String title = postTable.child(0).child(0).child(0).text();
        String desc = postTable.child(0).child(1).child(1).text();
        String date = postTable.child(0).child(2).child(0).text();
        date = date.substring(0, date.indexOf(" ["));
        try {
            result = new Post(title, desc, Converter.convertStringToDate(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
