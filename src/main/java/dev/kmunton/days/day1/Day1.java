package dev.kmunton.days.day1;

import dev.kmunton.days.Day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Day1 implements Day {

    private List<Integer> data = new ArrayList<>();

    public Day1(String resource) {
        processData(resource);
    }

    public void processData(String resource) {
        try {
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(resource)).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String s = myReader.nextLine();
                data.add(Integer.parseInt(s));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public int part1() {
        int length = data.size();

        int totalIncreased = 0;

        for (int i = 0; i < length; i = i + 1){
            if (i == 0) {
                continue;
            }

            if (data.get(i) > data.get(i-1)) {
                totalIncreased += 1;
            }
        }

        return totalIncreased;

    }

    public int part2() {
        int length = data.size();

        int totalIncreased = 0;

        for (int i = 0; i < length; i = i + 1){
            if (i < 3) {
                continue;
            }

            int window2 = data.get(i) + data.get(i-1) + data.get(i-2);
            int window1 = data.get(i-1) + data.get(i-2) + data.get(i-3);
            if (window2 > window1) {
                totalIncreased += 1;
            }
        }

        return totalIncreased;
    }
}
