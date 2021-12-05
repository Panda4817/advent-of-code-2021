package dev.kmunton.days.day4;

import dev.kmunton.days.day3.Day3;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day4Test {

    private static Day4 day;
    @BeforeAll
    static void beforeAll() {
        day =  new Day4("4.txt");
    }

    @Test
    void part1() {
        assertEquals(day.part1(), 4512);
    }

    @Test
    void part2() {
        assertEquals(day.part2(), 1924);
    }
}