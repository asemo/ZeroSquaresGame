package javaapplication1;


import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Board {
    private int size;
    private Square[][] grid;
    private List<Color> colors;
    private List<Point> movablePositions;  // لحفظ إحداثيات المربعات القابلة للتحريك

    public Board(int size, int fixedSquareCount, int movableAndTargetCount) {
        this.size = size;
        grid = new Square[size][size];
        colors = generateColors(movableAndTargetCount); // إنشاء قائمة بالألوان
        movablePositions = new ArrayList<>();

        // تهيئة كل المربعات كفراغات
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Square(Square.SquareType.EMPTY, Color.WHITE);
            }
        }

        addRandomFixedSquares(fixedSquareCount);          // إضافة مربعات ثابتة
        addRandomTargetsAndMovables(movableAndTargetCount); // إضافة مربعات هدف وملونة متطابقة اللون
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
    private void addRandomTargetsAndMovables(int count) {
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

    // تحقق مما إذا كان بالإمكان تحريك المربع القابل للتحريك للأعلى
    private boolean canMoveUp(Point movablePos) {
        int x = movablePos.x;
        int y = movablePos.y;
        boolean canMove = isWithinBounds(x - 1, y) && grid[x - 1][y].getType() == Square.SquareType.EMPTY;
        System.out.println("Can move up from (" + x + ", " + y + "): " + canMove);
        return canMove;
    }

    // تحقق مما إذا كان بالإمكان تحريك المربع القابل للتحريك للأسفل
    private boolean canMoveDown(Point movablePos) {
        int x = movablePos.x;
        int y = movablePos.y;
        boolean canMove = isWithinBounds(x + 1, y) && grid[x + 1][y].getType() == Square.SquareType.EMPTY;
        System.out.println("Can move down from (" + x + ", " + y + "): " + canMove);
        return canMove;
    }

    // تحقق مما إذا كان بالإمكان تحريك المربع القابل للتحريك لليسار
    private boolean canMoveLeft(Point movablePos) {
        int x = movablePos.x;
        int y = movablePos.y;
        boolean canMove = isWithinBounds(x, y - 1) && grid[x][y - 1].getType() == Square.SquareType.EMPTY;
        System.out.println("Can move left from (" + x + ", " + y + "): " + canMove);
        return canMove;
    }

    // تحقق مما إذا كان بالإمكان تحريك المربع القابل للتحريك لليمين
    private boolean canMoveRight(Point movablePos) {
        int x = movablePos.x;
        int y = movablePos.y;
        boolean canMove = isWithinBounds(x, y + 1) && grid[x][y + 1].getType() == Square.SquareType.EMPTY;
        System.out.println("Can move right from (" + x + ", " + y + "): " + canMove);
        return canMove;
    }

    // دالة لتحريك جميع المربعات الملونة في اتجاه معين
    public void moveMovableSquares(int dx, int dy) {
        // فرز المواقع لتجنب التداخل
        if (dx == 1) {
            Collections.sort(movablePositions, (a, b) -> Integer.compare(b.x, a.x)); // تحريك لأسفل
        } else if (dx == -1) {
            Collections.sort(movablePositions, (a, b) -> Integer.compare(a.x, b.x)); // تحريك لأعلى
        } else if (dy == 1) {
            Collections.sort(movablePositions, (a, b) -> Integer.compare(b.y, a.y)); // تحريك لليمين
        } else if (dy == -1) {
            Collections.sort(movablePositions, (a, b) -> Integer.compare(a.y, b.y)); // تحريك لليسار
        }

        // تحريك كل مربع قابل للحركة
        for (Point pos : movablePositions) {
            int x = pos.x;
            int y = pos.y;
            Color originalColor = grid[x][y].getColor();

            // تحقق من إمكانية الحركة في الاتجاه المحدد
            boolean canMove = false;
            if (dy == -1) {
                canMove = canMoveLeft(pos);
            } else if (dy == 1) {
                canMove = canMoveRight(pos);
            } else if (dx == -1) {
                canMove = canMoveUp(pos);
            } else if (dx == 1) {
                canMove = canMoveDown(pos);
            }

            if (canMove) {
                // تابع التحريك في الاتجاه المحدد
                if (dy == -1) {
                    while (isWithinBounds(x, y - 1) && grid[x][y - 1].getType() == Square.SquareType.EMPTY) {
                        grid[x][y - 1] = new Square(Square.SquareType.MOVABLE, originalColor);
                        grid[x][y] = new Square(Square.SquareType.EMPTY, Color.WHITE);
                        y--;
                    }
                } else if (dy == 1) {
                    while (isWithinBounds(x, y + 1) && grid[x][y + 1].getType() == Square.SquareType.EMPTY) {
                        grid[x][y + 1] = new Square(Square.SquareType.MOVABLE, originalColor);
                        grid[x][y] = new Square(Square.SquareType.EMPTY, Color.WHITE);
                        y++;
                    }
                } else if (dx == -1) {
                    while (isWithinBounds(x - 1, y) && grid[x - 1][y].getType() == Square.SquareType.EMPTY) {
                        grid[x - 1][y] = new Square(Square.SquareType.MOVABLE, originalColor);
                        grid[x][y] = new Square(Square.SquareType.EMPTY, Color.WHITE);
                        x--;
                    }
                } else if (dx == 1) {
                    while (isWithinBounds(x + 1, y) && grid[x + 1][y].getType() == Square.SquareType.EMPTY) {
                        grid[x + 1][y] = new Square(Square.SquareType.MOVABLE, originalColor);
                        grid[x][y] = new Square(Square.SquareType.EMPTY, Color.WHITE);
                        x++;
                    }
                }

                // التحقق إذا كان المربع القابل للحركة يقف على مربع الهدف ويطابقه في اللون
                if (isWithinBounds(x, y) && grid[x][y].getType() == Square.SquareType.TARGET &&
                    grid[x][y].getColor().equals(originalColor)) {
                    // تغيير لون مربع الهدف إلى الأبيض
                    grid[x][y] = new Square(Square.SquareType.EMPTY, Color.WHITE);
                }
                // تحديث الموقع النهائي للمربع القابل للتحريك
                pos.setLocation(x, y);
            } else {
                System.out.println("Cannot move from (" + x + ", " + y + ") in the direction (" + dx + ", " + dy + ")");
            }
        }
    }

    // تحقق مما إذا كانت جميع المربعات الملونة متطابقة مع مربعات الهدف
    public boolean checkGoalAchieved() {
        for (Point pos : movablePositions) {
            if (grid[pos.x][pos.y].getType() != Square.SquareType.TARGET ||
                !grid[pos.x][pos.y].getColor().equals(grid[pos.x][pos.y].getColor())) {
                return false;
            }
        }
        return true;
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




