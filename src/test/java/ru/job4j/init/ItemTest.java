package ru.job4j.init;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ItemTest {

    @Test
    public void whenCreateThenGetSameName() {
        Item item = new Item(1, "Jorik");
        assertThat(
                item.getName(),
                is("Jorik")
        );
    }

    @Test
    public void whenCreateThenGetSameId() {
        Item item = new Item(1, "Jorik");
        assertThat(
                item.getId(),
                is(1)
        );
    }
}
