import java.util.Scanner;
import java.util.*;
import javax.swing.JFrame;

class SnakeGame
{
    public static void main(String[] args) {
       {
        int boardWidth=600;
        int boardHeight=600;
        JFrame frame=new JFrame("Snake Game");
        frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        Snake start=new Snake(boardHeight, boardWidth);
        frame.add(start);
        frame.pack();
        start.requestFocus();
        }
        }
    }