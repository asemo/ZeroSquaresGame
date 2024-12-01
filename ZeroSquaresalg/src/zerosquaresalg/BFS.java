package zerosquaresalg;

import java.util.*;

import java.util.*;

public class BFS {

    public BFS() {
        // Constructor
    }

    public List<State> playBFS(State initState, State goalState) {
        Set<State> visited = new HashSet<>();
        Queue<State> queue = new LinkedList<>();
        Map<State, State> parent = new HashMap<>();
        
        queue.add(initState);
        parent.put(initState, null);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
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
                if (!visited.contains(nextState) && !parent.containsKey(nextState)) {
                    parent.put(nextState, currentState);
                    queue.add(nextState);
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

