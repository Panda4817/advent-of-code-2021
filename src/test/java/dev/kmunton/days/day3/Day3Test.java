package dev.kmunton.days.day3;

import dev.kmunton.days.day2.Day2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day3Test {
    private static Day3 day;
    @BeforeAll
    static void beforeAll() {
        day =  new Day3("3.txt");
    }


    @Test
    void part1() {
        assertEquals(day.part1(), 198);
    }

    @Test
    void part2() {
        assertEquals(day.part2(), 230);
    }
}