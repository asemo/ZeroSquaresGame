package javaapplication5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class BoardGUi extends JFrame implements KeyListener {
    int index;
    Boardlogic boardLogic;
    JPanel main_panel = new JPanel();
    JButton[][] buttons;

    BoardGUi(int index) {
        this.index = index;
        boardLogic = new Boardlogic();
        setupStaticGrid(); // استخدام الشكل الثابت للرقعة
        setupButtons();
        setupFrame();
    }

    // إعداد الشكل الثابت للرقعة
    private void setupStaticGrid() {
        // تحديد شكل الرقعة
        boardLogic.gridX = 5;
        boardLogic.gridY = 5;
        boardLogic.board = new char[][] {
            {'#', '#', '#', '#', '#'},
            {'#', '#', ' ', 'R', '#'},
            {'#', ' ', ' ', ' ', '#'},
            {'#', '#', 'r', ' ', '#'},
            {'#', '#', '#', '#', '#'}
        };

        // إعداد الأهداف
        boardLogic.goals.add(new Goal('R', 1, 3, Color.RED));
        
        // إعداد الحجارة
        boardLogic.stones.add(new Stone('r', Color.RED, 3, 2, false));
    }

    private void setupButtons() {
        buttons = new JButton[boardLogic.gridX][boardLogic.gridY];
        main_panel.setLayout(new GridLayout(buttons.length, buttons[0].length));
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFocusable(false);
                main_panel.add(buttons[i][j]);
            }
        }
    }

    private void setupFrame() {
        updateFrame();
        main_panel.setSize(new Dimension(700, 700));
        this.add(main_panel);
        this.setSize(700, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setFocusable(true);
        this.addKeyListener(this);
    }

    public void updateFrame() {
        for (int i = 0; i < boardLogic.board.length; i++) {
            for (int j = 0; j < boardLogic.board[i].length; j++) {
                buttons[i][j].setBackground(Color.WHITE);
                if (boardLogic.board[i][j] == '#') {
                    buttons[i][j].setBackground(Color.BLACK);
                } else if (boardLogic.board[i][j] == 'R') {
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.RED, 10));
                }
            }
        }

        for (Stone stone : boardLogic.stones) {
            buttons[stone.getX()][stone.getY()].setBackground(stone.getColor());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                boardLogic.move("UP");
                break;
            case KeyEvent.VK_DOWN:
                boardLogic.move("DOWN");
                break;
            case KeyEvent.VK_LEFT:
                boardLogic.move("LEFT");
                break;
            case KeyEvent.VK_RIGHT:
                boardLogic.move("RIGHT");
                break;
        }

        updateFrame(); // تحديث العرض بعد الحركة
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
