package javaapplication5;

import java.util.ArrayList;
import java.util.Arrays;

public class Boardlogic {
    int gridX, gridY;
    char[][] board;
    ArrayList<Stone> stones = new ArrayList<>();
    ArrayList<Goal> goals = new ArrayList<>();

    boolean finished = false;

    // Constructor
    Boardlogic() {}

    Boardlogic(int gridX, int gridY, char[][] board, ArrayList<Stone> stones, ArrayList<Goal> goals) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.board = board;
        this.stones = stones;
        this.goals = goals;
    }

    // تنفيذ الحركة
public Boardlogic move(String dir) {
    // نسخ اللوحة الحالية
    Boardlogic boardLogic = new Boardlogic(gridX, gridY, deepCopyBoard(), copyStones(), copyGoals());
    int[][] canMove = new int[stones.size()][2];
    boolean[] getInGoal = new boolean[stones.size()];

    for (int i = 0; i < stones.size(); i++) {
        Stone s = stones.get(i);
        canMove[i][0] = s.getX();
        canMove[i][1] = s.getY();
        getInGoal[i] = false;
    }

    // تنفيذ الحركة حسب الاتجاه
    for (int i = 0; i < boardLogic.stones.size(); i++) {
        Stone s = boardLogic.stones.get(i);
        if (s.isInGoal()) continue;
        int tempX = s.getX(), tempY = s.getY();

        switch (dir) {
            case "UP":    tempX = moveVertically(s, -1, getInGoal, i); break;
            case "DOWN":  tempX = moveVertically(s, 1, getInGoal, i); break;
            case "LEFT":  tempY = moveHorizontally(s, -1, getInGoal, i); break;
            case "RIGHT": tempY = moveHorizontally(s, 1, getInGoal, i); break;
        }

        canMove[i][0] = tempX;
        canMove[i][1] = tempY;
    }

    // تحديث المواقع
    for (int i = 0; i < boardLogic.stones.size(); i++) {
        Stone s = boardLogic.stones.get(i);
        if (s.isInGoal()) continue;
        s.setX(canMove[i][0]);
        s.setY(canMove[i][1]);
        if (getInGoal[i]) {
            s.setInGoal(true);
            clearGoal(boardLogic, s);
            System.out.println("You win");
        }
    }
    return boardLogic; // أعد النسخة الجديدة
}


    // تابع الحركة العمودية
    private int moveVertically(Stone s, int step, boolean[] getInGoal, int i) {
    int temp = s.getX() + step;
    while (temp >= 0 && temp < gridX && checkIfYouCanWalk(temp, s.getY())) {
        // تحقق من الهدف فقط، وتجاهل `flag`
        if (board[temp][s.getY()] == Character.toUpperCase(s.getC())) {
            getInGoal[i] = true;
            break;
        }
        temp += step;
    }
    return temp - step;
}


    // تابع الحركة الأفقية
   private int moveHorizontally(Stone s, int step, boolean[] getInGoal, int i) {
    int temp = s.getY() + step;
    while (temp >= 0 && temp < gridY && checkIfYouCanWalk(s.getX(), temp)) {
        // تحقق من الهدف فقط، وتجاهل `flag`
        if (board[s.getX()][temp] == Character.toUpperCase(s.getC())) {
            getInGoal[i] = true;
            break;
        }
        temp += step;
    }
    return temp - step;
}


    // إزالة الهدف عند وصول الحجر إليه
    private void clearGoal(Boardlogic boardLogic, Stone s) {
        for (Goal g : goals) {
            if (g.getC() == Character.toUpperCase(s.getC())) {
                boardLogic.board[g.getX()][g.getY()] = '_';
            }
        }
    }

    // التحقق من إمكانية التحرك إلى موقع معين
    private boolean checkIfYouCanWalk(int i, int j) {
        if (board[i][j] == '#') return false;
        for (Stone s : stones) {
            if (!s.isInGoal() && s.getX() == i && s.getY() == j) return false;
        }
        return true;
    }

    // التحقق من انتهاء اللعبة
    public boolean checkGameOver() {
        for (Stone stone : stones) {
            if (!stone.isInGoal()) return false;
        }
        finished = true;
        return true;
    }

    // عمل نسخة عميقة من اللوحة
    private char[][] deepCopyBoard() {
        char[][] copy = new char[gridX][gridY];
        for (int i = 0; i < gridX; i++) System.arraycopy(board[i], 0, copy[i], 0, gridY);
        return copy;
    }

    // نسخ قائمة الأحجار
    private ArrayList<Stone> copyStones() {
        ArrayList<Stone> newStones = new ArrayList<>();
        for (Stone s : stones) {
            newStones.add(new Stone(s.getC(), s.getColor(), s.getX(), s.getY(), s.isInGoal()));
        }
        return newStones;
    }

    // نسخ قائمة الأهداف
    private ArrayList<Goal> copyGoals() {
        ArrayList<Goal> newGoals = new ArrayList<>();
        for (Goal g : goals) {
            newGoals.add(new Goal(g.getC(), g.getX(), g.getY(), g.getColor()));
        }
        return newGoals;
    }

    // طباعة اللوحة
    public void printGrid() {
        for (char[] row : board) {
            System.out.println(Arrays.toString(row));
        }
    }
}
