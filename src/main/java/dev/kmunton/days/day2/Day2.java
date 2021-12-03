package dev.kmunton.days.day2;

import dev.kmunton.days.Day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Day2 implements Day {

    private List<Command> data = new ArrayList<>();

    public Day2(String resource) {
        processData(resource);
    }

    @Override
    public void processData(String filename) {
        try {
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(filename)).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String s = myReader.nextLine();
                String[] parts = s.split(" ");
                data.add(new Command(parts[0], Integer.parseInt(parts[1])));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public int part1() {
        Submarine sub = new Submarine();
        sub.setCourseNoAim(data);
        return sub.getDepth() * sub.getHorizontalPosition();

    }

    @Override
    public int part2() {
        Submarine sub = new Submarine();
        sub.setCourse(data);
        return sub.getDepth() * sub.getHorizontalPosition();
    }
}
