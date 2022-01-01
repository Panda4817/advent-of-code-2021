package dev.kmunton.days.day17;

import dev.kmunton.days.Day;

public class Day17 implements Day {

    private int startX;
    private int endX;
    private int startY;
    private int endY;

    public Day17(String filename) {
        processData(filename);
    }

    @Override
    public void processData(String filename) {
        // hard coded values from input txt
        startX = 269;
        startY = -44;
        endX = 292;
        endY = -68;
    }

    private boolean inTargetXY(int x, int y) {
        int sx = 0;
        int sy = 0;
        while(true) {
            sx += x;
            sy += y;
            if (sx >= startX && sx <= endX && sy <= startY && sy >= endY) {
                return true;
            } else if (sx > endX || sy < endY) {
                break;
            }
            if (x > 0) {
                x -= 1;
            }
            y -= 1;

        }
        return false;
    }

    @Override
    public int part1() {
        int maxY = Math.abs(endY) - 1;
        int start = 0;
        while( start > endY) {
            start += maxY;
            if (maxY > 0) {
                maxY -= 1;
            } else {
                break;
            }
        }
        return start;
    }

    @Override
    public int part2() {
        int minY = endY;
        int maxY = Math.abs(endY) - 1;

        int maxX = endX;
        int minX = 1;

        int count = 0;

        for (int x = minX; x < maxX + 1; x++) {
            for (int y = minY; y < maxY + 1; y++) {
                if (inTargetXY(x, y)) {
                    count += 1;
                }
            }
        }

        return count;
    }
}
