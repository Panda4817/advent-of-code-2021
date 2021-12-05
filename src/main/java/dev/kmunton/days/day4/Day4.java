package dev.kmunton.days.day4;

import dev.kmunton.days.Day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day4  implements Day {

    private List<Board> boards;
    private List<Integer> drawNumbers;
    private int size;

    public Day4(String filename) {
        processData(filename);
        size = 5;
    }

    @Override
    public void processData(String filename) {
        try {
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(filename)).getFile());
            Scanner myReader = new Scanner(file);
            boards = new ArrayList<>();
            Board board = new Board();
            int i = 0;
            while (myReader.hasNextLine()) {
                String s = myReader.nextLine();
                if (i == 0) {
                    List<String> lst = new ArrayList<String>(List.of(s.split(",")));
                    drawNumbers = lst.stream().map(Integer::valueOf).collect(Collectors.toList());
                    i += 1;
                    continue;
                }
                if (s == "") {
                    if (board.getNumbers().size() != 0) {
                        boards.add(board);
                        board = new Board();
                    }
                    continue;
                }
                List<Integer> row = new ArrayList<String>(List.of(s.split(" "))).stream().filter(c -> c != "").map(Integer::valueOf).collect(Collectors.toList());
                board.addRow(row);
            }
            boards.add(board);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private Boolean winnerFound() {
        for (Board b: boards){
            if (b.isWinner()) {
                return true;
            }
        }
        return false;
    }

    private Board winner() {
        for (Board b: boards){
            if (b.isWinner()) {
                return b;
            }
        }
        return new Board();
    }

    private List<Integer> winnerIndex() {
        int i = -1;
        List<Integer> winners = new ArrayList<>();
        for (Board b: boards){
            i += 1;
            if (b.isWinner()) {
                winners.add(i);
            }
        }
        return winners;
    }

    private void markOnBoards(Integer num) {
        for (Board b: boards){
            b.markIfOnBoard(num);
        }
    }

    @Override
    public int part1() {

        Integer drawnNumber = 0;
        while(!winnerFound() && drawNumbers.size() > 0) {

            drawnNumber = drawNumbers.remove(0);
            markOnBoards(drawnNumber);
        }

        int score = winner().calculateScore(drawnNumber);
        return score;
    }

    @Override
    public int part2() {
        Integer drawnNumber = 0;
        while(drawNumbers.size() > 0) {

            drawnNumber = drawNumbers.remove(0);
            markOnBoards(drawnNumber);
            List<Integer> indexes = winnerIndex();
            int adjustIndex = 0;
            while (boards.size() > 1 && indexes.size() > 0) {
                int index = indexes.remove(0);
                index -= adjustIndex;
                boards.remove(index);
                adjustIndex += 1;

            }
             if (boards.size() == 1 && winnerFound()) {
                 break;
             }
        }

        int score = boards.get(0).calculateScore(drawnNumber);
        return score;
    }
}
