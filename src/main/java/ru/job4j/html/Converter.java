package ru.job4j.html;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.TreeMap;

public class Converter {

    private static Map<String, Integer> months = fillMonths();

    private static Map<String, Integer> fillMonths() {
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

    public static LocalDateTime convertStringToDate(String stringToConvert) throws ParseException {
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
            Integer month = months.get(pieces[1]);
            if (month == null) {
                throw new ParseException("Date format (month) on site is changed!", 0);
            }
            date = LocalDate.of(Integer.parseInt(20 + year.substring(0, year.length() - 1)),
                    month, Integer.parseInt(pieces[0]));
            time = LocalTime.parse(pieces[3]);
        } else {
            throw new ParseException("Date format on site is changed!", 0);
        }
        return LocalDateTime.of(date, time);
    }
}
