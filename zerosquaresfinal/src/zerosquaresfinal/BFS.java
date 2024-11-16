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
