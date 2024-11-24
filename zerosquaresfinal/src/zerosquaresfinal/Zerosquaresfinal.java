package zerosquaresfinal;


import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Zerosquaresfinal {

    public static void main(String[] args) {
        Move move = new Move();
        
        Scanner sc=new Scanner(System.in);
        State preState = new State(Level2.grid, Level2.color, Level2.status);
        System.out.println("Enter number 1 for DFS or 2 for BFS or 3 for DFSR");
        int x=sc.nextInt();
        switch(x){
            case 1:
                DFS dfs =new DFS();
                dfs.search(preState);
                break;
            case 2:
                BFS bfs=new BFS();
                bfs.search(preState);
                break;
            case 3:
                DFSRecursion dfsr=new DFSRecursion();
                dfsr.search(preState);
        }

       //System.out.println("Initial State:");
        //System.out.println(preState.show());
       // DFS dfs = new DFS();
   // dfs.search(preState);
   // BFS bfs =new BFS();
    //bfs.search(preState);

       
        List<String> directions = Arrays.asList("right", "left", "up", "down");

        
      //  applyDirections(preState, move, directions);
    }

   
    public static void applyDirections(State preState, Move move, List<String> directions) {
        for (String direction : directions) {
            State nextState = null;

         
            switch (direction) {
                case "right":
                    nextState = move.moveRight(preState);
                    break;
                case "left":
                    nextState = move.moveLeft(preState);
                    break;
                case "up":
                    nextState = move.moveUp(preState);
                    break;
                case "down":
                    nextState = move.moveDown(preState);
                    break;
            }

            
            if (nextState != null && !nextState.show().equals(preState.show())) {
                //System.out.println("Direction: " + direction);
             //   System.out.println(nextState.show());
                preState = new State(nextState); 
            } else {
                //System.out.println("Direction: " + direction + " - Move not possible.");
            }

            
            if (nextState != null && nextState.checkWin()) {
               // System.out.println("You Won.");
                break;  
            }
        }
    }
}









/*
package zerosquaresfinal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Zerosquaresfinal {

    public static void main(String[] args) {
        Move move = new Move();
        State preState = new State(Level2.grid, Level2.color, Level2.status);

       System.out.println("Initial State:");
        System.out.println(preState.show());

      
        List<String> directions = Arrays.asList("right", "left", "up", "down");

        
        applyDirections(preState, move, directions);
    }

    
    public static void applyDirections(State preState, Move move, List<String> directions) {
        for (String direction : directions) {
            State nextState = null;

          
            switch (direction) {
                case "right":
                    nextState = move.moveRight(preState);
                    break;
                case "left":
                    nextState = move.moveLeft(preState);
                    break;
                case "up":
                    nextState = move.moveUp(preState);
                    break;
                case "down":
                    nextState = move.moveDown(preState);
                    break;
            }

            
            if (nextState != null && !nextState.show().equals(preState.show())) {
                System.out.println("Direction: " + direction);
                System.out.println(nextState.show());
                preState = new State(nextState);  
            } else {
                System.out.println("Direction: " + direction + " - Move not possible.");
            }

            
            if (nextState != null && nextState.checkWin()) {
                System.out.println("You Won.");
                break;  
            }
        }
    }
}

*/