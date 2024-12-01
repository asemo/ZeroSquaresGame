package javaapplication3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BoardGui extends JFrame implements KeyListener {
    int index;
    BoardLogic boardLogic;
    JPanel main_panel = new JPanel();
    JButton[][] buttons;

    BoardGui(int index) {
        this.index = index;
        boardLogic = new BoardLogic();
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
            {'#', ' ', ' ', 'R', '#'},
            {'#', ' ', ' ', 'G', '#'},
            {'#', '#', 'r', ' ', 'g'},
            {'#', '#', '#', '#', '#'}
        };

        // إعداد الأهداف
        boardLogic.goals.add(new Goal('R', 1, 3, Color.RED));
        boardLogic.goals.add(new Goal('G', 2, 3, Color.BLUE));
       // boardLogic.goals.add(new Goal('T', 0, 0, Color.YELLOW));
        // إعداد الحجارة
        boardLogic.stones.add(new Stone('r', Color.RED, 3, 2, false));
        boardLogic.stones.add(new Stone('g', Color.BLUE, 3, 3, false));
        //boardLogic.stones.add(new Stone('t', Color.YELLOW, 1, 1, false));
    }

                                //هدول شغلات مو مهمة مافي داعي نلعي فيهن كتير
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
                }else if(boardLogic.board[i][j]=='G'){
                buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE, 10));
                }
//                else if(boardLogic.board[i][j]==''){
//                
//                }
                // المزيد من الشرط لرسم الأهداف والأحجار إذا لزم الأمر
            }
        }for (Goal goal : boardLogic.goals) {
    if (goal.getC() == 'G' && goal.getX() == goal.getX() && goal.getY() == goal.getY()) {
        buttons[goal.getX()][goal.getY()].setBackground(Color.WHITE); // الهدف يتحول إلى أبيض
        //buttons[goal.getX()][goal.getY()].setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));
    }
}

        for (Stone stone : boardLogic.stones) {
            if (!stone.isInGoal()) {
                buttons[stone.getX()][stone.getY()].setBackground(stone.getColor());
            }
        }
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                boardLogic.move("UP", true);
                break;
            case KeyEvent.VK_DOWN:
                boardLogic.move("DOWN", true);
                break;
            case KeyEvent.VK_LEFT:
                boardLogic.move("LEFT", true);
                break;
            case KeyEvent.VK_RIGHT:
                boardLogic.move("RIGHT", true);
                break;
        }

       updateFrame();
        repaint();

//        if (boardLogic.checkGameOver()) {
//            this.dispose();
//            index++;
//            new BoardGui(index);
//            return;}
//        } else if (boardLogic.lost) {
//            this.dispose();
//            new BoardGui(index);
//        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
