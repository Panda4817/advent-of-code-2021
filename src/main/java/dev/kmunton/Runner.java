package dev.kmunton;

import dev.kmunton.days.Day;
import dev.kmunton.days.DayException;


public class Runner {

    public static Day mapDayToClass(String day){
        try {
            Class<Day> dayClass = (Class<Day>) Class.forName("dev.kmunton.days.day" + day + ".Day" + day);
            String resource = day + ".txt";
            Class[] cArg = {String.class};
            return dayClass.getDeclaredConstructor(cArg).newInstance(resource);
        } catch (Exception ex ) {
            return new DayException();
        }
    }

    public static void main(String[] args) {
        if (args.length == 0){
            System.out.println("Provide day number");
            return;
        } else if (args.length > 1) {
            System.out.println("Too many days provided. Provide one day.");
            return;
        }

        Day ans = mapDayToClass(args[0]);

        System.out.println("Part 1: ");
        final long startTimePart1 = System.currentTimeMillis();
        int part1 = ans.part1();
        final long endTimePart1 = System.currentTimeMillis();

        if (part1 > 0) {
            System.out.println(part1);
        }

        if (part1 != -1) {
            System.out.println("Total execution time: " + (endTimePart1 - startTimePart1) + " ms");
        }

        System.out.println();
        System.out.println("Part 2: ");
        final long startTimePart2 = System.currentTimeMillis();
        int part2 = ans.part2();
        final long endTimePart2 = System.currentTimeMillis();

        if (part2 > 0) {
            System.out.println(part2);
        }
        if (part2 != -1) {
            System.out.println("Total execution time: " + (endTimePart2 - startTimePart2) + " ms");
        }

    }
}
