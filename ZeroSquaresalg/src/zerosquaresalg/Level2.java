package zerosquaresfinal;


import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Zerosquaresfinal {

    public static void main(String[] args) {
        Move move = new Move();

        Scanner sc=new Scanner(System.in);
        State preState = new State(Level2.grid, Level2.color, Level2.status);
        System.out.println("Enter number 1 for DFS or 2 for BFS or 3 for DFSR or 4 for UCS ");
        int x=sc.nextInt();
        switch(x){
            case 1:
                DFS dfs =new DFS();
                dfs.search(preState);
                break;
            case 2:
                BFS bfs=new BFS();
                bfs.search(preState);
                break;
            case 3:
                DFSRecursion dfsr=new DFSRecursion();
                dfsr.search(preState);
            case 4:
                UCS ucs=new UCS();
                ucs.search(preState);
        }

        //System.out.println("Initial State:");
        //System.out.println(preState.show());
        // DFS dfs = new DFS();
        // dfs.search(preState);
        // BFS bfs =new BFS();
        //bfs.search(preState);


        List<String> directions = Arrays.asList("right", "left", "up", "down");


        //  applyDirections(preState, move, directions);
    }


    public static void applyDirections(State preState, Move move, List<String> directions) {
        for (String direction : directions) {
            State nextState = null;


            switch (direction) {
                case "right":
                    nextState = move.moveRight(preState);
                    break;
                case "left":
                    nextState = move.moveLeft(preState);
                    break;
                case "up":
                    nextState = move.moveUp(preState);
                    break;
                case "down":
                    nextState = move.moveDown(preState);
                    break;
            }


            if (nextState != null && !nextState.show().equals(preState.show())) {
                //System.out.println("Direction: " + direction);
                //   System.out.println(nextState.show());
                preState = new State(nextState);
            } else {
                //System.out.println("Direction: " + direction + " - Move not possible.");
            }


            if (nextState != null && nextState.checkWin()) {
                // System.out.println("You Won.");
                break;
            }
        }
    }
}









/*
package zerosquaresfinal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Zerosquaresfinal {

    public static void main(String[] args) {
        Move move = new Move();
        State preState = new State(Level2.grid, Level2.color, Level2.status);

       System.out.println("Initial State:");
        System.out.println(preState.show());


        List<String> directions = Arrays.asList("right", "left", "up", "down");


        applyDirections(preState, move, directions);
    }


    public static void applyDirections(State preState, Move move, List<String> directions) {
        for (String direction : directions) {
            State nextState = null;


            switch (direction) {
                case "right":
                    nextState = move.moveRight(preState);
                    break;
                case "left":
                    nextState = move.moveLeft(preState);
                    break;
                case "up":
                    nextState = move.moveUp(preState);
                    break;
                case "down":
                    nextState = move.moveDown(preState);
                    break;
            }


            if (nextState != null && !nextState.show().equals(preState.show())) {
                System.out.println("Direction: " + direction);
                System.out.println(nextState.show());
                preState = new State(nextState);
            } else {
                System.out.println("Direction: " + direction + " - Move not possible.");
            }


            if (nextState != null && nextState.checkWin()) {
                System.out.println("You Won.");
                break;
            }
        }
    }
}

*/
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

package zerosquaresfinal;

import java.util.*;

public class BFS {
    private Move move;
    private Set<String> visited;

    public BFS() {
        this.move = new Move();
        this.visited = new HashSet<>();
    }


    public boolean search(State initialState) {
        Queue<State> queue = new LinkedList<>();
        initialState.parent = null;
        queue.add(initialState);
        visited.add(initialState.show());


        List<String> directions = Arrays.asList("left", "right", "down", "up");

        while (!queue.isEmpty()) {
            State currentState = queue.poll();


            System.out.println("Current grid:");
            System.out.println(currentState.show());


            if (currentState.checkWin()) {
                ArrayList<State> path = reconstructPath(currentState);
                System.out.println("Number of visited states: " + visited.size());
                System.out.println("Number of states in the path: " + path.size());
                markPathOnGrid(path);
                return true;
            }


            addNextStates(queue, currentState, directions);
        }

        System.out.println("No solution found.");
        return false;
    }

    private void addNextStates(Queue<State> queue, State currentState, List<String> directions) {
        for (String direction : directions) {

            State nextState = null;


            switch (direction) {
                case "right":
                    nextState = move.moveRight(currentState);
                    break;
                case "left":
                    nextState = move.moveLeft(currentState);
                    break;
                case "up":
                    nextState = move.moveUp(currentState);
                    break;
                case "down":
                    nextState = move.moveDown(currentState);
                    break;
            }


            if (nextState != null && !visited.contains(nextState.show())) {
                nextState.parent = currentState;
                queue.add(nextState);
                visited.add(nextState.show());


                System.out.println("Direction: " + direction);
                System.out.println("Grid after movement:");
                System.out.println(nextState.show());
            }
        }
    }


    public ArrayList<State> reconstructPath(State goalState) {
        ArrayList<State> path = new ArrayList<>();
        State current = goalState;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        reverse(path);
        return path;
    }


    public void markPathOnGrid(ArrayList<State> path) {
        State finalState = path.get(path.size() - 1);
        for (State state : path) {
            for (int i = 0; i < state.grid.length; i++) {
                for (int j = 0; j < state.grid[i].length; j++) {
                    if ("free".equals(state.status[i][j])) {
                        finalState.grid[i][j] = "◆";
                    }
                }
            }
        }


        System.out.println("Final grid with path:");
        System.out.println(finalState.show());
    }


    public void reverse(List<State> list) {
        for (int i = 0, j = list.size() - 1; i < j; i++, j--) {
            State temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }
    }
}
package zerosquaresfinal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;


public class DFS {
    private Move move;
    private Set<String> visited;

    public DFS() {
        this.move = new Move();
        this.visited = new HashSet<>();
    }


    public boolean search(State initialState) {
        Stack<State> stack = new Stack<>();
        initialState.parent = null;
        stack.push(initialState);
        visited.add(initialState.show());


        List<String> directions = Arrays.asList("left", "right", "down", "up");

        while (!stack.isEmpty()) {
            State currentState = stack.pop();


            System.out.println("Current grid:");
            System.out.println(currentState.show());


            if (currentState.checkWin()) {
                ArrayList<State> path = reconstructPath(currentState);
                System.out.println("Number of visited states: " + visited.size());
                System.out.println("Number of states in the path: " + path.size());
                markPathOnGrid(path);
                return true;
            }


            addNextStates(stack, currentState, directions);
        }

        System.out.println("No solution found.");
        return false;
    }

    private void addNextStates(Stack<State> stack, State currentState, List<String> directions) {
        for (String direction : directions) {

            State nextState = null;


            switch (direction) {
                case "right":
                    nextState = move.moveRight(currentState);
                    break;
                case "left":
                    nextState = move.moveLeft(currentState);
                    break;
                case "up":
                    nextState = move.moveUp(currentState);
                    break;
                case "down":
                    nextState = move.moveDown(currentState);
                    break;
            }


            if (nextState != null && !visited.contains(nextState.show())) {
                nextState.parent = currentState;
                stack.push(nextState);
                visited.add(nextState.show());


                System.out.println("Direction: " + direction);
                System.out.println("Grid after movement:");
                System.out.println(nextState.show());
            }
        }
    }




    private ArrayList<State> reconstructPath(State goalState) {
        ArrayList<State> path = new ArrayList<>();
        State current = goalState;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        reverse(path);
        return path;
    }


    private void markPathOnGrid(ArrayList<State> path) {
        State finalState = path.get(path.size() - 1);
        for (State state : path) {
            for (int i = 0; i < state.grid.length; i++) {
                for (int j = 0; j < state.grid[i].length; j++) {
                    if(state.grid[i][j].equals("red")){
                        finalState.grid[i][j]="◆";
                    }
                    else if ("free".equals(state.status[i][j])) {
                        finalState.grid[i][j] = "◆";
                    }
                }
            }
        }


        System.out.println("Final grid with path:");
        System.out.println(finalState.show());
    }


    private void reverse(List<State> list) {
        for (int i = 0, j = list.size() - 1; i < j; i++, j--) {
            State temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }
    }
}

package zerosquaresfinal;


import java.util.*;

public class UCS {
    private Move move;
    private Set<String> visited;

    public UCS() {
        this.move = new Move();
        this.visited = new HashSet<>();
    }


    public boolean search(State initialState) {

        PriorityQueue<State> priorityQueue = new PriorityQueue<>(new Comparator<State>() {
            @Override
            public int compare(State state1, State state2) {

                return Integer.compare(state1.cost, state2.cost);
            }
        });

        initialState.parent = null;
        initialState.cost = 0;
        priorityQueue.add(initialState);
        visited.add(initialState.show());


        List<String> directions = Arrays.asList("left", "right", "down", "up");

        while (!priorityQueue.isEmpty()) {
            State currentState = priorityQueue.poll();


            System.out.println("Current grid:");
            System.out.println(currentState.show());


            if (currentState.checkWin()) {
                ArrayList<State> path = reconstructPath(currentState);
                System.out.println("Number of visited states: " + visited.size());
                System.out.println("Number of states in the path: " + path.size());
                markPathOnGrid(path);
                return true;
            }


            addNextStates(priorityQueue, currentState, directions);
        }

        System.out.println("No solution found.");
        return false;
    }

    private void addNextStates(PriorityQueue<State> priorityQueue, State currentState, List<String> directions) {
        for (String direction : directions) {

            State nextState = null;


            switch (direction) {
                case "right":
                    nextState = move.moveRight(currentState);
                    break;
                case "left":
                    nextState = move.moveLeft(currentState);
                    break;
                case "up":
                    nextState = move.moveUp(currentState);
                    break;
                case "down":
                    nextState = move.moveDown(currentState);
                    break;
            }


            if (nextState != null && !visited.contains(nextState.show())) {
                nextState.parent = currentState;
                nextState.cost = currentState.cost + 1;
                priorityQueue.add(nextState);
                visited.add(nextState.show());


                System.out.println("Direction: " + direction);
                System.out.println("Grid after movement:");
                System.out.println(nextState.show());
            }
        }
    }


    private ArrayList<State> reconstructPath(State goalState) {
        ArrayList<State> path = new ArrayList<>();
        State current = goalState;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }


    private void markPathOnGrid(ArrayList<State> path) {
        State finalState = path.get(path.size() - 1);
        for (State state : path) {
            for (int i = 0; i < state.grid.length; i++) {
                for (int j = 0; j < state.grid[i].length; j++) {
                    if ("free".equals(state.status[i][j])) {
                        finalState.grid[i][j] = "◆";
                    }
                }
            }
        }


        // finalState.grid[finalState.goalRow][finalState.goalCol] = "G";


        System.out.println("Final grid with path:");
        System.out.println(finalState.show());
    }
}

package zerosquaresfinal;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DFSRecursion {
    private Move move;
    private Set<String> visited;
    private ArrayList<State> path;

    public DFSRecursion() {
        this.move = new Move();
        this.visited = new HashSet<>();
        this.path = new ArrayList<>();
    }


    public boolean search(State initialState) {
        visited.add(initialState.show());
        initialState.parent = null;

        if (dfs(initialState)) {
            System.out.println("Solution found!");
            System.out.println("Number of visited states: " + visited.size());
            System.out.println("Number of states in the path: " + path.size());
            markPathOnGrid(path);
            return true;
        } else {
            System.out.println("No solution found.");
            return false;
        }
    }

    private boolean dfs(State currentState) {

        System.out.println("Current grid:");
        System.out.println(currentState.show());


        if (currentState.checkWin()) {
            reconstructPath(currentState);
            return true;
        }


        List<String> directions = Arrays.asList("left", "right", "down", "up");


        for (String direction : directions) {
            State nextState = null;


            switch (direction) {
                case "right":
                    nextState = move.moveRight(currentState);
                    break;
                case "left":
                    nextState = move.moveLeft(currentState);
                    break;
                case "up":
                    nextState = move.moveUp(currentState);
                    break;
                case "down":
                    nextState = move.moveDown(currentState);
                    break;
            }


            if (nextState != null && !visited.contains(nextState.show())) {
                visited.add(nextState.show());
                nextState.parent = currentState;


                System.out.println("Direction: " + direction);
                System.out.println("Grid after movement:");
                System.out.println(nextState.show());


                if (dfs(nextState)) {
                    return true; //
                }


                visited.remove(nextState.show());
            }
        }

        return false;
    }


    private void reconstructPath(State goalState) {
        State current = goalState;
        while (current != null) {
            path.add(0, current);
            current = current.parent;
        }
    }

    private void markPathOnGrid(ArrayList<State> path) {
        State finalState = path.get(path.size() - 1);
        for (State state : path) {
            for (int i = 0; i < state.grid.length; i++) {
                for (int j = 0; j < state.grid[i].length; j++) {
                    if (state.grid[i][j].equals("red")) {
                        finalState.grid[i][j] = "◆";
                    } else if ("free".equals(state.status[i][j])) {
                        finalState.grid[i][j] = "◆";
                    }
                }
            }
        }

        System.out.println("Final grid with path:");
        System.out.println(finalState.show());
    }
}

package zerosquaresfinal;

public class Level2 {

    public static final String[][] grid= {
            {"⬛", "⬛", "⬛", "⬛", "⬛", "⬛", "⬛", "⬛"},
            {"⬛", "⬜", "⬜", "⬛", "⬜", "⬜", "⬜", "⬛"},
            {"⬛", "🔴", "⬜", "⬜", "⬜", "⬜", "⬜", "⬛"},
            {"⬛", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬛"},
            {"⬛", "⬛", "⬛", "⬛", "🔴", "⬛", "⬛", "⬛"},
            {"⬜", "⬜", "⬜", "⬛", "⬛", "⬛", "⬜", "⬜"}
    };
    public static final String[][] color ={
            {"black", "black", "black", "black", "black", "black", "black", "black"},
            {"black", "white", "white", "black", "white", "white", "white", "black"},
            {"black", "red", "white", "white", "white", "white", "white", "black"},
            {"black", "white", "white", "white", "white", "white", "white", "black"},
            {"black", "black", "black", "black", "red", "black", "black", "black"},
            {"white", "white", "white", "black", "black", "black", "white", "white"}
    };
    public static final String[][] status = {
            {"fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed"},
            {"fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed"},
            {"fixed", "free", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed"},
            {"fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed"},
            {"fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed"},
            {"fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed"}
    };
}

package zerosquaresfinal;
public class Level3 {


    public static final String[][] grid= {
            {"⬜", "⬜", "⬛", "⬛", "⬛", "⬛", "⬛", "⬛", "⬛", "⬛", "⬛", "⬛", "⬛"},
            {"⬜", "⬜", "⬛", "🔴", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬛"},
            {"⬛", "⬛", "⬛", "⬜", "⬜", "⬛", "⬛", "⬛", "⬛", "⬛", "⬛", "⬜", "⬛"},
            {"⬛", "⬜", "⬜", "⬜", "⬜", "⬜", "🔴", "⬜", "⬜", "⬜", "⬛", "⬜", "⬛"},
            {"⬛", "⬜", "⬛", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬛", "⬜", "⬛"},
            {"⬛", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬛"},
            {"⬛", "⬛", "⬛", "⬛", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬛"},
            {"⬜", "⬜", "⬜", "⬛", "⬛", "⬛", "⬛", "⬛", "⬛", "⬛", "⬛", "⬛", "⬛"},
    };

    public static final String[][]  color = {
            {"white", "white", "black", "black", "black", "black", "black", "black", "black", "black", "black", "black", "black"},
            {"white", "white", "black", "red", "white", "white", "white", "white", "white", "white", "white", "white", "black"},
            {"black", "black", "black", "white", "white", "black", "black", "black", "black", "black", "black", "white", "black"},
            {"black", "white", "white", "white", "white", "white", "red", "white", "white", "white", "black", "white", "black"},
            {"black", "white", "black", "white", "white", "white", "white", "white", "white", "white", "black", "white", "black"},
            {"black", "white", "white", "white", "white", "white", "white", "white", "white", "white", "white", "white", "black"},
            {"black", "black", "black", "black", "white", "white", "white", "white", "white", "white", "white", "white", "black"},
            {"white", "white", "white", "black", "black", "black", "black", "black", "black", "black", "black", "black", "black"}
    };

    public static final String[][] status = {
            {"fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed"},
            {"fixed", "fixed", "fixed", "free", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed"},
            {"fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed"},
            {"fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed"},
            {"fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed"},
            {"fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed"},
            {"fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed"},
            {"fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed"}
    };


}


