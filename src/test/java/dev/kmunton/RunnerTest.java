package dev.kmunton;

import dev.kmunton.days.Day;
import dev.kmunton.days.DayException;
import dev.kmunton.days.day1.Day1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class RunnerTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }


    @Test
    void mapDayToClass_String_Day() {
        Day obj = Runner.mapDayToClass("hello");

        assertEquals(DayException.class, obj.getClass());
    }

    @Test
    void mapDayToClass_Empty_Day() {
        Day obj = Runner.mapDayToClass("");

        assertEquals(DayException.class, obj.getClass());
    }

    @Test
    void mapDayToClass_Lower_Than_0_Day() {
        Day obj = Runner.mapDayToClass("-1");

        assertEquals(DayException.class, obj.getClass());
    }

    @Test
    void mapDayToClass_Too_High_Day() {
        Day obj = Runner.mapDayToClass("26");

        assertEquals(DayException.class, obj.getClass());
    }

    @Test
    void mapDayToClass_Correct_Day() {
        Day obj = Runner.mapDayToClass("1");

        assertEquals(Day1.class, obj.getClass());
    }

    @Test
    void main_Empty_args() {
        String[] list = {};
        Runner.main(list);

        assertEquals("Provide day number", outputStreamCaptor.toString().trim());
    }

    @Test
    void main_incorrect_word_args() {
        String[] list = {"hello"};
        Runner.main(list);

        assertEquals("Part 1: \nIncorrect day entered, no result\n\nPart 2: \nIncorrect day entered, no result",
                outputStreamCaptor.toString().trim());
    }

    @Test
    void main_incorrect_low_number_args() {
        String[] list = {"-1"};
        Runner.main(list);

        assertEquals("Part 1: \nIncorrect day entered, no result\n\nPart 2: \nIncorrect day entered, no result",
                outputStreamCaptor.toString().trim());
    }

    @Test
    void main_incorrect_high_number_args() {
        String[] list = {"26"};
        Runner.main(list);

        assertEquals("Part 1: \nIncorrect day entered, no result\n\nPart 2: \nIncorrect day entered, no result",
                outputStreamCaptor.toString().trim());
    }

    @Test
    void main_too_many_args() {
        String[] list = {"1", "2"};
        Runner.main(list);

        assertEquals("Too many days provided. Provide one day.", outputStreamCaptor.toString().trim());
    }

    @Test
    void main_correct_args() {
        String[] list = {"1"};
        Runner.main(list);

        assertNotEquals("Part 1: \nIncorrect day entered, no result\n\nPart 2: \nIncorrect day entered, no result",
                outputStreamCaptor.toString().trim());
    }
}