
package javaapplication2;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javaapplication2.Square;

public class Board {
    private int size;
    private Square[][] grid;
    private List<Color> colors;
    private List<Point> movablePositions;  // لحفظ إحداثيات المربعات القابلة للتحريك
    //private Map<Point, List<Point>> paths;
    public Board(int size, int fixedSquareCount, int movableAndTargetCount) {
        this.size = size;
        grid = new Square[size][size];
        colors = generateColors(movableAndTargetCount); // إنشاء قائمة بالألوان
        movablePositions = new ArrayList<>();
       // paths = new HashMap<>();

        // تهيئة كل المربعات كفراغات
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Square(Square.SquareType.EMPTY, Color.WHITE);
            }
        }

        addRandomFixedSquares(fixedSquareCount);          // إضافة مربعات ثابتة
        addRandomMovableSquares(movableAndTargetCount); // إضافة مربعات هدف وملونة متطابقة اللون
    }

    // إنشاء قائمة بألوان عشوائية للمربعات الملونة ومربعات الهدف
    private List<Color> generateColors(int count) {
        List<Color> colors = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            colors.add(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        }
        return colors;
    }

    // إضافة مربعات ثابتة عشوائيًا
    private void addRandomFixedSquares(int count) {
        Random random = new Random();
        int added = 0;

        while (added < count) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);

            if (grid[x][y].getType() == Square.SquareType.EMPTY) {
                grid[x][y] = new Square(Square.SquareType.FIXED, Color.BLACK);
                added++;
            }
        }
    }

    // إضافة مربعات ملونة ومربعات هدف بألوان متطابقة
  /**
   *     private void addRandomTargetsAndMovables(int count) {
        Random random = new Random();
        int added = 0;

        while (added < count) {
            // تحديد لون معين للزوج من المربع الملون ومربع الهدف
            Color color = colors.get(added);

            // إضافة مربع هدف
            int targetX, targetY;
            do {
                targetX = random.nextInt(size);
                targetY = random.nextInt(size);
            } while (grid[targetX][targetY].getType() != Square.SquareType.EMPTY);

            grid[targetX][targetY] = new Square(Square.SquareType.TARGET, color);

            // إضافة مربع ملون
            int movableX, movableY;
            do {
                movableX = random.nextInt(size);
                movableY = random.nextInt(size);
            } while (grid[movableX][movableY].getType() != Square.SquareType.EMPTY);

            grid[movableX][movableY] = new Square(Square.SquareType.MOVABLE, color);
            movablePositions.add(new Point(movableX, movableY)); // حفظ موقع المربع القابل للتحريك

            added++;
        }
        
        
    }
   * 
   */
    
 // دالة لإضافة مربعات قابلة للتحريك عشوائيًا
    private void addRandomMovableSquares(int count) {
        Random random = new Random();
        int added = 0;

        while (added < count) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);

            if (grid[x][y].getType() == Square.SquareType.EMPTY) {
                Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                grid[x][y] = new Square(Square.SquareType.MOVABLE, color);
                Point movablePosition=new Point(x,y);
                // حفظ موقع المربع القابل للتحريك
                movablePositions.add(movablePosition);
               //  paths.put(movablePosition, new ArrayList<>());
                // paths.get(movablePosition).add(movablePosition);
                // طباعة موقع المربع القابل للتحريك
        System.out.println("تم إنشاء مربع قابل للتحريك في الموقع: (" + y + ", " + x + ")");
                added++;
            }
        }
    }

   
    
 // دالة لتحريك جميع المربعات القابلة للتحريك في اتجاه واحد
    public void moveMovableSquares(int dx, int dy) {
    // فرز المواقع حسب الاتجاه
    if (dx == 1) {
        Collections.sort(movablePositions, (a, b) -> Integer.compare(b.x, a.x)); // تحريك للأسفل
    } else if (dx == -1) {
        Collections.sort(movablePositions, (a, b) -> Integer.compare(a.x, b.x)); // تحريك للأعلى
    } else if (dy == 1) {
        Collections.sort(movablePositions, (a, b) -> Integer.compare(b.y, a.y)); // تحريك لليمين
    } else if (dy == -1) {
        Collections.sort(movablePositions, (a, b) -> Integer.compare(a.y, b.y)); // تحريك لليسار
    }

    // تحريك كل مربع قابل للحركة
    for (Point pos : movablePositions) {
        int x = pos.x;
        int y = pos.y;
        Color color = grid[x][y].getColor();
       // List<Point> path = paths.get(pos);

        // التحقق مما إذا كان بإمكان المربع التحرك في الاتجاه المطلوب
        if (isWithinBounds(x + dx, y + dy) && grid[x + dx][y + dy].getType() == Square.SquareType.EMPTY) {
            System.out.println("يمكن تحريك المربع في (" + x + ", " + y + ") إلى الاتجاه المطلوب.");
            // تابع التحريك حتى الاصطدام بعائق
            while (isWithinBounds(x + dx, y + dy) && grid[x + dx][y + dy].getType() == Square.SquareType.EMPTY) {
                grid[x + dx][y + dy] = new Square(Square.SquareType.MOVABLE, color);
                grid[x][y] = new Square(Square.SquareType.EMPTY, Color.WHITE);
                
                x += dx;
                y += dy;
                // path.add(new Point(x, y));
            }
            // تحديث الموقع النهائي
            pos.setLocation(x, y);
        } else {
            System.out.println("لا يمكن تحريك المربع في (" + x + ", " + y + ") إلى الاتجاه المطلوب.");
        }
        
    }
    
}

    
    
    
    
    
    
    
    
    
    
    
    // عرض الرقعة
     public void display(Graphics g) {
        int cellSize = 50;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                g.setColor(grid[i][j].getColor());
                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                g.setColor(Color.GRAY);
                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }

    // تحقق من أن الإحداثيات ضمن الحدود
    private boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }
    
}




