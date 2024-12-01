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

/*
package zerosquaresfinal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DFS {
    private Move move;
    private Set<String> visited;
    private ArrayList<State> path;

    public DFS() {
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

*/