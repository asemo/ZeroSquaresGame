
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
