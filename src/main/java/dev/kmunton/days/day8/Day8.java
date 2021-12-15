package dev.kmunton.days.day8;

import dev.kmunton.days.Day;
import dev.kmunton.days.day5.Line;
import dev.kmunton.days.day6.Fish;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day8 implements Day {
    private List<Signal> data;
    private int size;

    public Day8(String filename) {
        processData(filename);
    }

    @Override
    public void processData(String filename) {
        try {
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(filename)).getFile());
            Scanner myReader = new Scanner(file);
            data = new ArrayList<>();
            while (myReader.hasNextLine()) {
                String s = myReader.nextLine();
                String[] parts = s.split(" \\| ");
                List<String> signal = Stream.of(parts[0].split("\\s+")).collect(Collectors.toList());
                List<String> output = Stream.of(parts[1].split("\\s+")).collect(Collectors.toList());
                Signal sig = new Signal(signal, output);
                data.add(sig);
            }
            size = data.size();
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
        int count = 0;
        for (Signal s: data){
            for (String o : s.getOutput()) {
                Integer i = o.length();
                if ( i == 2 || i == 4 || i == 7 | i == 3) {
                    count += 1;
                }
            }
        }
        return count;
    }

    @Override
    public int part2() {
        int sum = 0;
        for (Signal s: data) {
            sum += s.deduceOutput();
        }
        return sum;
    }
}
