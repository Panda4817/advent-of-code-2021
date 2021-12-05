package dev.kmunton.days.day5;

import dev.kmunton.days.day4.Day4;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day5Test {

    private static Day5 day;
    @BeforeAll
    static void beforeAll() {
        day =  new Day5("5.txt");
    }

    @Test
    void part1() {
        assertEquals(day.part1(), 5);
    }

    @Test
    void part2() {
        assertEquals(day.part2(), 12);
    }
}