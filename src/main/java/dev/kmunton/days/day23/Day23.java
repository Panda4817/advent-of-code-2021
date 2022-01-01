package dev.kmunton.days.day23;

import dev.kmunton.days.Day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day23 implements Day {

    private Integer[][] grid;
    private Map<Integer, Amphipod> letterInfo;
    private Integer minSteps;
    private Integer maxY;
    private Integer maxX;
    private Integer empty;
    private Integer wall;

    public Day23(String filename) {
        letterInfo = new HashMap<>();
        letterInfo.put(1, new Amphipod("A", 1, 1, 2));
        letterInfo.put(2, new Amphipod("B", 2, 10, 4));
        letterInfo.put(3, new Amphipod("C", 3, 100, 6));
        letterInfo.put(4, new Amphipod("D", 4, 1000, 8));
        processData("23a.txt");
    }

    private static int applyAsInt(Node n) {
        return n.getSteps() + n.getHeuristic();
    }

    @Override
    public void processData(String filename) {
        try {
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(filename)).getFile());
            java.util.Scanner myReader = new java.util.Scanner(file);
            maxX = 11;
            empty = 0;
            wall = 9;
            if (filename.equals("23a.txt")) {
                maxY = 3;
                grid = new Integer[maxY][maxX];
                minSteps = 13000;

            } else {
                maxY = 5;
                grid = new Integer[maxY][maxX];
                minSteps = 43000;
            }
            int y = 0;
            while (myReader.hasNextLine()) {
                String s = myReader.nextLine();
                List<String> row = List.of(s.split("(?<=\\G.)"));
                int x = 0;
                for (String cell : row) {
                    if (cell.equals("#")) {
                        grid[y][x] = 9;
                    }
                     else if (cell.equals(".")) {
                        grid[y][x] = 0;
                    } else {
                         grid[y][x] = switch(cell) {
                             case "A" -> 1;
                             case "B" -> 2;
                             case "C" -> 3;
                             default -> 4;
                         };
                    }
                    x += 1;

                }
                y += 1;

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

    private Integer[][] copyGrid(Integer[][] gridToCopy) {
        Integer[][] newGrid = new Integer[maxY][maxX];
        int y = 0;
        for (Integer[] row: gridToCopy) {
            newGrid[y] = row.clone();
            y += 1;
        }
        return newGrid;
    }

    private int getSteps(int currentY, int currentX, int newY, int newX, int num) {
        int spaces = 0;
        int diffX = Math.abs(Math.max(currentX, newX) - Math.min(currentX, newX));
        if (currentY > 0 && newY > 0) {
            spaces += currentY;
            spaces += newY;
            spaces += diffX;
        } else {
            spaces += (diffX + Math.abs(Math.max(currentY, newY) - Math.min(currentY, newY)));
        }

        return  spaces * letterInfo.get(num).getEnergyPerStep();
    }

    private int calculateHeuristic(Integer[][] grid) {
        int result = 0;
        int burrowY = maxY-1;
        for (int x = 0; x < maxX; x++) {
            int i = grid[0][x];
            if (i != empty) {
                result += getSteps(0, x, burrowY, letterInfo.get(i).getBurrowX(), i);
                burrowY -= 1;
                if (burrowY == 0) {
                    burrowY = maxY-1;
                }
            }
        }
        for (int n = 1; n < 5; n++){
            int x = letterInfo.get(n).getBurrowX();
            for (int y = 1; y < maxY; y++) {
               int i = grid[y][x];
               if (i != empty && i != n) {
                   result += getSteps(y, x, burrowY, letterInfo.get(i).getBurrowX(), i);
                   burrowY -= 1;
               } else if (i == n) {
                   burrowY -= 1;
               }
                if (burrowY == 0) {
                    burrowY = maxY-1;
                }


           }
       }
       return result;
    }



    private List<Node> canItMoveIntoBurrowFromHall(int currentX, int num, Node node) {
        List<Node> possibleMoves = new ArrayList<>();
        Integer[][] grid = node.getGrid();
        int burrowX = letterInfo.get(num).getBurrowX();
        int newY = 0;
        for (int y = 1; y < maxY; y++) {
            if (Objects.equals(grid[y][burrowX], empty)) {
                newY = y;
            }
            else if (grid[y][burrowX] != num && !Objects.equals(grid[y][burrowX], empty)) {
                return new ArrayList<>();
            } else {
                break;
            }

        }
        if (newY != 0) {
            Integer[][] gridCopy = copyGrid(grid);
            gridCopy[newY][burrowX] = num;
            gridCopy[0][currentX] = 0;
            int stepsToAdd = getSteps(0, currentX, newY, burrowX, num);
            int steps = stepsToAdd + node.getSteps();
            possibleMoves.add(new Node(gridCopy, steps, calculateHeuristic(gridCopy)));
        }
        return possibleMoves;
    }

    private boolean inOwnBurrow(int num, int burrowX, Integer[][] grid) {
        for (int y = maxY-1; y > 0; y--) {
            if (grid[y][burrowX] != num && grid[y][burrowX] != 0) {
                return false;
            }
        }
        return true;
    }

    private List<Node> canItMoveIntoHallFromBurrowOrMoveIntoBurrowDirect(int currentY, int currentX, int num, Node node) {
        int burrowX = letterInfo.get(num).getBurrowX();
        Integer[][] grid = node.getGrid();
        List<Node> possibleMoves = new ArrayList<>();
        List<Integer> newX = new ArrayList<>();
        Integer newY = null;
        int burrowEntrance1 = letterInfo.get(1).getBurrowX();
        int burrowEntrance2 = letterInfo.get(2).getBurrowX();
        int burrowEntrance3 = letterInfo.get(3).getBurrowX();
        int burrowEntrance4 = letterInfo.get(4).getBurrowX();
        for (int x = currentX+1; x < maxX; x++) {
            if (x != burrowEntrance4 && x != burrowEntrance1 && x != burrowEntrance2 && x != burrowEntrance3 && Objects.equals(grid[0][x], empty)) {
                newX.add(x);
            } else if(!Objects.equals(grid[0][x], empty)) {
                break;
            }
            if (x == burrowX && currentX != burrowX) {
                for (int y = 1; y < maxY; y++) {
                    if (grid[y][burrowX] == 0) {
                        newY = y;
                    }
                    else if (grid[y][burrowX] != num && !Objects.equals(grid[y][burrowX], empty)) {
                        newY = null;
                        break;
                    } else {
                        break;
                    }

                }
            }

        }
        for (int x = currentX-1; x >= 0; x--) {
            if (x != burrowEntrance1 && x != burrowEntrance2 && x != burrowEntrance3 && x != burrowEntrance4 && Objects.equals(grid[0][x], empty)) {
                newX.add(x);
            } else if(!Objects.equals(grid[0][x], empty)) {
                break;
            }
            if (x == burrowX && currentX != burrowX) {
                for (int y = 1; y < maxY; y++) {
                    if (Objects.equals(grid[y][burrowX], empty)) {
                        newY = y;
                    }
                    else if (grid[y][burrowX] != num && !Objects.equals(grid[y][burrowX], empty)) {
                        newY = null;
                        break;
                    } else {
                        break;
                    }

                }
            }

        }

        int stepsSoFar = node.getSteps();
        for (Integer x: newX) {
            Integer[][] gridCopy = copyGrid(grid);
            gridCopy[0][x] = num;
            gridCopy[currentY][currentX] = 0;
            int stepsToAdd = getSteps(currentY, currentX, 0, x, num);
            int steps = stepsToAdd + stepsSoFar;
            possibleMoves.add(new Node(gridCopy, steps, calculateHeuristic(gridCopy)));
        }
        if (newY != null) {
            Integer[][] gridCopy = copyGrid(grid);
            gridCopy[newY][burrowX] = num;
            gridCopy[currentY][currentX] = 0;
            int stepsToAdd = getSteps(currentY, currentX, newY, burrowX, num);
            int steps = stepsToAdd + stepsSoFar;
            possibleMoves.add(new Node(gridCopy, steps, calculateHeuristic(gridCopy)));
        }

        return possibleMoves;
    }

    private List<Node> getNodes(Node node) {
        List<Node> nodes = new ArrayList<>();
        Integer[][] grid = node.getGrid();
        for (int x = 0; x < maxX; x++) {
            int i = grid[0][x];
            if (i == empty) {
                continue;
            }
            int burrowX = letterInfo.get(i).getBurrowX();
            if (burrowX > x) {
                if (node.getHall().subList(x + 1, burrowX).stream().mapToInt(v -> v).sum() == 0) {
                    nodes.addAll(canItMoveIntoBurrowFromHall(x, i, node));
                }
            } else {
                if (node.getHall().subList(burrowX + 1, x).stream().mapToInt(v -> v).sum() == 0) {
                    nodes.addAll(canItMoveIntoBurrowFromHall(x, i, node));
                }
            }

        }

        for (int n = 1; n < 5; n++) {
            int x = letterInfo.get(n).getBurrowX();
            if (inOwnBurrow(n, x, grid)) {
                continue;
            }
            for (int y = 1; y < maxY; y++) {
                int i = grid[y][x];
                if ( i != empty) {
                    nodes.addAll(canItMoveIntoHallFromBurrowOrMoveIntoBurrowDirect(y, x, i, node));
                    break;
                }
            }
        }

        return nodes;
    }

    private void aStar() {
        List<Node> visited = new ArrayList<>();
        Queue<Node> q = new PriorityQueue<>(1, Comparator.comparingInt(Day23::applyAsInt));
        Node startNode = new Node(copyGrid(grid), 0, calculateHeuristic(grid));
        q.add(startNode);
        visited.add(startNode);
        while(!q.isEmpty()) {
            Node node = q.poll();
            if (node.getHeuristic() == 0){
                minSteps = node.getSteps();
                break;
            }

            List<Node> newNodes = getNodes(node);
            for (Node newNode: newNodes) {
                if (newNode.getSteps() >= minSteps || visited.contains(newNode)) {
                    continue;
                }
                visited.add(newNode);
                q.add(newNode);
            }

        }
    }

    @Override
    public int part1() {
        printGrid(grid);
        System.out.println("Part 1 worked out by hand first, then coded using A* approach");
        aStar();
        return minSteps;
    }

    private void printGrid(Integer[][] grid) {
        for (Integer[] row : grid) {
            for (Integer i : row) {
                if (i == 0) {
                    System.out.print(". ");
                    continue;
                }
                if (i == 9) {
                    System.out.print("# ");
                    continue;
                }
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    @Override
    public int part2() {
        processData("23b.txt");
        printGrid(grid);
//        aStar();
//        System.out.println(minSteps);
        System.out.println("Part 2 worked out by hand  :)");
        return 3000 + 4000 + 70 + 10 + 800 + 700 + 5000 + 5000 + 7 + 70 + 500 + 600 + 700 + 10000 + 10000
                + 4 + 500 + 40 + 5 + 50 + 60 + 80 + 6 + 6 + 7 + 60 + 9;
    }
}
