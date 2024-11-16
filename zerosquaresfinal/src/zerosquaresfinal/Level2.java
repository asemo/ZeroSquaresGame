
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
