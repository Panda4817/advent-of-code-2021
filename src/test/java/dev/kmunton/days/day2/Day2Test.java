package dev.kmunton.days.day2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day2Test {
    private static Day2 day;
    @BeforeAll
    static void beforeAll() {
        day =  new Day2("2.txt");
    }

    @Test
    void part1() {
        assertEquals(day.part1(), 150);
    }

    @Test
    void part2() {
        assertEquals(day.part2(), 900);
    }
}