import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Pong extends JFrame implements KeyListener, MouseListener, Runnable{ 
  MyDrawPanel drawpanel1;
  int workspaceWidth=1000, workspaceHeight=500;
  JLabel title;
  JButton play, reset;
  JPanel main, menu;
  int y = 100, y2 = 100, moveY = 0, moveY2 = 0, ballPosX = 500, ballPosY = 225, score1 = 0, score2 = 0, pX, pY, regHit = 0;
  double motionX = 2.00, motionY = 2.00;
  boolean yu=false, yd=false, yu2 = false, yd2 = false, start = false, u=false, d=true, bold = false, termVelocity = false;
  Thread th = new Thread (this);
  
  public static void main(String[ ] args) 
  {
    new Pong();
  }  
  
  public Pong(){
    drawpanel1=new MyDrawPanel();
    this.add(drawpanel1);
    
    th.start ();
    
    addKeyListener(this);
    addMouseListener(this);
    this.setSize(1015,535);
    this.setVisible(true);
  }
  
  public void keyPressed(KeyEvent e){
    switch (e.getKeyCode())
    {
      case KeyEvent.VK_UP: yu2 = true; break;
      case KeyEvent.VK_DOWN: yd2 = true; break;
      case KeyEvent.VK_W: yu = true; break;
      case KeyEvent.VK_S: yd = true; break;
    }
  }
  public void keyReleased(KeyEvent e){
    switch (e.getKeyCode())
    {
      case KeyEvent.VK_UP: yu2 = false; break;
      case KeyEvent.VK_DOWN: yd2 = false; break;
      case KeyEvent.VK_W: yu = false; break;
      case KeyEvent.VK_S: yd = false; break;
    }
  }
  public void keyTyped(KeyEvent e){}
  
  public void mousePressed(MouseEvent e) {
    if(!start){score1 = 0; score2 = 0;}
    start = true;
  }
  public void mouseReleased(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  public void mouseClicked(MouseEvent e) {}
  
  public void run(){
    while(true){
      if(start){
        if(score1  == 10 || score2 == 10){start = false;}
        paddleUpdate();
        if (ballPosY < 0 || ballPosY > 475)
        {
          motionY = -motionY;
        }
        if((ballPosX == 50  && ballPosY>y && ballPosY<(y + 150)) || (ballPosX == 950  && ballPosY>y2 && ballPosY<(y2 + 150))){ //This checks for when the ball is in contact with either paddle
          if(!termVelocity &&(yu && (ballPosY > y && ballPosY <(y+25))) || (yd && (ballPosY > (y+125) && ballPosY <(y+150)))){  //If paddle1 is in motion and the ball hits a corner, the speed increasaes.
            if(yu){motionX = 4;motionY = 4;}
            if(yd){motionX = 4;motionY = -4;}
          }
          else if(!termVelocity &&(yu2 && (ballPosY > y2 && ballPosY <(y2+25))) || (yd2 && (ballPosY > (y2+125) && ballPosY <(y2+150)))){  //If paddle1 is in motion and the ball hits a corner, the speed increasaes.
            if(yu2){motionX = -4;motionY = 4;}
            if(yd2){motionX = -4;motionY = -4;}
          }
          else{
            regHit++;
            if(regHit == 3 && motionX<4){motionX = motionX / 0.66; motionY = motionY / 0.66;}
            if(regHit == 8 && motionX<4){motionX = motionX * 1.33; motionY = motionY * 1.33;}
            if(regHit == 13){motionX = motionX *1.25; motionY = motionY *1.25; termVelocity = true;} //This is the max. ball speed, which is why terminal velocity is set as "true"
            motionX = -motionX;
          }
        }
        if(ballPosX < 25 || (ballPosX+25) > 975){   //This checks when a player has scored, resets variables and slightly increases the thread to prepare the players for the next round
          if(ballPosX<25){score2++;}
          if((ballPosX+25) > 975){score1++;}
          ballPosX = 500;
          ballPosY = 225;
          y = 100;
          y2 = 100;
          motionY = 2.00;
          motionX = -2.00;
          regHit = 0;
          repaint();
          try{Thread.sleep (1000);}
          catch (InterruptedException ex){}
        }
        ballPosX = ballPosX + ((int)motionX);
        ballPosY = ballPosY + ((int)motionY);
        repaint();
        try{Thread.sleep (4);}
        catch (InterruptedException ex){}
      }
    }
  }
  
  void paddleUpdate(){ //This method checks for paddle movement based on booleans activated by the keyListener
    if(yu && y>0){
      moveY=-3;
      y = y + moveY;
    }
    if(yd && y<350){
      moveY=3;
      y = y + moveY;
    }
    if(yu2 && y2>0){
      moveY2=-3;
      y2 = y2 + moveY2;
    }
    if(yd2 && y2<350){
      moveY2=3;
      y2 = y2 + moveY2;
    }
  }
  class MyDrawPanel extends JPanel {
    
    public void paintComponent(Graphics g) {
      //Converts the ordinay graphics object into a Graphics2d object
      Graphics2D g2 = (Graphics2D)g;
      //This line takes away the jaggedness and smooths out the lines
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
      
      //Graphics commands go here
      
      AffineTransform old = g2.getTransform();
      
      g2.fillRect(0, 0, 1000, 575);
      
      g2.setColor(Color.WHITE);
      if(!start){
        g2.setFont(new Font("Century Gothic", Font.PLAIN, 120));
        if(score1 == 10 || score2 == 10){
          if(score1 == 10){g2.drawString("PLAYER 1 WINS", 100, 175);}
          if(score2 == 10){g2.drawString("PLAYER 2 WINS", 100, 175);}
          g2.setFont(new Font("Century Gothic", Font.PLAIN, 40));
          g2.drawString("Play Again", 420, 265);
        }
        else{
          g2.drawString("PONG", 325, 175);
          g2.setFont(new Font("Century Gothic", Font.PLAIN, 40));
          g2.drawString("Play", 460, 265);
        }
      }
      else{
        g2.setFont(new Font("Century Gothic", Font.PLAIN, 80));
        g2.drawString((""+score1), 150, 125);
        g2.drawString((""+score2), 825, 125);
        
        g2.fillRect(25, y, 25, 150);
        g2.fillRect(950, y2, 25, 150);
        
        g2.fillRect(ballPosX, ballPosY, 25, 25);
      }
      g2.setFont(new Font("Century Gothic", Font.PLAIN, 10));
    }
  }
}