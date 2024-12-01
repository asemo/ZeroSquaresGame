package zerosquaresfinal;


import java.util.Arrays;


public class State {
    public String[][] grid;
    public String[][] color;
    public String[][] status;
    public State parent;
    public int cost;

    public State(String[][] grid, String[][] color, String[][] status) {
        this.grid = grid;
        this.color = color;
        this.status = status;
    }

////هون طبقنا مفهوم ال deep Copy 
    // يعني رح ننشئ مصفوفات لكل حالة وننسخ اليها كل سطر من المصفوفات يعني نسخ مشان ما نعدل على الاصلية
public State(State preState) {
    this.grid = new String[preState.grid.length][];
    this.color = new String[preState.color.length][];
    this.status = new String[preState.status.length][];
    
    for (int i = 0; i < preState.grid.length; i++) {
        this.grid[i] = preState.grid[i].clone();
    }
    for (int i = 0; i < preState.color.length; i++) {
        this.color[i] = preState.color[i].clone();
    }
    for (int i = 0; i < preState.status.length; i++) {
        this.status[i] = preState.status[i].clone();
    }
    this.parent = preState.parent;  // نسخ خاصية الأب
}


    

    public String show() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                str.append(grid[i][j]).append(" ");
            }
            str.append("\n");
        }
        return str.toString();
    }

    public boolean checkWin() {
        for (int i = 0; i < status.length; i++) {
            for (int j = 0; j < status[i].length; j++) {
                if ("free".equals(status[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }
}



