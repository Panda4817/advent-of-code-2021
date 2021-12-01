package dev.kmunton.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DayExceptionTest {

    private DayException day = new DayException();

    @Test
    void part1() {
        assertEquals(-1, day.part1());
    }

    @Test
    void part2() {
        assertEquals(-1, day.part2());
    }
}