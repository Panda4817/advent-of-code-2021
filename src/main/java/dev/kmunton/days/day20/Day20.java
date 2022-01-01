package dev.kmunton.days.day20;

import dev.kmunton.days.Day;
import dev.kmunton.days.day19.Vector;
import dev.kmunton.days.day5.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day20 implements Day {

    private Map<Integer, Integer> output;
    private Map<Point, Integer> map;
    private final int[] dx = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
    private final int[] dy = {-1, -1, -1, 0, 0, 0, 1, 1, 1};

    public Day20(String filename) {
        processData(filename);
    }

    @Override
    public void processData(String filename) {
        try {
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(filename)).getFile());
            java.util.Scanner myReader = new java.util.Scanner(file);

            output = new HashMap<>();
            map = new HashMap<>();
            boolean isOutput = true;
            int y = 0;
            while (myReader.hasNextLine()) {
                String s = myReader.nextLine();

                if (Objects.equals(s, "")) {
                    isOutput = false;
                    continue;
                }

                if (isOutput) {
                    int i = 0;
                    for (Character c : s.toCharArray()) {
                        output.put(i, c == '#' ? 1 : 0);
                        i += 1;
                    }
                    continue;
                }

                if (!isOutput) {
                    int x = 0;
                    for (Character c : s.toCharArray()) {
                        Point point = new Point(x, y);
                        map.put(point, c == '#' ? 1 : 0);
                        x += 1;
                    }

                    y += 1;
                }


            }

            myReader.close();
        } catch (
                FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getBinToDecimal(String s) {
        int dec = Integer.parseInt(s, 2);
        return dec;
    }

    private String findNeighborsAndCreateBinaryString(Point point, Map<Point, Integer> copy, int turn) {

        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            Point p = new Point(point.getX() + dx[i], point.getY() + dy[i]);
            int num = copy.getOrDefault(p, turn % 2 == 0 || turn == 0 ? 0 : 1);
            binary.append(num);
        }
        return binary.toString();
    }

    private void addEdgePixels(int turn) {
        Map<Point, Integer> copyMap = new HashMap<>(map);
        for (Point key : copyMap.keySet()) {
            for (int i = 0; i < 9; i++) {
                Point p = new Point(key.getX() + dx[i], key.getY() + dy[i]);
                if (!map.containsKey(p)) {
                    if (turn % 2 == 0 || turn == 0) {
                        map.put(p, 0);
                    } else {
                        map.put(p, 1);
                    }

                }
            }
        }
    }


    private void printImage() {
        int minx = map.keySet().stream().map(Point::getX).mapToInt(i -> i).min().getAsInt();
        int maxx = map.keySet().stream().map(Point::getX).mapToInt(i -> i).max().getAsInt();
        int miny = map.keySet().stream().map(Point::getY).mapToInt(i -> i).min().getAsInt();
        int maxy = map.keySet().stream().map(Point::getY).mapToInt(i -> i).max().getAsInt();

        for (int y = miny; y < maxy + 1; y++) {
            for (int x = minx; x < maxx + 1; x ++) {
                System.out.print(map.get(new Point(x, y)) == 1 ? '#' : '.');
            }
            System.out.println();
        }
    }



    @Override
    public int part1() {

        for (int i = 0; i < 2; i++) {
            addEdgePixels(i);
            addEdgePixels(i);
            Map<Point, Integer> copyMap = new HashMap<>(map);
            for (Point key: copyMap.keySet()) {
                String binary = findNeighborsAndCreateBinaryString(key, copyMap, i);
                int dec = getBinToDecimal(binary);
                map.put(key, output.get(dec));
            }
        }
        System.out.println(map.values().stream().filter(v -> v == 1).count());
        System.out.println();
        printImage();
        return 0;
    }

    @Override
    public int part2() {
        for (int i = 0; i < 48; i++) {
            addEdgePixels(i);
            addEdgePixels(i);
            Map<Point, Integer> copyMap = new HashMap<>(map);
            for (Point key: copyMap.keySet()) {
                String binary = findNeighborsAndCreateBinaryString(key, copyMap, i);
                int dec = getBinToDecimal(binary);
                map.put(key, output.get(dec));
            }
        }
        System.out.println(map.values().stream().filter(v -> v == 1).count());
        System.out.println();
        printImage();
        return 0;
    }
}
