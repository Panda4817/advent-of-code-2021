package dev.kmunton.days.day7;

import dev.kmunton.days.Day;
import dev.kmunton.days.day6.Fish;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7 implements Day {

    private static final boolean ASC = true;
    List<Integer> data;
    Map<Integer, Integer> mapData;

    public Day7(String filename) {
        processData(filename);
    }


    @Override
    public void processData(String filename) {
        try {
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(filename)).getFile());
            Scanner myReader = new Scanner(file);
            mapData = new HashMap<>();
            data = Stream.of(myReader.nextLine().split(",")).map(Integer::valueOf).collect(Collectors.toList());
            for (Integer i: data) {
                int count = mapData.getOrDefault(i, 0);
                mapData.put(i, (int) data.stream().filter(n -> n == i).count());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public int part1() {
        int currentLowest = 1000000000;
        int totalFuel = 0;
        for (int i: mapData.keySet()) {
            totalFuel = 0;
            for (int j : data) {
                totalFuel += Math.abs(i-j);
            }
            if (totalFuel < currentLowest) {
                currentLowest = totalFuel;
            }
        }
        return currentLowest;
    }

    @Override
    public int part2() {
        int max = data.stream().max(Integer::max).get();
        int currentLowest = Integer.MAX_VALUE;
        int totalFuel;
        for (int i = 0; i < max+1; i++) {
            totalFuel = 0;
            for (int j : data) {
                int steps = Math.abs(i-j);
                for (int x = 0; x < steps; x++) {
                    totalFuel += x + 1;
                }
            }
            if (totalFuel < currentLowest) {
                currentLowest = totalFuel;
            }
        }
        return currentLowest;
    }
}
