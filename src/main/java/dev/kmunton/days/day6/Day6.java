package dev.kmunton.days.day6;

import dev.kmunton.days.Day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 implements Day {

    private List<Fish> data;
    private Map<Integer, Long> mapData;


    public Day6(String filename) {
        processData(filename);
    }

    @Override
    public void processData(String filename) {
        try {
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(filename)).getFile());
            Scanner myReader = new Scanner(file);
            mapData = new HashMap<>();
            data = Stream.of(myReader.nextLine().split(",")).map(Integer::valueOf).map(Fish::new).collect(Collectors.toList());
            for (Fish f: data) {
                Long count = mapData.getOrDefault(f.getTimer(), Long.valueOf("0"));
                if (count != 0) {
                    continue;
                }
                mapData.put(f.getTimer(), data.stream().filter(n -> n.getTimer() == f.getTimer()).count());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private List<Fish> oneCycle(List<Fish> lst) {
        List<Fish> newFish = new ArrayList<>();
        for (Fish f : lst) {
            f.decreaseTimer();
            if (f.getTimer() == 6 && !f.isYoung()) {
                newFish.add(new Fish(8));
            }

        }
        lst.addAll(newFish);
        return lst;
    }

    private int spawnFish(int days, int currentDay, List<Fish> lst) {
        while (currentDay != days) {
            oneCycle(lst);
            currentDay += 1;
        }
        return lst.size();
    }

    @Override
    public int part1() {
        // using an array - not the best way
        int days = 80;
        return spawnFish(days, 0, data);

    }

    @Override
    public int part2() {
        // Better way using a hashmap
        int days = 256;
        int currentDay = 0;
        while (currentDay != days) {
            Map<Integer, Long> map = new HashMap<>();
            for (int i=1; i < 9; i ++) {
                Long vals = mapData.getOrDefault(i, Long.valueOf("0", 2));
                int key = i-1;
                map.put(key, vals);
            }
            Long count0 = mapData.getOrDefault(0, Long.valueOf("0", 2));
            Long count6 = map.getOrDefault(6, Long.valueOf("0", 2));
            map.put(6, count6 + count0);
            map.put(8, count0);
            currentDay += 1;
            mapData = map;
        }
        System.out.println(mapData.values().stream().mapToLong(i -> i).sum());
        return 0;
    }


}
