package javaapplication5;

import java.awt.*;

public class Stone {
    private char cell;
    private Color color;
    private int x;
    private int y;
    private boolean inGoal;

    Stone(char cell, Color color, int x, int y, boolean inGoal) {
        this.cell = cell;
        this.color = color;
        this.x = x;
        this.y = y;
        this.inGoal = inGoal;
    }

    public char getC() {
        return cell;
    }

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isInGoal() {
        return inGoal;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setInGoal(boolean inGoal) {
        this.inGoal = inGoal;
    }
}
