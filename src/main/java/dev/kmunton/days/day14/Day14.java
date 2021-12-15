package dev.kmunton.days.day14;

import dev.kmunton.days.Day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Stream;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Day14 implements Day {

    private String template;
    private Map<String, Long> pairs;
    private int size;
    private Map<String, String> rules;
    private final int stepsPart1 = 10;
    private final int stepsPart2 = 40;

    public Day14(String filename) {
        processData(filename);
    }

    @Override
    public void processData(String filename) {
        try {
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(filename)).getFile());
            Scanner myReader = new Scanner(file);
            rules = new HashMap<>();
            pairs = new HashMap<>();
            boolean isTemplate = true;
            while (myReader.hasNextLine()) {
                String s = myReader.nextLine();
                if (s == "") {
                    isTemplate = false;
                    continue;
                }
                if (isTemplate) {
                    template = s;
                    List<String>  initial = List.of(s.split("(?<=\\G.{2})"));
                    for (int i = 0; i < initial.size()-1; i++) {
                        pairs.put(initial.get(i), Long.valueOf("1"));
                        pairs.put(initial.get(i).substring(1,2) + initial.get(i+1).substring(0,1), Long.valueOf("1"));
                        pairs.put(initial.get(i+1), Long.valueOf("1"));
                    }
                    size = template.length();
                } else {
                    String[] parts = s.split(" -> ");
                    rules.put(parts[0], parts[1]);
                }
            }
            myReader.close();
        } catch (
                FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e);
        }
    }



    private void polymerisePairs() {

        Map<String, Long> pairsNew = new HashMap<>();

        for (String pair : pairs.keySet()) {
            String none = "null";
            String output = rules.getOrDefault(pair, none);
            long countCurrent = pairs.getOrDefault(pair, Long.valueOf("0"));
            if (!output.equals(none)) {

                String newPair = pair.charAt(0) + output;
                addToMap(newPair, countCurrent, pairsNew);

                newPair = output + pair.charAt(1);
                addToMap(newPair, countCurrent, pairsNew);

            } else {
                addToMap(pair, countCurrent, pairsNew);
            }

        }
        pairs = pairsNew;

    }

    private void addToMap(String key, Long toAdd, Map<String, Long> map) {
        long count = map.getOrDefault(key, Long.valueOf("0"));
        map.put(key, count + toAdd);
    }

    private long mostCommonSubtractLeastCommon(Map<String, Long> countMap) {
        Set<Map.Entry<String, Long>> entrySet = countMap.entrySet();
        long mostCommon = entrySet.stream().max(comparingByValue()).get().getValue();
        long leastCommon = entrySet.stream().min(comparingByValue()).get().getValue();
        System.out.println(mostCommon + " " + leastCommon);
        return (mostCommon - leastCommon) / 2;
    }

    private long letterCount() {
        Map<String, Long> countMap = new HashMap<>();

        for (String key: pairs.keySet()) {
            long value = pairs.get(key);
            String letter1 = key.substring(0, 1);
            String letter2 = key.substring(1, 2);

            addToMap(letter1, value, countMap);
            addToMap(letter2, value, countMap);
        }

        String first = template.substring(0, 1);
        addToMap(first, Long.valueOf("1"), countMap);

        String last = template.substring(size-1, size);
        addToMap(last, Long.valueOf("1"), countMap);

        return mostCommonSubtractLeastCommon(countMap);

    }

    @Override
    public int part1() {
        int step = 0;
        while(step != stepsPart1) {
            polymerisePairs();
            step += 1;
        }
        System.out.println(letterCount());
        return 0;
    }


    @Override
    public int part2() {
        int step = stepsPart1;
        while(step != stepsPart2) {
            polymerisePairs();
            step += 1;
        }
        System.out.println(letterCount());
        return 0;
    }
}
