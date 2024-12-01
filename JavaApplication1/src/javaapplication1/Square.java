
package javaapplication1;


import java.awt.Color;

public class Square {
    // SquareType.java
public enum SquareType {
    EMPTY,      // مربع فارغ
    FIXED,      // مربع ثابت يمنع الحركة
    MOVABLE,    // مربع ملون قابل للتحريك
    TARGET      // مربع الهدف
}

    private SquareType type;
    private Color color;

    public Square(SquareType type, Color color) {
        this.type = type;
        this.color = color;
    }

    public SquareType getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public void setType(SquareType type) {
        this.type = type;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

