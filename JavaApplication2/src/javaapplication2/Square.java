
package javaapplication2;

import java.awt.Color;



import java.awt.Color;

public class Square {
    // الأنواع المختلفة للمربعات
    public enum SquareType {
        EMPTY,      // مربع فارغ
        FIXED,      // مربع ثابت يمنع الحركة
        MOVABLE,    // مربع ملون قابل للتحريك
        TARGET      // مربع الهدف
    }

    private SquareType type; // نوع المربع
    private Color color;      // لون المربع
    private Color borderColor;

    // مُنشئ المربع
    public Square(SquareType type, Color color,Color borderColor) {
        this.type = type;
        this.color = color;
        this.borderColor = borderColor;
    }
        // مُنشئ المربع
    public Square(SquareType type, Color color) {
        this.type = type;
        this.color = color;
        this.borderColor = null;
    }

    // getters and setters
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
     public Color getBorderColor() {
        return borderColor;
     }
}


