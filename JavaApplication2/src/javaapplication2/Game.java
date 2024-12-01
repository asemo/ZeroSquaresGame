/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

/**
 *
 * @author ASUS
 */import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class Game extends JFrame implements KeyListener{
    private Board board; // رقعة اللعبة

    // مُنشئ اللعبة
    public Game(int size) {
        board = new Board(size,5,1); // إنشاء الرقعة بحجم معين

        // إعداد واجهة المستخدم
        this.setTitle("Game Board");
        this.setSize(400, 400); // حجم نافذة اللعبة
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addKeyListener(this);
        
    }
   @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_UP:
                board.moveMovableSquares(-1, 0);  // تحريك للأعلى
                
                break;
            case KeyEvent.VK_DOWN:
                board.moveMovableSquares(1, 0);   // تحريك للأسفل
                break;
            case KeyEvent.VK_LEFT:
                board.moveMovableSquares(0, -1);  // تحريك لليسار
                break;
            case KeyEvent.VK_RIGHT:
                board.moveMovableSquares(0, 1);   // تحريك لليمين
                break;
        }
        
        repaint();
    }
    

    // دالة لرسم الرقعة
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        board.display(g); // رسم الرقعة
    }

    @Override
    public void keyTyped(KeyEvent e) {
         //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
         //To change body of generated methods, choose Tools | Templates.
    }
}
