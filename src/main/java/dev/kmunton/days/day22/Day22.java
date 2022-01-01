package dev.kmunton.days.day22;

import dev.kmunton.days.Day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day22 implements Day {


    List<Range> allRanges;

    public Day22(String filename) {
        processData(filename);
    }

    @Override
    public void processData(String filename) {
        try {
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(filename)).getFile());
            java.util.Scanner myReader = new java.util.Scanner(file);

            allRanges = new ArrayList<>();
            while (myReader.hasNextLine()) {
                String s = myReader.nextLine();
                String[] parts = s.split(" ");
                boolean isOn = parts[0].equals("on");

                String[] xyz = parts[1].split(",");
                String[] xparts = xyz[0].split("\\.\\.");
                int minx = Integer.parseInt(xparts[0].substring(2));
                int maxx = Integer.parseInt(xparts[1]);
                String[] yparts = xyz[1].split("\\.\\.");
                int miny = Integer.parseInt(yparts[0].substring(2));
                int maxy = Integer.parseInt(yparts[1]);
                String[] zparts = xyz[2].split("\\.\\.");
                int minz = Integer.parseInt(zparts[0].substring(2));
                int maxz = Integer.parseInt(zparts[1]);

                Cube from = new Cube(minx, miny, minz);
                Cube to = new Cube(maxx, maxy, maxz);
                Range range = new Range(from, to, isOn);
                allRanges.add(range);
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


    @Override
    public int part1() {

        List<Range> allCuboids = new ArrayList<>();
        for (Range r: allRanges) {
            int minx = r.getFrom().getX();
            int miny = r.getFrom().getY();
            int minz = r.getFrom().getZ();
            int maxx = r.getTo().getX();
            int maxy = r.getTo().getY();
            int maxz = r.getTo().getZ();
            if (minx >= -50 && miny >= -50 && minz >= -50 && maxx <= 50 && maxy <= 50 && maxz <= 50) {
                List<Range> extraCuboids = new ArrayList<>();
                if (r.getOn()) {
                    extraCuboids.add(new Range(r));
                }
                for (Range cuboid: allCuboids) {
                    Optional<Range> possibleOverlap = cuboid.getOverlapping(r);
                    possibleOverlap.ifPresent(extraCuboids::add);
                }
                allCuboids.addAll(extraCuboids);
            }
        }
        long howManyOn = allCuboids.stream().mapToLong(r -> r.getOn() ? r.numberOfCubes() : -1 * r.numberOfCubes()).sum();
        System.out.println(howManyOn);
        return 0;
    }

    @Override
    public int part2() {

        List<Range> allCuboids = new ArrayList<>();
        for (Range r : allRanges) {
            List<Range> extraCuboids = new ArrayList<>();
            if (r.getOn()) {
                extraCuboids.add(new Range(r));
            }
            for (Range cuboid: allCuboids) {
                Optional<Range> possibleOverlap = cuboid.getOverlapping(r);
                possibleOverlap.ifPresent(extraCuboids::add);
            }
            allCuboids.addAll(extraCuboids);
        }

        long howManyOn = allCuboids.stream().mapToLong(r -> r.getOn() ? r.numberOfCubes() : -1 * r.numberOfCubes()).sum();
        System.out.println(howManyOn);
        return 0;
    }
}
