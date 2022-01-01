package dev.kmunton.days.day18;

import dev.kmunton.days.Day;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day18 implements Day {

    private SnailfishNumber root;
    private Stack<SnailfishNumber> snailfishNumberStack;
    private final List<Character> numList = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

    public Day18(String filename) {
        processData(filename);
    }

    @Override
    public void processData(String filename) {
        try {
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(filename)).getFile());
            Scanner myReader = new Scanner(file);
            snailfishNumberStack = new Stack<>();
            while (myReader.hasNextLine()) {
                String s = myReader.nextLine();
                Stack<Character> stack = new Stack<>();
                for (Character c : s.toCharArray()) {
                    if (c.equals('[') || numList.contains(c)) {
                        stack.push(c);
                    } else if (c.equals(']')){
                        SnailfishNumber rightSnailfishNumber = null;
                        SnailfishNumber leftSnailfishNumber = null;
                        String rightString = stack.pop().toString();
                        Integer rightNum = null;
                        if (rightString.equals("n")) {
                            rightSnailfishNumber = snailfishNumberStack.pop();
                        } else {
                            rightNum = Integer.parseInt(rightString);
                        }
                        String leftString = stack.pop().toString();
                        Integer leftNum = null;
                        if (leftString.equals("n")) {
                            leftSnailfishNumber = snailfishNumberStack.pop();
                        } else {
                            leftNum = Integer.parseInt(leftString);
                        }
                        stack.pop();
                        SnailfishNumber snailfishNumber = new SnailfishNumber(leftSnailfishNumber, rightSnailfishNumber, leftNum, rightNum);
                        snailfishNumber.setParent(null);
                        if (snailfishNumber.getLeft().isPresent()) {
                            snailfishNumber.getLeft().get().setParent(snailfishNumber);
                        }
                        if (snailfishNumber.getRight().isPresent()) {
                            snailfishNumber.getRight().get().setParent(snailfishNumber);
                        }
                        snailfishNumberStack.push(snailfishNumber);
                        stack.push('n');
                    }
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

    private SnailfishNumber addition(SnailfishNumber right) {
        SnailfishNumber newRoot = new SnailfishNumber(root, right, null, null);
        newRoot.setParent(null);
        newRoot.getLeft().get().setParent(newRoot);
        newRoot.getRight().get().setParent(newRoot);
        newRoot.reduction();
        return newRoot;
    }

    private void printParent(SnailfishNumber node) {
        if (node.getLeft().isPresent()) {
            printParent(node.getLeft().get());
        }
        if (node.getRight().isPresent()) {
            printParent(node.getRight().get());
        }

    }

    @Override
    public int part1() {
        List<SnailfishNumber> snailfishNumberList = new ArrayList<>(snailfishNumberStack.stream().toList());
        SnailfishNumber left = snailfishNumberList.remove(0);
        SnailfishNumber right = snailfishNumberList.remove(0);
        root = new SnailfishNumber(left, right, null, null);
        root.setParent(null);
        root.getLeft().get().setParent(root);
        root.getRight().get().setParent(root);
        root.reduction();
        while(!snailfishNumberList.isEmpty()) {
            right = snailfishNumberList.remove(0);
            root = addition(right);
        }
        System.out.println(root);
        System.out.println(root.calculateMagnitude(root));
        return 0;
    }

    @Override
    public int part2() {
        processData("18.txt");
        List<SnailfishNumber> snailfishNumberList = new ArrayList<>(snailfishNumberStack.stream().toList());
        long largestMagnitude = 0;
        int size = snailfishNumberList.size();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++ ) {
                if (i == j) {
                    continue;
                }
                SnailfishNumber left = new SnailfishNumber(snailfishNumberList.get(i));
                SnailfishNumber right = new SnailfishNumber(snailfishNumberList.get(j));
                SnailfishNumber possibleOne = new SnailfishNumber(left, right, null, null);
                possibleOne.setParent(null);
                possibleOne.getLeft().get().setParent(possibleOne);
                possibleOne.getRight().get().setParent(possibleOne);
                possibleOne.reduction();
                long m = possibleOne.calculateMagnitude(possibleOne);
                if (m > largestMagnitude) {
                    largestMagnitude = m;
                }

                right = new SnailfishNumber(snailfishNumberList.get(i));

                left = new SnailfishNumber(snailfishNumberList.get(j));

                SnailfishNumber possibleTwo = new SnailfishNumber(left, right, null, null);
                possibleTwo.setParent(null);
                possibleTwo.getLeft().get().setParent(possibleTwo);
                possibleTwo.getRight().get().setParent(possibleTwo);
                possibleTwo.reduction();
                long m2 = possibleTwo.calculateMagnitude(possibleTwo);
                if (m2 > largestMagnitude) {
                    largestMagnitude = m2;
                }

            }
        }
        System.out.println(largestMagnitude);
        return 0;
    }

}
