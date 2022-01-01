package dev.kmunton.days.day25;

import dev.kmunton.days.Day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day25 implements Day {

    private int width;
    private int height;
    List<List<Integer>> grid = new ArrayList<>();

    public Day25(String filename) {
        processData(filename);
    }

    @Override
    public void processData(String filename) {
        try {
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(filename)).getFile());
            java.util.Scanner myReader = new java.util.Scanner(file);


            int y = 0;
            while (myReader.hasNextLine()) {
                String s = myReader.nextLine();
                List<Integer> row = new ArrayList<>();
                String[] cols = s.split("(?<=\\G.)");
                int x = 0;
                for (String col: cols) {
                    if (col.equals(".")) {
                        row.add(0);
                    } else if (col.equals(">")) {
                        row.add(1);
                    } else {
                        row.add(2);
                    }
                    x += 1;
                }
                grid.add(row);
                y += 1;

            }
            width = grid.get(0).size();
            height = grid.size();

            myReader.close();
        } catch (
                FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void  printGrid() {
        for (List<Integer> row : grid) {
            System.out.println( row);
        }
    }

    private List<List<Integer>> copyGrid() {
        List<List<Integer>> gridCopy = new ArrayList<>();
        for (List<Integer> row : grid) {
            List<Integer> newRow = new ArrayList<>(row);
            gridCopy.add(newRow);
        }
        return gridCopy;
    }




    @Override
    public int part1() {
        printGrid();
        long steps = 0L;


        while (true) {

            // move sea cucumbers that move east
            List<List<Integer>> gridCopy = copyGrid();
            int moved = 0;
            int y = 0;
            for (List<Integer> row : gridCopy) {
                int x = 0;
                for (Integer i : row) {
                    if (i == 1) {
                        int newX = x + 1;
                        if (newX >= width) {
                            newX = 0;
                        }
                        if (gridCopy.get(y).get(newX) == 0) {
                            grid.get(y).set(newX, 1);
                            grid.get(y).set(x, 0);
                            moved += 1;
                        }
                    }
                    x += 1;
                }
                y += 1;
            }

            // move sea cucumbers that move south
            gridCopy = copyGrid();
            y = 0;
            for (List<Integer> row : gridCopy) {
                int x = 0;
                for (Integer i : row) {
                    if (i == 2) {
                        int newY = y + 1;
                        if (newY >= height) {
                            newY = 0;
                        }
                        if (gridCopy.get(newY).get(x) == 0) {
                            grid.get(newY).set(x, 2);
                            grid.get(y).set(x, 0);
                            moved += 1;
                        }
                    }
                    x += 1;
                }
                y += 1;
            }



            steps += 1L;
            if (moved == 0) {
                break;
            }


        }

        System.out.println(steps);


        return 0;
    }

    @Override
    public int part2() {
        System.out.println("No part 2");
        return 0;
    }
}
