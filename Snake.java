import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

class Snake extends JPanel implements ActionListener,KeyListener
{
    class Tile 
    {
        int x;
         int y;
        Tile(int x,int y)
        {
            this.x=x;
            this.y=y;
        }
    }
    
    
    int boardHeight=600;
    int boardWidth=600;
    int tileSize=25;
    int speedX;
    int speedY;
    boolean gameOver=false;
    //Snake
    Tile snakeHead;
    //Food
    Tile food;
    Random random;
    


    //Game Logic
    Timer gameLoop;
    ArrayList<Tile>snakeBody;

    Snake(int boardHeight,int boardWidth)
    {
        this.boardHeight=boardHeight;
        this.boardWidth=boardWidth;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);
        snakeHead=new Tile(5,5);
        food=new Tile(10,10);
        random=new Random();
        placeFood();
        speedX=1;
        speedY=0;
        gameLoop=new Timer(125,this);
        gameLoop.start();
        snakeBody=new ArrayList<Tile>();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g)
    {
        /*Grid
        for(int i=0;i<boardWidth/tileSize;i++)
        {
            g.drawLine(i*tileSize, 0, i*tileSize,boardHeight);
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize);
        }*/
        //food
        g.setColor(Color.red);
        g.fill3DRect(food.x * tileSize, food.y *tileSize, tileSize, tileSize,true);
        //snakeHead
        g.setColor(Color.green);
        g.fill3DRect(snakeHead.x * tileSize,snakeHead.y * tileSize,tileSize, tileSize,true);
        //snakebody
        for(int i=0;i<snakeBody.size();i++)
        {

            Tile snakePart=snakeBody.get(i);
            g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            g.fill3DRect(snakePart.x *tileSize,snakePart.y*tileSize, tileSize, tileSize,true);
        }
        //Counting Score
        g.setFont(new Font("Arial",Font.PLAIN,16));
        if(!gameOver)
        {
            g.setColor(Color.green);
			g.setFont( new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+String.valueOf(snakeBody.size()), (boardHeight - metrics.stringWidth("Score: "+String.valueOf(snakeBody.size())))/2, g.getFont().getSize());
        }
        else{
            g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+String.valueOf(snakeBody.size()), (boardWidth - metrics1.stringWidth("Score: "+String.valueOf(snakeBody.size())))/2, g.getFont().getSize());
		//Game Over text
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (boardWidth - metrics2.stringWidth("Game Over"))/2, boardHeight/2);
        g.setColor(Color.green);
        g.setFont(new Font("Arial",Font.BOLD,40));
         g.drawString("Press Space to Restart ",100,400);
           
        }
    }
    public void placeFood()
   {
    food.x=random.nextInt(boardWidth/tileSize);
    food.y=random.nextInt(boardHeight/tileSize);
  }

  public boolean collision(Tile tile1,Tile tile2)
     {
        return tile1.x == tile2.x && tile1.y == tile2.y;
     }
    public void move()
    {
        //eating the food
        if(collision(snakeHead, food))
        {
            snakeBody.add(new Tile(food.x,food.y));
            placeFood();
        }
        //Attaching to snake body
        for(int i=snakeBody.size()-1;i>=0;i--){
            Tile snakePart=snakeBody.get(i);
            if(i==0){
            snakePart.x=snakeHead.x;
            snakePart.y=snakeHead.y;
            }
            else{
                Tile prevSnakePart=snakeBody.get(i-1);
                snakePart.x=prevSnakePart.x;
                snakePart.y=prevSnakePart.y;
            }
        }  
        //snakeHead
        snakeHead.x+=speedX;
        snakeHead.y+=speedY;
        for(int i=0;i<snakeBody.size();i++)
        {
            Tile snakePart=snakeBody.get(i);
            //checking collision of snakeHead with  snakePart
            if(collision(snakeHead, snakePart))
            {
              gameOver=true;
            }
        }
        //checking collision of snakeHead with Borders
         if(snakeHead.x * tileSize<0|| snakeHead.x*tileSize>boardWidth||snakeHead.y*tileSize<0||snakeHead.y>boardHeight)
        {
            gameOver=true;
        }
    }

    
    
    public void actionPerformed(ActionEvent a) {
        move();
        repaint();
        if(gameOver)
        {
            gameLoop.stop();
        }
    }
   
    public void keyPressed(KeyEvent e) {
       if(e.getKeyCode()==KeyEvent.VK_UP && speedY!=1)
       {
        speedX=0;
        speedY=-1; 
       }
       else if(e.getKeyCode()==KeyEvent.VK_DOWN && speedY!=-1)
       {
        speedX=0;
        speedY=1;
       } 
       else if(e.getKeyCode()==KeyEvent.VK_RIGHT && speedX!=-1)
       {
        speedX=1;
        speedY=0;
       }
       else if(e.getKeyCode()==KeyEvent.VK_LEFT && speedX!=1)
       {
        speedX=-1;
        speedY=0;
       }
       else if(e.getKeyCode()==KeyEvent.VK_SPACE)
       {  
            //snakeBody.clear(); 
            if(gameOver){
           gameOver = false;
           snakeHead=new Tile(5,5);
           placeFood();
           gameLoop=new Timer(125,this);
           gameLoop.start();
           speedX=1;
           speedY=0;
           snakeBody=new ArrayList<Tile>();
           repaint();
       }
    }
}
    public void keyTyped(KeyEvent e) { }
    public void keyReleased(KeyEvent e) { }
    }
