package dev.kmunton.days;

public class DayException implements Day{

    @Override
    public void processData(String file) {
    }

    public int part1() {
        System.out.println("Incorrect day entered, no result");
        return -1;
    }

    public int part2() {
        System.out.println("Incorrect day entered, no result");
        return -1;
    }
}
