package ru.job4j.html;

import org.junit.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SqlRuParseTest {

    @Test
    public void whenRegularDate() throws ParseException {
        SqlRuParse sqlRuParse = new SqlRuParse();
        String input = "2 дек 19, 22:29";
        LocalDateTime expected = LocalDateTime.of(19, 12, 2, 22, 29);
        assertThat(
                sqlRuParse.convertStringToDate(input),
                is(expected)
        );
    }

    @Test (expected = ParseException.class)
    public void whenWrongMonthThenEx() throws ParseException {
        SqlRuParse sqlRuParse = new SqlRuParse();
        String input = "2 стн 19, 22:29";
        LocalDateTime expected = LocalDateTime.of(19, 12, 2, 22, 29);
        assertThat(
                sqlRuParse.convertStringToDate(input),
                is(expected)
        );
    }

    @Test
    public void whenSpecialDate() throws ParseException {
        SqlRuParse sqlRuParse = new SqlRuParse();
        String input = "сегодня, 22:29";
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.parse("22:29");
        LocalDateTime expected = LocalDateTime.of(date, time);
        assertThat(
                sqlRuParse.convertStringToDate(input),
                is(expected)
        );
    }

    @Test
    public void whenSecondSpecialDate() throws ParseException {
        SqlRuParse sqlRuParse = new SqlRuParse();
        String input = "вчера, 02:29";
        LocalDate date = LocalDate.now().minusDays(1);
        LocalTime time = LocalTime.parse("02:29");
        LocalDateTime expected = LocalDateTime.of(date, time);
        assertThat(
                sqlRuParse.convertStringToDate(input),
                is(expected)
        );
    }

    @Test (expected = ParseException.class)
    public void whenWrongSpecialDateThenEx() throws ParseException {
        SqlRuParse sqlRuParse = new SqlRuParse();
        String input = "завтра, 02:29";
        LocalDateTime expected = LocalDateTime.of(19, 12, 2, 22, 29);
        assertThat(
                sqlRuParse.convertStringToDate(input),
                is(expected)
        );
    }

    @Test (expected = ParseException.class)
    public void whenWrongDateThenEx() throws ParseException {
        SqlRuParse sqlRuParse = new SqlRuParse();
        String input = "some string with big length";
        LocalDateTime expected = LocalDateTime.of(19, 12, 2, 22, 29);
        assertThat(
                sqlRuParse.convertStringToDate(input),
                is(expected)
        );
    }
}
