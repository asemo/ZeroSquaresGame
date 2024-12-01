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
