package dev.kmunton.days.day6;

import dev.kmunton.days.day5.Day5;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class Day6Test {

    private static Day6 day;
    @BeforeAll
    static void beforeAll() {
        day =  new Day6("6.txt");
    }

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void part1() {
        assertEquals( 5934, day.part1());

    }

    @Test
    void part2() {
        int ans = day.part2();
        assertEquals(0, ans);
        assertEquals("26984457539", outputStreamCaptor.toString().trim());
    }
}