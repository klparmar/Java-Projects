import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class BREAKOUT extends Applet implements KeyListener, Runnable
{
  int x_pos = 200;   
  int y_pos = 272;  
  int x_speed = 1;  
  int y_speed = 1;  
  int radius = 8;  
  int appletsize_x = 400; 
  int appletsize_y = 300; 
  boolean xL = false, xR = false;
  int padX = 155, padX2 = 100, moveX = 0, moveX2 =0;
  int score1 =0, lastScore1 = 0, life = 4, z = 4;
  boolean start = false, anustart = false;
  int[][]brick;
  
  private Image dbImage;
  private Graphics dbg;
  Image backImage;
  public void init()
  {
    addKeyListener(this);
    backImage = getImage (getCodeBase(), "SPACE.gif");
    brick = new int[4][9];
  }
  
  public void start ()
  {
    
    Thread th = new Thread (this);
    th.start ();
  }
  
  public void stop()
  {
    
  }
  
  public void destroy()
  {
    
  }
  public void keyPressed(KeyEvent e){
    switch (e.getKeyCode())
    {
      case KeyEvent.VK_LEFT: xL = true; break;
      case KeyEvent.VK_RIGHT: xR = true; break;
      case KeyEvent.VK_SPACE: start = true; break;
    }
  }
  public void keyReleased(KeyEvent e){
    switch (e.getKeyCode())
    {
      case KeyEvent.VK_LEFT: xL = false; break;
      case KeyEvent.VK_RIGHT: xR = false; break;
    }
  }
  public void keyTyped(KeyEvent e){}
  
  public void run ()
  {
    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
    
    while (true)
    {
      if(start){
        paddleUpdate();
        // Ensure ball does not go out of bounds...
        if (x_pos > appletsize_x - radius)
        {
          x_speed = -1;
        }
        // Ensure ball does not go out of bounds...
        else if (x_pos < radius)
        {
          x_speed = +1;
        }
        if (y_pos > appletsize_y - radius)
        {
          // If ball falls down, lose a life a breifly increase the thread
          y_speed = -1;
          life -=1;
          x_pos = 200; 
          y_pos = 272;  
          x_speed = 1;  
          y_speed = 1;
          padX = 170;
          moveX = 0;
          z = 4;
          repaint();
          try{Thread.sleep (1000);}
          catch (InterruptedException ex){}
          
        }
        // Ensure ball does not go out of bounds...
        else if (y_pos < (radius))
        {
          y_speed = +1;
        }
        
        x_pos += x_speed;
        y_pos += y_speed;
        if(y_pos == 280  && x_pos>=padX && x_pos<(padX + 90)){ 
          y_speed = -y_speed;
        }
        
        if(y_pos<=155 && y_pos>125){z = 3;}
        if(y_pos<125 && y_pos>95){z = 2;}
        if(y_pos<95 && y_pos>65){z = 1;}
        if(y_pos<65){z = 0;}
        if(y_pos>155){z = 4;}
        if(z != 4){
          for(int q = 0; q<9; q++){
            if(x_pos >= brick[z][q] && x_pos <=(brick[z][q]+42)){
              y_speed = -y_speed; 
              if(x_pos == brick[z][q] || x_pos == (brick[z][q]+42)){x_speed = -x_speed; y_speed = -y_speed; }
              if(brick[z][q]== (45*q)){score1+=5; lastScore1 = score1;}
              brick[z][q] = -10000;
            }
          }
        }
      }
      if(lastScore1 == 180){
        lastScore1 = 0;
        anustart = true;
        y_speed = -1;
        x_pos = 200; 
        y_pos = 272;  
        x_speed = 1;  
        y_speed = 1;
        padX = 170;
        moveX = 0;
        z = 4;
        repaint();
        try{Thread.sleep (1000);}
        catch (InterruptedException ex){}
        anustart = false;
      }
      if(life == 0){
        start = false;
        life = 4;
        lastScore1 = score1;
        score1 = 0;
        z = 4;
        anustart = false;
      }
      repaint();
      try
      {
        Thread.sleep (4);
      }
      catch (InterruptedException ex)
      {
        // do nothing
      }
      Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
    }
  }
  
  public void update (Graphics g)
  {
    // Initialisierung des DoubleBuffers
    if (dbImage == null)
    {
      dbImage = createImage (this.getSize().width, this.getSize().height);
      dbg = dbImage.getGraphics ();
    }
    
    // Bildschirm im Hintergrund löschen
    dbg.setColor (getBackground ());
    dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);
    
    // Auf gelöschten Hintergrund Vordergrund zeichnen
    dbg.setColor (getForeground());
    paint (dbg);
    
    // Nun fertig gezeichnetes Bild Offscreen auf dem richtigen Bildschirm anzeigen
    g.drawImage (dbImage, 0, 0, this);
  }
  void paddleUpdate(){ //This method checks for paddle movement based on booleans activated by the keyListener
    if(xL && padX>0){
      moveX=-3;
      padX = padX + moveX;
    }
    if(xR && padX<340){
      moveX=3;
      padX = padX + moveX;
    }
  }
  public void paint (Graphics g)
  {
    g.drawImage (backImage, 0, 0, this);
    
    g.setColor  (new Color(0, 138, 230));
    g.fillRect (padX, 280, 60, 10);
    g.setFont(new Font ("MS Serif", Font.BOLD, 15));
    g.setColor  (new Color(77, 166, 255));
//    g.fillRect(0,0,400,25);
    g.setColor (Color.YELLOW);
    g.drawString("SCORE: "+score1, 10, 20);
    g.drawString("LIFE: "+life, 330, 20);
    g.setColor (Color.red);
    for(int i = 0; i<4; i++){
      for(int j = 0; j<9; j++){
        if(!start || anustart){brick[i][j] = (45 * j);}
        if(i==0){g.fillRect((brick[i][j]),(30), 42, 30);}
        if(i==1){g.setColor (Color.yellow); g.fillRect((brick[i][j]),(62), 42, 30);}
        if(i==2){g.setColor (Color.green); g.fillRect((brick[i][j]),(94), 42, 30);}
        if(i==3){g.setColor (new Color(51, 102, 204)); g.fillRect((brick[i][j]),(126), 42, 30);}
      }
    }
    g.setColor  (Color.GRAY);
    g.fillOval (x_pos - radius, y_pos - radius, 2 * radius, 2 * radius);
    if(!start){
      g.setColor  (Color.BLACK);
      g.fillRect(0, 0, appletsize_x, appletsize_y);
      g.setColor  (new Color(217, 217, 217));
      g.setFont(new Font ("MS Serif", Font.BOLD, 50));
      g.drawString("GALACTIC", 60, 60);
      g.drawString("BREAKOUT", 55, 120);
      g.setFont(new Font ("MS Serif", Font.BOLD, 15));
      g.drawString("Press Spacebar to Play", 115, 175);
      g.setColor(new Color(153, 255, 102));
      if(lastScore1!=0){g.drawString("High Score: "+lastScore1, 135, 215);}
    }
  }
}