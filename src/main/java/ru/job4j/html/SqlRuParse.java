package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.TreeMap;

public class SqlRuParse implements Runnable {

    private Map<String, Integer> months = fillMonths();

    private Map<String, Integer> fillMonths() {
        Map<String, Integer> result = new TreeMap<>();
        result.put("янв", 1);
        result.put("фев", 2);
        result.put("мар", 3);
        result.put("апр", 4);
        result.put("май", 5);
        result.put("июн", 6);
        result.put("июл", 7);
        result.put("авг", 8);
        result.put("сен", 9);
        result.put("окт", 10);
        result.put("ноя", 11);
        result.put("дек", 12);
        return result;
    }

    public static void main(String[] args) {
        SqlRuParse sqlRuParse = new SqlRuParse();
        sqlRuParse.run();
    }

    public LocalDateTime convertStringToDate(String stringToConvert) throws ParseException {
        String[] pieces = stringToConvert.split(" ");
        LocalDate date;
        LocalTime time;
        if (pieces.length == 2) {
            time = LocalTime.parse(pieces[1]);
            if (pieces[0].equals("сегодня,")) {
                date = LocalDate.now();
            } else if (pieces[0].equals("вчера,")) {
                date = LocalDate.now().minusDays(1);
            } else {
                throw new ParseException("Date format (today, yesterday) on site is changed!", 0);
            }
        } else if (pieces.length == 4) {
            String year = pieces[2];
            Integer month = this.months.get(pieces[1]);
            if (month == null) {
                throw new ParseException("Date format (month) on site is changed!", 0);
            }
            date = LocalDate.of(Integer.parseInt(year.substring(0, year.length() - 1)),
                    month, Integer.parseInt(pieces[0]));
            time = LocalTime.parse(pieces[3]);
        } else {
            throw new ParseException("Date format on site is changed!", 0);
        }
        return LocalDateTime.of(date, time);
    }

    @Override
    public void run() {
        try {
            Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                System.out.println(href.attr("href"));
                System.out.println(href.text());
                String dateAndTimeToParse = href.parent().parent().child(5).text();
                System.out.println(dateAndTimeToParse);
                try {
                    System.out.println(convertStringToDate(dateAndTimeToParse));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
