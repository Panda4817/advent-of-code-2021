package dev.kmunton.days.day13;

import dev.kmunton.days.day5.Point;

public class Dot extends Point {
    public Dot(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return getX() + "," + getY();
    }


}
