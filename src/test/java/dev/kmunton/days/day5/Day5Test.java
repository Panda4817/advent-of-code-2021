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
        assertEquals( 5, day.part1());
    }

    @Test
    void part2() {
        assertEquals( 12, day.part2());
    }
}