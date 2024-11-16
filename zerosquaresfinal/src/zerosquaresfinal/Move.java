
package zerosquaresfinal;

public class Move {
    public Move() {}

    public State moveRight(State preState) {
              
        State nextState = new State(preState);
      
       for (int i = 0; i < preState.grid.length; i++) {
    for (int j = preState.grid[i].length - 1; j >= 0; j--) {
        if (preState.status[i][j].equals("free")) {
            nextState.grid[i][j] = "⬜";
            nextState.color[i][j] = "white";
            nextState.status[i][j] = "fixed";

            int[] checkResult = checkRight(preState, nextState, i, j);
            boolean end = checkResult[0] == 1;
            int y = checkResult[1];

            if (end) {
                nextState.grid[i][y] = "⬜";
                nextState.color[i][y] = "white";
            } else {
                nextState.grid[i][y] = preState.grid[i][j];
                nextState.color[i][y] = preState.color[i][j];
                nextState.status[i][y] = preState.status[i][j];
            }
            return nextState;
        }
    }
}
return nextState;
    }

    public State moveLeft(State preState) {
               
        State nextState = new State(preState);
       
        for (int i = 0; i < preState.grid.length; i++) {
            for (int j = 0; j < preState.grid[i].length; j++) {
                if (preState.status[i][j].equals("free")) {
                    nextState.grid[i][j] = "⬜";
                    nextState.color[i][j] = "white";
                    nextState.status[i][j] = "fixed";
                    boolean end;
                    int y;
                    int[] checkResult = checkLeft(preState, nextState, i, j);
                    end = checkResult[0] == 1;
                   
                    y = checkResult[1];
                    if (end) {
                        nextState.grid[i][y] = "⬜";
                        nextState.color[i][y] = "white";
                    } else {
                        nextState.grid[i][y] = preState.grid[i][j];
                        nextState.color[i][y] = preState.color[i][j];
                        nextState.status[i][y] = preState.status[i][j];
                    }
                    return nextState;
                }
            }
        }
        return nextState;
    }

    public State moveUp(State preState) {
                
       
        State nextState = new State(preState);
         
        for (int i = 0; i < preState.grid.length; i++) {
            for (int j = 0; j < preState.grid[i].length; j++) {
                if (preState.status[i][j].equals("free")) {
                    nextState.grid[i][j] = "⬜";
                    nextState.color[i][j] = "white";
                    nextState.status[i][j] = "fixed";
                    boolean end;
                    int x;
                    int[] checkResult = checkUp(preState, nextState, i, j);
                    end = checkResult[0] == 1;
                
                    x = checkResult[1];
                    if (end) {
                        nextState.grid[x][j] = "⬜";
                        nextState.color[x][j] = "white";
                    } else {
                        nextState.grid[x][j] = preState.grid[i][j];
                        nextState.color[x][j] = preState.color[i][j];
                        nextState.status[x][j] = preState.status[i][j];
                    }
                    return nextState;
                }
            }
        }
        return nextState;
    }

    public State moveDown(State preState) {
             
        State nextState = new State(preState);
        
        for (int i = preState.grid.length - 1; i >= 0; i--) {
            for (int j = 0; j < preState.grid[i].length; j++) {
                if (preState.status[i][j].equals("free")) {
                    nextState.grid[i][j] = "⬜";
                    nextState.color[i][j] = "white";
                    nextState.status[i][j] = "fixed";
                    boolean end;
                    int x;
                    int[] checkResult = checkDown(preState, nextState, i, j);
                    end = checkResult[0] == 1;
                    // System.out.print("the value of end after moving is "+end);
                    x = checkResult[1];
                    if (end) {
                        nextState.grid[x][j] = "⬜";
                        nextState.color[x][j] = "white";
                    } else {
                        nextState.grid[x][j] = preState.grid[i][j];
                        nextState.color[x][j] = preState.color[i][j];
                        nextState.status[x][j] = preState.status[i][j];
                    }
                    return nextState;
                }
            }
        }
        return nextState;
    }

    private int[] checkRight(State preState, State nextState, int x, int y) {
        boolean end = false;
        int j = y;
        //////  للفحص انو لساتنا ضمن حدود الرقعة
       // System.out.println("the prestate is lenght"+preState.grid[x].length);
       // System.out.println("the prestate.status is "+preState.color[x][y+1]);
        if (y < preState.grid[x].length - 1 && preState.status[x][y + 1].equals("free")) {
            return new int[]{0, j};
        }
        //لفحص انو ماوصلنا لنقطة نهاية ولساتنا ضمن حدود الرقعة 
        while (!end && j < preState.grid[x].length - 1) {
            // هون مشان اذا كان في قدامي حجر تاني متحرك او عائق اسود
            if (nextState.status[x][j + 1].equals("free") || nextState.color[x][j + 1].equals("black")) {
                break;
            }// هون مشان نتحقق اذا وصلنا لموقع الهدف 
            if (preState.color[x][j + 1].equals(preState.color[x][y])) {
                end = true;
                j += 1;
                break;
            }
            // هلق هي هون مشان بس اذا ماوصلنا للهدف بس قدامنا مربع فاضي فخلص تحرك يمين بس 
            j += 1;
        }
        return new int[]{end ? 1 : 0, j};
    }

    private int[] checkLeft(State preState, State nextState, int x, int y) {
        boolean end = false;
        int j = y;
        if (y > 0 && preState.status[x][y - 1].equals("free")) {
            return new int[]{0, j};
        }
        while (!end && j > 0) {
            if (nextState.status[x][j - 1].equals("free") || nextState.color[x][j - 1].equals("black")) {
                break;
            }
            if (preState.color[x][j - 1].equals(preState.color[x][y])) {
                end = true;
                j -= 1;
                break;
            }
            j -= 1;
        }
        return new int[]{end ? 1 : 0, j};
    }

    private int[] checkUp(State preState, State nextState, int x, int y) {
        boolean end = false;
        int i = x;
        if (x > 0 && preState.status[x - 1][y].equals("free")) {
            return new int[]{0, i};
        }
        while (!end && i > 0) {
            if (nextState.status[i - 1][y].equals("free") || nextState.color[i - 1][y].equals("black")) {
                break;
            }
            if (preState.color[i - 1][y].equals(preState.color[x][y])) {
                end = true;
                i -= 1;
                break;
            }
            i -= 1;
        }
        return new int[]{end ? 1 : 0, i};
    }

    private int[] checkDown(State preState, State nextState, int x, int y) {
        boolean end = false;
        int i = x;
        if (x < preState.grid.length - 1 && preState.status[x + 1][y].equals("free")) {
            return new int[]{0, i};
        }
        while (!end && i < preState.grid.length - 1) {
            if (nextState.status[i + 1][y].equals("free") || nextState.color[i + 1][y].equals("black")) {
                break;
            }
            if (preState.color[i + 1][y].equals(preState.color[x][y])) {
                end = true;
                i += 1;
                break;
            }
            i += 1;
        }
        return new int[]{end ? 1 : 0, i};
    }
}

