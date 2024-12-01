
package javaapplication1;
import java.util.Scanner;
public class JavaApplication1 {
    public static void main(String[] args) {
    Scanner sa=new Scanner(System.in);
   Game game = new Game(5, 5, 1); // رقعة 5x5، 3 مربعات ثابتة، 2 مربعات ملونة و2 مربعات هدف
        game.setVisible(true);
    }
    
}
