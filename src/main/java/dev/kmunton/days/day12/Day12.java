package dev.kmunton.days.day12;

import dev.kmunton.days.Day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day12 implements Day {

    private final Graph graph = new Graph();

    public Day12(String filename) {
        processData(filename);
    }

    @Override
    public void processData(String filename) {
        try {
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(filename)).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String s = myReader.nextLine();
                String[] caves = s.split("-");
                graph.addVertex(caves[0]);
                graph.addVertex(caves[1]);
                graph.addEdge(caves[0], caves[1]);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private boolean isSmall(String s) {
        if (s == "start" || s == "end") {
            return false;
        }

        for (char c : s.toCharArray()) {
            int n = (int) c;
            if (n >= 97 && n <= 122) {
                return true;
            }
        }
        return false;
    }

    private boolean smallCheck(List<Vertex> lst) {
        Map<String, Integer> count = new HashMap<>();
        for (Vertex n: lst) {
            if (isSmall(n.getLabel())) {
                int num = count.getOrDefault(n.getLabel(), 0);
                count.put(n.getLabel(), num + 1);
            }

        }
        return count.values().stream().filter(i -> i > 1).count() == 0;
    }



    private void recurse(Graph graph, Vertex vertex, List<Vertex> current, List<List<Vertex>> paths, boolean part1) {
        if (Objects.equals(vertex.getLabel(), "end")) {
            paths.add(current);
            return;
        }

        for (Vertex v : graph.getAdjVertices(vertex.getLabel())) {
            if (Objects.equals(v.getLabel(), "start")) {
                continue;
            }

            if (part1) {
                if (isSmall(v.getLabel()) && current.contains(v)) {
                    continue;
                }
            } else {
                if (isSmall(v.getLabel()) && current.contains(v) && !smallCheck(current)) {
                    continue;
                }
            }

            List<Vertex> currentPath = new ArrayList<>(current);
            currentPath.add(v);
            recurse(graph, v, currentPath, paths, part1);
        }
    }

    @Override
    public int part1() {
        List<List<Vertex>> paths = new ArrayList<>();
        List<Vertex> lst = new ArrayList<>();
        Vertex start = new Vertex("start");
        lst.add(start);
        recurse(graph, start, lst, paths, true);
        return paths.size();
    }

    @Override
    public int part2() {
        List<List<Vertex>> paths = new ArrayList<>();
        List<Vertex> lst = new ArrayList<>();
        Vertex start = new Vertex("start");
        lst.add(start);
        recurse(graph, start, lst, paths, false);
        return paths.size();
    }
}
