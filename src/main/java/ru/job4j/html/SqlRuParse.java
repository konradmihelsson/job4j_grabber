package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;

public class SqlRuParse implements Runnable {

    public static void main(String[] args) {
        SqlRuParse sqlRuParse = new SqlRuParse();
        sqlRuParse.run();
    }

    @Override
    public void run() {
        for (int i = 1; i < 6; i++) {
            try {
                Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers"
                        + "/" + i).get();
                Elements row = doc.select(".postslisttopic");
                for (Element td : row) {
                    Element href = td.child(0);
                    System.out.println(href.attr("href"));
                    System.out.println(href.text());
                    String dateAndTimeToParse = href.parent().parent().child(5).text();
                    System.out.println(dateAndTimeToParse);
                    try {
                        System.out.println(Converter.convertStringToDate(dateAndTimeToParse));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
