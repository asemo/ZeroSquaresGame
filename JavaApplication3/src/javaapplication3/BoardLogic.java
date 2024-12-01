package javaapplication3;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;

public class BoardLogic {
    int gridX, gridY;
    char[][] board;
    ArrayList<Stone> stones = new ArrayList<>();
    ArrayList<Goal> goals = new ArrayList<>();

    boolean finished = false;
   // boolean lost = false;

    BoardLogic() {}

    BoardLogic(int gridX, int gridY, char[][] board, ArrayList<Stone> stones, ArrayList<Goal> goals) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.board = board;
        this.stones = stones;
        this.goals = goals;
    }

    public BoardLogic move(String dir,boolean flag){
        BoardLogic boardLogic;
        if(flag){
            boardLogic = this;
        }
        else{
            char[][] newBoard = new char[gridX][gridY];
            for (int i = 0; i < gridX; i++) {
                System.arraycopy(board[i], 0, newBoard[i], 0, gridY);
            }
            ArrayList<Stone> newStones = new ArrayList<>();
            for (Stone s : stones) {
                newStones.add(new Stone(s.getC(),s.getColor(), s.getX(), s.getY(), s.isInGoal()));
            }
            ArrayList<Goal> newGoals = new ArrayList<>();
            for (Goal g : goals) {
                newGoals.add(new Goal(g.getC(),g.getX(), g.getY(), g.getColor()));
            }
            System.out.println("the new board "+newBoard.toString());
            System.out.println("the new stones "+newStones.size());
            System.out.println("the new goals is "+newGoals.size());
          
            boardLogic = new BoardLogic(gridX, gridY, newBoard, newStones, newGoals);
        }


        int [][] canMove = new int [stones.size()][2];
        boolean[] getInGoal = new boolean[stones.size()];
        for(int i=0;i<stones.size();i++){
            Stone s = stones.get(i);
            canMove[i][0] = s.getX();
            canMove[i][1] = s.getY();
            getInGoal[i]=false;
        }
/////هذا الجزء الصغير من الكود هو فقط يشكيك على المربعات من اجل الحركة
        for(int i=0;i<boardLogic.stones.size();i++) {
            Stone s = boardLogic.stones.get(i);

            if(s.isInGoal()) continue;
            char c = s.getC();

            //check movement
            if (dir == "UP") {
              //  System.out.println("up");
                int temp = s.getX() - 1;
                while(temp>=0 && checkIfYouCanWalk(temp,s.getY(),true)){
                    if(boardLogic.board[temp][s.getY()]==Character.toUpperCase(c)) {
                        getInGoal[i]=true;
                        break;
                    }
                    temp--;
                }
                canMove[i][0] = temp+1;
                canMove[i][1] = s.getY();
                System.out.println("the new cordinate.x is after movin Up for is "+canMove[i][0]);
             
                System.out.println("the new cordinate.y after moving up is :"+canMove[i][1]);
                System.out.println("the index of stone is "+(stones.indexOf(stones))+1);
                //System.out.println(canMove[i][1]);
            } else if (dir == "DOWN") {
               // System.out.println("down");
                int temp = s.getX() + 1;
                while(temp<boardLogic.gridX && checkIfYouCanWalk(temp,s.getY(),true)){
                    if(boardLogic.board[temp][s.getY()]==Character.toUpperCase(c)) {
                        getInGoal[i]=true;
                        break;
                    }
                    temp++;
                }
                canMove[i][0] = temp-1;
                canMove[i][1] = s.getY();
                System.out.println("the new cordinate.x after moving down is :"+canMove[i][0]);
                System.out.println("the new cordinate.y after moving down is :"+canMove[i][1]);
                 System.out.println("the index of stone is "+(stones.indexOf(stones))+1);
            } else if (dir == "LEFT") {
               // System.out.println("left");
                int temp = s.getY() - 1;
                while(temp>=0 && checkIfYouCanWalk(s.getX(),temp,true)){
                    if(boardLogic.board[s.getX()][temp]==Character.toUpperCase(c)){
                        getInGoal[i]=true;
                        break;
                    }
                    temp--;
                }
                canMove[i][0] = s.getX();
                canMove[i][1] = temp+1;
                 System.out.println("the new cordinate.x after moving left is :"+canMove[i][0]);
                System.out.println("the new cordinate.y after moving left is :"+canMove[i][1]);
                 System.out.println("the index of stone is "+(stones.indexOf(stones))+1);
            } else {
              //  System.out.println("right");
                int temp = s.getY() + 1;
                while(temp<boardLogic.gridY && checkIfYouCanWalk(s.getX(),temp,true)){
                    if(boardLogic.board[s.getX()][temp]==Character.toUpperCase(c)){
                        getInGoal[i]=true;
                        break;
                    }
                    temp++;
                }
                canMove[i][0] = s.getX();
                canMove[i][1] = temp-1;
                 System.out.println("the new cordinate.x after moving right is :"+canMove[i][0]);
                System.out.println("the new cordinate.y after moving right is :"+canMove[i][1]);
            }


        }
        /// هذا الجزء من الكود ياحزرك مشان شو؟ 
        // هاد مشان تحويل الهدف بعد ما يتحقق الى مربع ابيض وبتصير تحسن تمرق فوقو
        for(int i=0;i<boardLogic.stones.size();i++) {
            Stone s = boardLogic.stones.get(i);
            if(s.isInGoal()) continue;
            s.setX(canMove[i][0]);
            s.setY(canMove[i][1]);
            if(getInGoal[i]){
                s.setInGoal(true);
                for(Goal g : goals){
                    if(g.getC() == Character.toUpperCase(s.getC())) {
                        boardLogic.board[g.getX()][g.getY()]='_';
                    }
                }

            }
        }
        return new BoardLogic(boardLogic.gridX, boardLogic.gridY, boardLogic.board, boardLogic.stones, boardLogic.goals);
    }


/// هاد نفس اخر جزء من التابع يلي فوق 
//    private void clearGoal(BoardLogic boardLogic, Stone s) {
//        for (Goal g : goals) {
//            if (g.getC() == Character.toUpperCase(s.getC())) {
//                boardLogic.board[g.getX()][g.getY()] = '_';
//            }
//        }
//    }

    private boolean checkIfYouCanWalk(int i, int j, boolean flag) {
        if (board[i][j] == '#') return false;
        //if (board[i][j] == 'O' && flag) { finished = true; lost = true; return false; }
        for (Stone s : stones) if (!s.isInGoal() && s.getX() == i && s.getY() == j) return false;
        return true;
    }

    public boolean checkGameOver() {
        for (Stone stone : stones) if (!stone.isInGoal()) return false;
        finished = true;
        return true;
    }

                            //هاد تابع الطباعة على الconsole لكن بما انو استخدت الGUI لكن هادالتابع مو كامل 
//     public void printGrid(){
//
//        for(int i=0;i<board.length;i++){
//            for(int j=0;j<board[i].length;j++){
//                System.out.print(board[i][j] + " ");
//            }
//            System.out.println();
//        }
//    }
}
//    private int moveVertically(Stone s, int step, boolean[] getInGoal, int i) {
//        int temp = s.getX() + step;
//        while (temp >= 0 && temp < gridX && checkIfYouCanWalk(temp, s.getY(), true)) {
//            if (board[temp][s.getY()] == Character.toUpperCase(s.getC())) {
//                getInGoal[i] = true;
//                break;
//            }
//            temp += step;
//        }
//        return temp - step;
//    }

//    private int moveHorizontally(Stone s, int step, boolean[] getInGoal, int i) {
//        int temp = s.getY() + step;
//        while (temp >= 0 && temp < gridY && checkIfYouCanWalk(s.getX(), temp, true)) {
//            if (board[s.getX()][temp] == Character.toUpperCase(s.getC())) {
//                getInGoal[i] = true;
//                break;
//            }
//            temp += step;
//        }
//        return temp - step;
//    }

//  public List<BoardLogic> possibleBoards(){
//
//        List<BoardLogic> boardLogics = new ArrayList<>();
//
//        System.out.println("possible boards: ");
//        System.out.println("UP");
//        boardLogics.add(move("UP",false));
//        boardLogics.get(0).printGrid();
//
//        System.out.println("DOWN");
//        boardLogics.add(move("DOWN",false));
//        boardLogics.get(1).printGrid();
//
//        System.out.println("LEFT");
//        boardLogics.add(move("LEFT",false));
//        boardLogics.get(2).printGrid();
//
//        System.out.println("RIGHT");
//        boardLogics.add(move("RIGHT",false));
//        boardLogics.get(3).printGrid();
//
//        return boardLogics;
//    }
//    private char[][] deepCopyBoard() {
//        char[][] copy = new char[gridX][gridY];
//        for (int i = 0; i < gridX; i++) System.arraycopy(board[i], 0, copy[i], 0, gridY);
//        return copy;
//    }

//    private ArrayList<Stone> copyStones() {
//        ArrayList<Stone> newStones = new ArrayList<>();
//        for (Stone s : stones) newStones.add(new Stone(s.getC(), s.getColor(), s.getX(), s.getY(), s.isInGoal()));
//        return newStones;
//    }

//    private ArrayList<Goal> copyGoals() {
//        ArrayList<Goal> newGoals = new ArrayList<>();
//        for (Goal g : goals) newGoals.add(new Goal(g.getC(), g.getX(), g.getY(), g.getColor()));
//        return newGoals;
//    }