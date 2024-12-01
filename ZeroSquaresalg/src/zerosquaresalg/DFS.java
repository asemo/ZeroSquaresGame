package zerosquaresalg; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;


import java.util.*;

public class DFS {

    public DFS() {
        // Constructor
    }

    public List<State> playDFS(State initState, State goalState) {
        Set<State> visited = new HashSet<>();
        Stack<State> stack = new Stack<>();
        Map<State, State> parent = new HashMap<>();
        
        stack.push(initState);
        parent.put(initState, null);

        while (!stack.isEmpty()) {
            State currentState = stack.pop();
            if (visited.contains(currentState)) {
                continue;
            }
            visited.add(currentState);

            if (currentState.getGrid().equals(goalState.getGrid())) {
                List<State> path = getPath(parent, goalState);
                System.out.println("Number of visited states = " + visited.size());
                return path;
            }

            for (State nextState : currentState.stateSpace()) {
                if (!visited.contains(nextState)) {
                    parent.put(nextState, currentState);
                    stack.push(nextState);
                }
            }
        }

        return null;
    }

    private List<State> getPath(Map<State, State> parent, State goalState) {
        List<State> path = new ArrayList<>();
        State currentState = goalState;

        while (currentState != null) {
            path.add(currentState);
            currentState = parent.get(currentState);
        }

        Collections.reverse(path);
        printPath(path);
        System.out.println("Number of states in path = " + path.size());
        return path;
    }

    private void printPath(List<State> path) {
        for (State state : path) {
            System.out.println(state.show());
        }
    }
}


