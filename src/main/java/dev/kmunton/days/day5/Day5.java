package dev.kmunton.days.day5;

import dev.kmunton.days.Day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day5 implements Day {

    List<Line> lines;
    int size;

    public Day5(String filename) {
        processData(filename);
    }

    @Override
    public void processData(String filename) {
        try {
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(filename)).getFile());
            Scanner myReader = new Scanner(file);
            lines = new ArrayList<>();
            while (myReader.hasNextLine()) {
                String s = myReader.nextLine();
                String[] parts = s.split(" -> ");
                String[] start = parts[0].split(",");
                String[] end = parts[1].split(",");
                Line line = new Line(Integer.parseInt(start[0]), Integer.parseInt(start[1]), Integer.parseInt(end[0]), Integer.parseInt(end[1]));
                lines.add(line);
            }
            size = lines.size();
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
        Map<Point, Integer> pointsCount = new HashMap<>();
        for (Line l : lines) {
            if (!l.isHorizontal() && !l.isVertical()) {
                continue;
            }

            List<Point> points = l.getAllPoints();

            for (Point p: points) {
                int count = pointsCount.getOrDefault(p, 0);
                pointsCount.put(p, count + 1);
            }
        }

        long intersections = pointsCount.values().stream().filter(v -> v >= 2).count();
        return Math.toIntExact(intersections);
    }

    @Override
    public int part2() {
        Map<Point, Integer> pointsCount = new HashMap<>();
        for (Line l : lines) {
            if (l.isVertical() || l.isHorizontal()) {
                List<Point> points = l.getAllPoints();
                for (Point p: points) {
                    int count = pointsCount.getOrDefault(p, 0);
                    pointsCount.put(p, count + 1);
                }
            } else if (l.isDiagonal()){
                List<Point> points = l.getAllDiagonalPoints();
                for (Point p: points) {
                    int count = pointsCount.getOrDefault(p, 0);
                    pointsCount.put(p, count + 1);
                }
            }

        }

        long intersections = pointsCount.values().stream().filter(v -> v >= 2).count();
        return Math.toIntExact(intersections);
    }
}
