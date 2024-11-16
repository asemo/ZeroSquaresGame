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

    // البحث عن الحل باستخدام DFS
    public boolean search(State initialState) {
        Stack<State> stack = new Stack<>();
        initialState.parent = null;
        stack.push(initialState);
        visited.add(initialState.show());

        // قائمة الاتجاهات
        List<String> directions = Arrays.asList("left", "right", "down", "up");

        while (!stack.isEmpty()) {
            State currentState = stack.pop();

            // طباعة الشبكة بعد كل حركة
            System.out.println("Current grid:");
            System.out.println(currentState.show());

            // إذا تحقق شرط الفوز
            if (currentState.checkWin()) {
                ArrayList<State> path = reconstructPath(currentState);  // إعادة بناء المسار
                System.out.println("Number of visited states: " + visited.size());
                System.out.println("Number of states in the path: " + path.size());
                markPathOnGrid(path);  // تمييز المربعات على الشبكة
                return true;
            }

            // إضافة الحالات التالية بناءً على قائمة الاتجاهات
            addNextStates(stack, currentState, directions);
        }

        System.out.println("No solution found.");
        return false;
    }

private void addNextStates(Stack<State> stack, State currentState, List<String> directions) {
    for (String direction : directions) {
        // استخدم النسخة المعدلة من currentState بناءً على الحركة
        State nextState = null;

        // تطبيق الحركة بناءً على الاتجاه الحالي
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

        // التحقق من أن الحركة ناجحة ولم يتم زيارة الحالة من قبل
        if (nextState != null && !visited.contains(nextState.show())) {
            nextState.parent = currentState;  // تعيين parent للحالة الجديدة
            stack.push(nextState);  // إضافة الحالة الجديدة للمكدس
            visited.add(nextState.show());  // إضافة الحالة إلى visited

            // طباعة الشبكة بعد تطبيق الحركة
            System.out.println("Direction: " + direction);
            System.out.println("Grid after movement:");
            System.out.println(nextState.show());
        }
    }
}



    // إعادة بناء المسار باستخدام خاصية `parent`
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

    // تمييز المربعات التي تم المرور عليها في الشبكة
    private void markPathOnGrid(ArrayList<State> path) {
        State finalState = path.get(path.size() - 1);  // الحالة النهائية
        for (State state : path) {
            for (int i = 0; i < state.grid.length; i++) {
                for (int j = 0; j < state.grid[i].length; j++) {
                    if ("free".equals(state.status[i][j])) {  // افترضنا أن "movable" تحدد المربعات القابلة للحركة
                        finalState.grid[i][j] = "◆";  // تغيير الرمز إلى معين أسود
                    }
                }
            }
        }

        // طباعة الشبكة النهائية
        System.out.println("Final grid with path:");
        System.out.println(finalState.show());
    }

    // دالة لعكس قائمة
    private void reverse(List<State> list) {
        for (int i = 0, j = list.size() - 1; i < j; i++, j--) {
            State temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }
    }
}

