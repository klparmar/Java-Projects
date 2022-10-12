import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class SpInv extends JFrame implements KeyListener, Runnable
{
   
  int radius = 8;  
  int size_x = 820; 
  int size_y = 600; 
  boolean xL = false, xR = false, shoot = false, shift = false;
  int padX = 155, padX2 = 100, moveX = 0, moveX2 =0, laserLim = 0, check, r = 0;
  int a1 = 300, b1, a2 = 300, b2, a3, b3;
  int score1 =0, lastScore1 = 0, life = 4, z = 4;
  boolean start = false;
  int[][]brick;
  Alien[][]aliens = new Alien[5][12];
  Funky funkyKong = new Funky();
  Spaceship ship = new Spaceship();
  Lasers shipLaser = new Lasers();
  Lasers alienLaser = new Lasers();
  Barrier[]Barriers = new Barrier[4];
  
  private Image dbImage;
  private Graphics dbg;
  ImageIcon icon = new ImageIcon("logo.png");
  Image logoImage = icon.getImage();
  ImageIcon icon2 = new ImageIcon("bg1.gif");
  Image backImage = icon2.getImage();
  ImageIcon icon3 = new ImageIcon("alien1.gif");
  Image alien1 = icon3.getImage();
  ImageIcon icon4 = new ImageIcon("ship.png");
  Image shipIMG = icon4.getImage();
  ImageIcon icon5 = new ImageIcon("alien2.gif");
  Image alien2 = icon5.getImage();
  ImageIcon icon6 = new ImageIcon("alien3.gif");
  Image alien3 = icon6.getImage();
  ImageIcon icon7 = new ImageIcon("bg.gif");
  Image backImage1 = icon7.getImage();
  ImageIcon icon8 = new ImageIcon("laser1.png");
  Image laser1 = icon8.getImage();
  ImageIcon icon9 = new ImageIcon("funkyKong.gif");
  Image funk = icon9.getImage();
  
  ImageIcon  sides = new ImageIcon("slideTING.png");
  ImageIcon  sidesDMG1 = new ImageIcon("slideTingScuff1.png");
  ImageIcon  sidesDMG2 = new ImageIcon("slideTingScuff2.png");
  ImageIcon  sidesDMG3 = new ImageIcon("slideTingScuff3.png");
  Image side1 =sides.getImage();  Image side2 =sides.getImage();
  
  ImageIcon  top = new ImageIcon("topTing1.png");
  ImageIcon  topDMG1 = new ImageIcon("topTingScuff.png");
  ImageIcon  topDMG2 = new ImageIcon("topTingScuff2.png");
  ImageIcon  topDMG3 = new ImageIcon("topTingScuff3.png");
  Image top1 = top.getImage();
  
  Rectangle shipHitBox = new Rectangle(padX, 480, 40 , 40);
  public static void main(String[]args){ new SpInv(); }  
  
  public SpInv ()
  {
    for(int q = 0; q<4; q++){
      Barriers[q] = new Barrier();
      Barriers[q].side1HitBox = new Rectangle(40+(q*204), 350, 25, 100);
      Barriers[q].topHitBox = new Rectangle(65+(q*204), 310, 50 , 80);
      Barriers[q].side2HitBox = new Rectangle(115+(q*204), 350, 25, 100);
      
      Barriers[q].side1 =sides.getImage();
      Barriers[q].top1 = top.getImage();
      Barriers[q].side2 =sides.getImage();
    }
    shipLaser.hitBox = new Rectangle(padX, shipLaser.y_pos, 10 ,20);
    Thread th = new Thread (this);
    th.start ();

    th2.start();
    for(int i = 0; i<5; i++){
      for(int j = 0; j<12; j++){
        aliens[i][j] = new Alien();
        aliens[i][j].create();
        aliens[i][j].hitBox = new Rectangle(aliens[i][j].x_pos+(45*j),aliens[i][j].y_pos+(45*i),50,45);
      }
    }
    funkyKong.create();
    MyDrawPanel drawPanel = new MyDrawPanel();
    addKeyListener(this);
    this.add(drawPanel);
    this.setSize(size_x, size_y);
    this.setVisible(true);
    this.setResizable(false);
  }
  
    
  public void keyPressed(KeyEvent e){
    switch (e.getKeyCode())
    {
      case KeyEvent.VK_LEFT: xL = true; break;
      case KeyEvent.VK_RIGHT: xR = true; break;
      case KeyEvent.VK_ENTER: start = true; break;
      case KeyEvent.VK_SPACE: shoot = true; break;
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
        for(int i = 0; i<5; i++){
          for(int j = 0; j<12; j++){
            aliens[i][j].checkPos = aliens[i][j].x_pos+(45*j);
            aliens[i][j].move();
            aliens[i][j].hitBox = new Rectangle(aliens[i][j].x_pos+(45*j),aliens[i][j].y_pos+(45*i),50,45);
            if(laserLim < 3 && !aliens[i][j].shot){int check = (int) (Math.random() * 100); System.out.println(check);
              if(check <=30){
                laserLim++;
                if(a1 != 300){a2 = i; b2 = j;               aliens[a2][b2].y_posLaser = aliens[a2][b2].y_pos+(45*a1); aliens[a2][b2].x_posLaser = aliens[a2][b2].x_pos+(45*b1);}
                else if(a2 !=300){a3 = i; b3 = i;}
                else{ a1 = i; b1 = j;               aliens[a1][b1].y_posLaser = aliens[a1][b1].y_pos+(45*a1); aliens[a1][b1].x_posLaser = aliens[a1][b1].x_pos+(45*b1);}
              }
//            System.out.println(a1);
            }
            if(laserLim == 3){
//              aliens[i][j].y_posLaser = aliens[i][j].y_pos+(45*i); aliens[i][j].x_posLaser = aliens[i][j].x_pos+(45*j);
//              aliens[i][j].shoot();
              aliens[a1][b1].shoot();
              aliens[a2][b2].shoot();
//              System.out.println("he"+laserLim);
            }
//            if(check <=30){
//              laserLim++;
//              if(a1 != 300){a2 = i; b2 = j;}
//              else if(a2 !=300){a3 = i; b3 = i;}
//              else{ a1 = i; b1 = j;} }
//            System.out.println(a1);
            }
          }
        }
      if(life == 0){
        start = false;
        life = 4;
        lastScore1 = score1;
        score1 = 0;
        z = 4;
      }
      repaint();
      try
      {
        Thread.sleep (100);
      }
      catch (InterruptedException ex)
      {
        // do nothing
      }
      Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
    }
  }
  Thread th2 = new Thread () {
    public void run () {
      while (true){
        paddleUpdate();
        if(shoot){
          shipLaser.shoot();
          if(shipLaser.y_pos == 0){shoot = false; shipLaser.y_pos = 490; }
        }
        else{shipLaser.x_pos = (padX+20);}
        for(int i = 0; i<5; i++){
          for(int j = 0; j<12; j++){
            if(aliens[i][j].hitBox.intersects(shipLaser.hitBox)){
              shipLaser.hitBox = new Rectangle(padX, 490, 10 ,20);
              System.out.println("he"+i+j);
              aliens[i][j].y_pos = -8000000;
              aliens[i][j].shot = true;
              shoot = false;
              shipLaser.y_pos = 490;
              if(i > 3){score1+=10;}
              if(i >0 && i<3){score1+=20;}
              if(i == 0){score1+=30;}
            }
          }
        }
        funkyKong.move();
        funkyKong.hitBox = new Rectangle(funkyKong.x_pos, funkyKong.y_pos,50,45);
        if(funkyKong.hitBox.intersects(shipLaser.hitBox)){
          shipLaser.hitBox = new Rectangle(padX, 490, 10 ,20);
          shoot = false;
          shipLaser.y_pos = 490;
          score1+=100;
          funkyKong.x_pos = -3050;
          funkyKong.y_pos = 15;
          funkyKong.x_speed = 1;
        }
//        if(laserLim == 3){
//          alienLaser.y_pos = aliens[a1][b1].y_pos+(45*i); alienLaser.x_pos = aliens[a1][b1].x_pos+(45*j);
//          alienLaser.y_pos = aliens[a2][b2].y_pos+(45*i); alienLaser.x_pos = aliens[a2][b2].x_pos+(45*j);
//          alienLaser.shoot();
//          aliens[a2][b2].shoot();
//        }
        for(int q = 0; q<4; q++){
          if(Barriers[q].side1HitBox.intersects(shipLaser.hitBox)){
            Barriers[q].sideHit1++;
            shipLaser.hitBox = new Rectangle(padX, 490, 10 ,20);
            shoot = false;
            shipLaser.y_pos = 490;
            if(Barriers[q].sideHit1 == 3){Barriers[q].side1 = sidesDMG1.getImage();}
            if(Barriers[q].sideHit1 == 6){Barriers[q].side1 = sidesDMG2.getImage();}
            if(Barriers[q].sideHit1 == 9){Barriers[q].side1 = sidesDMG3.getImage();}
            if(Barriers[q].sideHit1 > 12){Barriers[q].side1HitBox = new Rectangle(2000+(q*204), 350, 25, 100);}
          }
          if(Barriers[q].side2HitBox.intersects(shipLaser.hitBox)){
            Barriers[q].sideHit2++;
            shipLaser.hitBox = new Rectangle(padX, 490, 10 ,20);
            shoot = false;
            shipLaser.y_pos = 490;
            if(Barriers[q].sideHit2 == 3){Barriers[q].side2 = sidesDMG1.getImage();}
            if(Barriers[q].sideHit2 == 6){Barriers[q].side2 = sidesDMG2.getImage();}
            if(Barriers[q].sideHit2 == 9){Barriers[q].side2 = sidesDMG3.getImage();}
            if(Barriers[q].sideHit2 > 12){Barriers[q].side2HitBox = new Rectangle(3000+(q*204), 350, 25, 100);}
          }
          if(Barriers[q].topHitBox.intersects(shipLaser.hitBox)){
            Barriers[q].topHit++;
            shipLaser.hitBox = new Rectangle(padX, 490, 10 ,20);
            shoot = false;
            shipLaser.y_pos = 490;
            if(Barriers[q].topHit == 3){Barriers[q].top1 = topDMG1.getImage();}
            if(Barriers[q].topHit == 6){Barriers[q].top1 = topDMG2.getImage();}
            if(Barriers[q].topHit == 9){Barriers[q].top1 = topDMG3.getImage();}
            if(Barriers[q].topHit > 12){Barriers[q].topHitBox = new Rectangle(4000+(q*204), 4350, 25, 100);}
          }
        }
        try{ Thread.sleep (4); }
        catch (InterruptedException ex){}
      }
    }
  };
  
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
    if(xR && padX<760){
      moveX=3;
      padX = padX + moveX;
    }
    shipHitBox = new Rectangle(padX, 480, 40 , 40);
  }
  class MyDrawPanel extends JPanel {
    public void paint (Graphics g)
    { 
      if(start){
      g.drawImage(backImage1, 0, 0, size_x, size_y, this);
      if(shoot){g.drawImage (laser1, shipLaser.x_pos, shipLaser.y_pos, 10, 20, this);}
      g.setColor  (new Color(0, 138, 230));
      g.drawImage (shipIMG, padX, 480, 40, 40, this);
      g.setFont(new Font ("MS Serif", Font.BOLD, 15));
      g.setColor  (new Color(77, 166, 255));
      g.setColor (Color.YELLOW);
      g.drawString("SCORE: "+score1, 10, 20);
      g.drawString("LIFE: "+life, 330, 20);
      g.setColor (Color.red);
      if(laserLim == 3){
      g.drawImage (laser1, aliens[a1][b1].x_posLaser, aliens[a1][b1].y_posLaser, 10, 20, this);
      g.drawImage (laser1, aliens[a2][b2].x_posLaser, aliens[a2][b2].y_posLaser, 10, 20, this);
      }
      g.drawImage(funk, funkyKong.x_pos, funkyKong.y_pos, 80, 80, this);
      for(int i = 0; i<5; i++){
        for(int j = 0; j<12; j++){
//          if(!start){brick[i][j] = (45 * j);}
          if(i==0 && !aliens[i][j].shot){g.drawImage(alien2,(aliens[i][j].x_pos+(45*j)), (aliens[i][j].y_pos+(52*i)), 50, 50, this);}
          if(i==1 && !aliens[i][j].shot){g.drawImage(alien1,(aliens[i][j].x_pos+(45*j)), (aliens[i][j].y_pos+(52*i)), 50, 50, this);}
          if(i==2 && !aliens[i][j].shot){g.drawImage(alien1,(aliens[i][j].x_pos+(45*j)), (aliens[i][j].y_pos+(52*i)), 50, 50, this);}
          if(i==3 && !aliens[i][j].shot){g.drawImage(alien3,(aliens[i][j].x_pos+(45*j)), (aliens[i][j].y_pos+(52*i)), 50, 50, this);}
          if(i==4 && !aliens[i][j].shot){g.drawImage(alien3,(aliens[i][j].x_pos+(45*j)), (aliens[i][j].y_pos+(52*i)), 50, 50, this);}
//          if(i==3){g.setColor (new Color(51, 102, 204)); g.fillRect((brick[i][j]),(126), 42, 30);}
        }
      }
      for(int q=0; q<4; q++){
        if(Barriers[q].sideHit1 < 12){g.drawImage(Barriers[q].side1, 40+(q*204), 350, 25, 100, this);}
        if(Barriers[q].topHit < 12){g.drawImage(Barriers[q].top1, 65+(q*204), 350, 50, 80, this);}
        if(Barriers[q].sideHit2 < 12){g.drawImage(Barriers[q].side2, 115+(q*204), 350, 25, 100, this);}
      }
    }
//      g.fillOval (x_pos - radius, y_pos - radius, 2 * radius, 2 * radius);
      if(!start){
        g.setColor  (Color.BLACK);
        g.drawImage(backImage, 0, 0, size_x, size_y, this);
        g.setColor  (new Color(217, 217, 217));
        g.setFont(new Font ("MS Serif", Font.BOLD, 50));
        g.drawImage(logoImage, 170, 30, 450, 220, this);
        g.setColor(new Color(0, 230, 0));
        g.drawString("Press ENTER to Play", 170, 340);
//        g.drawString("SpInv", 55, 120);
        g.setFont(new Font ("MS Serif", Font.BOLD, 15));
//        g.drawString("Press Spacebar to Play", 115, 175);
//        if(lastScore1!=0){g.drawString("High Score: "+lastScore1, 135, 215);}
//        ((aliens[i][j].y_pos+(45*i))+50) && (shipLaser.x_pos+5 >= aliens[i][j].x_pos+(45*j)) && (shipLaser.x_pos+5 <= ((aliens[i][j].x_pos+(45*j))+50))
//        || Barriers[q].side2HitBox.intersects(alienLaser.hitBox)
      }
    }
  }
}