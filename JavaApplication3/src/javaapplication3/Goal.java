package javaapplication3;

import java.awt.*;

public class Goal {
    private char cell;
    private int x;
    private int y;
    private Color color;

    Goal(char cell, int x, int y, Color color) {
        this.cell = cell;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public char getC() {
        return cell;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }
}
