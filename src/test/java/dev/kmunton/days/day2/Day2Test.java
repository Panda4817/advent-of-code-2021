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
        assertEquals(150, day.part1());
    }

    @Test
    void part2() {
        assertEquals(900, day.part2());
    }
}