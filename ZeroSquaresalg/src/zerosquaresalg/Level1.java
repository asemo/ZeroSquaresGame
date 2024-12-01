
package zerosquaresalg;

public class Level1 {
    
    public static final String[][] grid = {
        {"⬛", "⬜", "⬛", "⬛", "⬛", "⬛", "⬛", "⬛"},
        {"⬛", "🔴", "⬜", "⬜", "⬜", "⬜", "🔴", "⬛"},
        {"⬛", "⬛", "⬛", "⬛", "⬛", "⬛", "⬛", "⬛"}
    };

    public static final String[][] color = {
        {"black", "white", "black", "black", "black", "black", "black", "black"},
        {"black", "red", "white", "white", "white", "white", "red", "black"},
        {"black", "black", "black", "black", "black", "black", "black", "black"}
    };

    public static final String[][] status = {
        {"fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed"},
        {"fixed", "free", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed"},
        {"fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed", "fixed"}
    };
    
}

