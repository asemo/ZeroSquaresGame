package javaapplication1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JFrame implements KeyListener {
    private Board board;

    public Game(int size, int fixedSquares, int movableAndTargetCount) {
        board = new Board(size, fixedSquares, movableAndTargetCount);

        // إعداد واجهة المستخدم
        this.setTitle("Zero Squares Game");
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        board.display(g);
    }

    // دوال KeyListener لتنفيذ الحركات حسب إدخال المستخدم
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_UP:
                board.moveMovableSquares(-1, 0);  // التحرك للأعلى
                break;
            case KeyEvent.VK_DOWN:
                board.moveMovableSquares(1, 0);   // التحرك للأسفل
                break;
            case KeyEvent.VK_LEFT:
                board.moveMovableSquares(0, -1);  // التحرك لليسار
                break;
            case KeyEvent.VK_RIGHT:
                board.moveMovableSquares(0, 1);   // التحرك لليمين
                break;
        }

        // إعادة رسم اللوحة بعد كل حركة
        repaint();

        // التحقق من شرط الهدف بعد كل حركة
        if (board.checkGoalAchieved()) {
            JOptionPane.showMessageDialog(this, "Congratulations! You've achieved the goal!");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    
}

