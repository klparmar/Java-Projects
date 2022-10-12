import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

public class Frogger extends JFrame implements KeyListener, MouseListener, Runnable{ //all the stuff
        MyDrawPanel drawpanel1;
        int workspaceWidth=700, workspaceHeight=650;
        JButton play, reset;
        JPanel main, menu;
        int  sourceX = 340, sourceY = 347, sourceX2 = 382, sourceY2 = 382, frogX = 350, frogY = 600, carLimit = 18, speed1, speed2, seconds =0, ting = 20;
        int y = 100, y2 = 100, xGrid = 0, yGrid = 0, finnaX = 500, finnaX2, finnaX3,  finnaX4, finnaY = 225, score1 = 0, score2 = 0, pX, pY, winCount = 0, spacing1, spacing2,spacing3, spacing4, spaceRand;
        int life = 4, score= 0;
        boolean yu=false, yd=false, xl = false, xr = false, start = false, u=false, d=true, hit = false, death = false;
        Thread th = new Thread (this);
        
        int mapGrid[][] = new int[12][13];
        Enemy cars[][] = new Enemy[5][3];
        Enemy wtrObs[][] = new Enemy[5][4];
        boolean wins[] = new boolean[4];
        
        ImageIcon icon = new ImageIcon("bg.gif");
        Image backImage = icon.getImage();
        ImageIcon icon2 = new ImageIcon("oc.gif");
        Image oc = icon2.getImage();
        ImageIcon icon3 = new ImageIcon("bg2.png");
        Image backImage2 = icon3.getImage();
        ImageIcon logo = new ImageIcon("LOGO.png");
        Image logoIMG = logo.getImage();
        ImageIcon logo2 = new ImageIcon("black.png");
        Image logoIMG2 = logo2.getImage();
        
        ImageIcon icon4 = new ImageIcon("grass.png");
        Image grass = icon4.getImage();
        ImageIcon icon5 = new ImageIcon("road.png");
        Image road = icon5.getImage();
        ImageIcon icon6 = new ImageIcon("water.gif");
        Image water = icon6.getImage();
        ImageIcon pad = new ImageIcon("lilypad.png");
        Image lilypad = pad.getImage();
        
        ImageIcon icon7 = new ImageIcon("frogSheet.png");
        Image frog = icon7.getImage();
        ImageIcon icon8 = new ImageIcon("death2.gif");
        
        ImageIcon icon9 = new ImageIcon("redCar1.png");
        Image redCar = icon9.getImage();
        ImageIcon blue1 = new ImageIcon("bluecar1.png");
        ImageIcon icon10 = new ImageIcon("blueCar.png");
        Image blueCar = icon10.getImage();
        ImageIcon icon11 = new ImageIcon("carSheet.png");
        Image carSprites = icon11.getImage();
        
        ImageIcon icon12 = new ImageIcon("log.png");
        Image log = icon12.getImage();
        
        ImageIcon icon13 = new ImageIcon("turtleLeft.png");
        ImageIcon icon14 = new ImageIcon("turtleRight.png");
        Image turtle = icon13.getImage();
        
        Rectangle frogHitBox = new Rectangle(frogX, frogY, 50 , 50);
        
        Rectangle lilyPad = new Rectangle(50, 0, 50 , 50);
        Rectangle lilyPad2 = new Rectangle(250, 0, 50 , 50);
        Rectangle lilyPad3 = new Rectangle(400, 0, 50 , 50);
        Rectangle lilyPad4 = new Rectangle(600, 0, 50 , 50);
        Timer frogTime = new Timer();
        TimerTask task = new TimerTask(){
                public void run(){
                        seconds++; 
                }
        };
        
        public static void main(String[ ] args) {
                new Frogger();
        }  
        
        public Frogger(){
                drawpanel1=new MyDrawPanel();
                this.add(drawpanel1);
                
                newPattern();
                for(int i = 0; i<5; i++){ //giving the obstacles properties 
                        for(int j = 0; j<3; j++){
                                cars[i][j] = new Enemy();
                                if(i == 1 || i == 3 || i == 5){ 
                                        cars[i][j].direction = true;            
                                        cars[i][j].xPos = -50+(j*spacing2);
                                        cars[i][j].speed = speed1;
                                }
                                else{ 
                                        cars[i][j].direction = false;                     
                                        cars[i][j].xPos = 750+(j*spacing1);
                                        cars[i][j].speed = speed2;
                                }
                                cars[i][j].yPos = 550-(i*50);
                                cars[i][j].hitBox = new Rectangle(cars[i][j].xPos, cars[i][j].yPos, 50, 50);
                                if(i ==4){
                                        cars[i][j].hitBox = new Rectangle(cars[i][j].xPos, cars[i][j].yPos, 100, 50);
                                        cars[i][j].speed = 2;
                                }
                        }
                        for(int j = 0; j<4; j++){
                                wtrObs[i][j] = new Enemy();
                                wtrObs[i][j].yPos = 250-(i*50);
                                if(i==2){wtrObs[i][j].hitBox = new Rectangle(wtrObs[i][j].xPos,wtrObs[i][j].yPos,100,50);}
                                if(i == 1 || i == 3 || i == 5){ 
                                        wtrObs[i][j].direction = true; 
                                        if(i==1){wtrObs[i][j].hitBox = new Rectangle(wtrObs[i][j].xPos,wtrObs[i][j].yPos,100,50); wtrObs[i][j].xPos = -50+(j*spacing1);} 
                                        else{wtrObs[i][j].hitBox = new Rectangle(wtrObs[i][j].xPos,wtrObs[i][j].yPos,150,50); wtrObs[i][j].xPos = -50+(j*spacing3);}
                                        wtrObs[i][j].speed = speed2;
                                }
                                else{ 
                                        wtrObs[i][j].direction = false;
                                        wtrObs[i][j].hitBox = new Rectangle(wtrObs[i][j].xPos,wtrObs[i][j].yPos,150,50);
                                        wtrObs[i][j].xPos = 800+(j*spacing4);
                                        wtrObs[i][j].speed = speed1;
                                }
                        }
                }
                th.start ();
                th2.start();
                addKeyListener(this);
                addMouseListener(this);
                this.setSize(workspaceWidth+15,workspaceHeight+75);
                this.setVisible(true);
        }
        
        public void keyPressed(KeyEvent e){ //keybind movements
                if(!death && start){
                        checkCollision();
//                        checkWin();
                        switch (e.getKeyCode())
                        {
                                case KeyEvent.VK_UP: yu = true;  checkCollision(); if(frogY>0){frogY-=50;} sourceX = 289; sourceY = 148; sourceX2 = 330; sourceY2 = 190; break;
                                case KeyEvent.VK_DOWN: yd = true; checkCollision(); if(frogY<600){frogY+=50;} sourceX = 291; sourceY = 3; sourceX2 = 330; sourceY2 = 41; break;
                                case KeyEvent.VK_LEFT: xl = true;  checkCollision(); if(frogX>0){frogX-=50;} sourceX = 287; sourceY = 61; sourceX2 = 337; sourceY2 = 97; break;
                                case KeyEvent.VK_RIGHT: xr = true; checkCollision(); if(frogX<650){frogX+=50;} sourceX = 287; sourceY = 109; sourceX2 = 337; sourceY2 = 141; break;
                        }
                }
        }
        public void keyReleased(KeyEvent e){ //movement animation
                checkWin();
                checkCollision();
                switch (e.getKeyCode())
                {
                        case KeyEvent.VK_UP: yu = false; sourceX = 340; sourceY = 347; sourceX2 = 382; sourceY2 = 382; break;
                        case KeyEvent.VK_DOWN: yd = false; sourceX = 342; sourceY = 203; sourceX2 = 378; sourceY2 = 236; break;
                        case KeyEvent.VK_LEFT: xl = false; sourceX = 339; sourceY = 67; sourceX2 = 375; sourceY2 = 94; break;
                        case KeyEvent.VK_RIGHT: xr = false; sourceX = 345; sourceY = 115; sourceX2 = 380; sourceY2 = 142; break;
                }
        }
        public void keyTyped(KeyEvent e){}
        
        public void mousePressed(MouseEvent e) { //title screen stuff
                if(!start){
                  score1 = 0; score = 0; life =4;
                  Timer frogTime = new Timer();
                  TimerTask task = new TimerTask(){
                    public void run(){
                      seconds++; 
                    }
                  };
                   frogTime.scheduleAtFixedRate(task, 1000, 1000);
                }
                start = true;
        }
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}
        
        public void run(){
                while(true){
                        if(start){
//                                checkCollision();
                                frogHitBox = new Rectangle(frogX, frogY, 50 , 50);

                               
                                
                                if((frogY<300 && hit == true) || (frogY>300 && hit) || (seconds==60)){  //collision extra
                                        death = true;
                                        frog = icon8.getImage();
                                        seconds = 0;
                                        try{Thread.sleep (2000);}
                                        catch (InterruptedException ex){}
                                }
                                
                                if(death){
                                        life--;
                                        frog = icon7.getImage();
                                        frogX = 350; frogY = 600;
                                        frogHitBox = new Rectangle(frogX, frogY, 50 , 50);
                                        hit = false;
                                        death = false;
                                        seconds = 0;
                                }
                                
                                if(winCount == 4){  //code for level up
                                        seconds = 0;
                                        if(ting!=8){ting-=4;}
                                        frogX = 350; frogY = 600;
                                        frogHitBox = new Rectangle(frogX, frogY, 50 , 50);
                                        if(carLimit != 6){ carLimit-=3;}
                                        newPattern();
                                        for(int i = 0; i<5; i++){
                                                for(int j = 0; j<3; j++){
                                                        if( cars[i][j].direction == true){ 
                                                                cars[i][j].direction = false;            
                                                                cars[i][j].xPos = 750+(j*250);
                                                        }
                                                        else{ 
                                                                cars[i][j].direction = true;                     
                                                                cars[i][j].xPos = -50+(j*-250);
                                                        }
                                                        cars[i][j].yPos = 550-(i*50);
                                                        cars[i][j].hitBox = new Rectangle(cars[i][j].xPos, cars[i][j].yPos, 50, 50);
                                                        if(i ==4){
                                                                cars[i][j].hitBox = new Rectangle(cars[i][j].xPos, cars[i][j].yPos, 100, 50);
                                                                if(cars[i][j].direction == false){redCar = icon9.getImage();}else{redCar = blue1.getImage();}
                                                        }
                                                }
                                                for(int j = 0; j<4; j++){
                                                        wins[j] = false;
                                                        if( wtrObs[i][j].direction == true){ 
                                                                wtrObs[i][j].direction = false;            
                                                                wtrObs[i][j].xPos = 800+(j*250);
                                                                turtle = icon13.getImage();
                                                        }
                                                        else{ 
                                                                wtrObs[i][j].direction = true;                     
                                                                wtrObs[i][j].xPos = -50+(j*-250);
                                                                turtle = icon14.getImage();
                                                        }
                                                        wtrObs[i][j].yPos = 250-(i*50);
//                    if(i == 1 || i == 3 || i == 5){cars[i][j].direction = true;}
//                    else{cars[i][j].direction = false;}
                                                }
                                        }
                                        try{Thread.sleep (2000);}
                                        catch (InterruptedException ex){}
                                        winCount = 0;
                                }
                                if(life == 0){
                                        start = false;
                                        ting = 20;
                                        for(int j = 0; j<4; j++){
                                                        wins[j] = false;
                                        }
                                        newPattern();
                                }
                                try{Thread.sleep (4);}
                                catch (InterruptedException ex){}
                        }
                }
        }
        Thread th2 = new Thread () {
                
                public void run () {
                        while (true){
                                if(start){ //movement of obstacles & hitboxes
                                        checkCollision();
                                        for(int i = 0; i<5; i++){
                                                for(int j = 0; j<3; j++){
                                                        cars[i][j].move();
                                                        cars[i][j].hitBox = new Rectangle(cars[i][j].xPos, cars[i][j].yPos,50,50);
                                                        if(i == 4){ cars[i][j].hitBox = new Rectangle(cars[i][j].xPos, cars[i][j].yPos,100,50);}
                                                }
                                                for(int j = 0; j<4; j++){
                                                        wtrObs[i][j].move();
                                                        if(i==1){wtrObs[i][j].hitBox = new Rectangle(wtrObs[i][j].xPos, wtrObs[i][j].yPos,100,50);}
                                                        else{wtrObs[i][j].hitBox = new Rectangle(wtrObs[i][j].xPos, wtrObs[i][j].yPos,150,50);}
                                                }
                                        }
                                        repaint();
                                        try{ Thread.sleep (ting); }
                                        catch (InterruptedException ex){}
                                        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                                }
                        }
                }
        };
        
        void checkCollision(){ //check collison
//          if(frogY==300){hit = true;}
////          if(frogY <300){hit = false;}
                
                
                
                
                if(frogY>300)
                {
                        for(int i = 0; i<5; i++){
                                for(int j = 0; j<3; j++){
                                        if(cars[i][j].hitBox.intersects(frogHitBox)){
                                                hit = true;
                                        }
                                }
                        }
                }     
                
                else if(frogY<300 && frogY>0){
                        int flag=0;
                        for(int i = 0; i<5; i++){
                                for(int j = 0; j<4; j++){
                                        if(frogHitBox.intersects(wtrObs[i][j].hitBox)){
//                                                System.out.println("Doing this "+i+", "+j);
                                                hit = false;
                                                flag=1;
                                                  if(wtrObs[i][j].direction){if(frogX != 650){frogX+=wtrObs[i][j].speed;}}else{if(frogX != 0){frogX-=wtrObs[i][j].speed;}}
                                        }
//                                        else{System.out.println("Doing this too"+i+", "+j);
//                                                hit = true;}
                                }
                        }
                        if(flag==0){
                                hit = true;}
                        
                }
                
                
                
        }
        
        
        void checkWin(){ //check wins
                if(frogY==0) {
                        if(lilyPad.intersects(frogHitBox) && !wins[0]){ wins[0] = true; finnaX = frogX; score+=100; }
                        else if(lilyPad2.intersects(frogHitBox) && !wins[1]) {wins[1] = true; finnaX2 = frogX; score+=100; }
                        else if(lilyPad3.intersects(frogHitBox) && !wins[2]) {wins[2] = true; finnaX3 = frogX; score+=100; } 
                        else if(lilyPad4.intersects(frogHitBox) && !wins[3]) {wins[3] = true; finnaX4 = frogX; score+=100; }
                         else{hit =true; System.out.println("he");}
                        frogX = 350; frogY = 600; seconds = 0;
                }
                for(int q = 0; q<4; q++){if(wins[q]){winCount++;}}
                if(winCount !=4){winCount = 0;}
        }
        
        void newPattern(){
                          spaceRand = (int)(Math.random()*5) + 1;
                
                if(spaceRand == 2){
                        spacing1 = 250;
                        spacing2 = 350;
                        spacing3 = 300;
                        spacing4 = 400;
                        speed1 = 1;
                        speed2 = 2;
                }
                else if(spaceRand == 2){
                        spacing1 = 250;
                        spacing2 = 250;
                        spacing3 = 300;
                        spacing4 = 300;
                        speed1 = 1;
                        speed2 = 1;
                }
                else if(spaceRand == 3){
                        spacing1 = 150;
                        spacing2 = 250;
                        spacing3 = 200;
                        spacing4 = 300;
                        speed1 = 2;
                        speed2 = 2;
                }
                else if(spaceRand == 4){
                        spacing1 = 250;
                        spacing2 = 150;
                        spacing3 = 300;
                        spacing4 = 200;
                        speed1 = 2;
                        speed2 = 2;
                }
                else{
                        spacing2 = 250;
                        spacing1 = 350;
                        spacing3 = 400;
                        spacing4 = 300;
                        speed1 = 2;
                        speed2 = 1;
                }
        }
        class MyDrawPanel extends JPanel { //graphics
                
                public void paintComponent(Graphics g) {
                        //Converts the ordinay graphics object into a Graphics2d object
                        Graphics2D g2 = (Graphics2D)g;
                        //This line takes away the jaggedness and smooths out the lines
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                        
                        //Graphics commands go here
                        
                        AffineTransform old = g2.getTransform();
                        
                        g2.setColor(new Color(0, 0, 0));
                        g2.fillRect(0, 0, 1000, 1000);
                        
                        g2.setColor(new Color(26, 255, 198));
                        if(!start){
                                g2.drawImage (backImage, 0, 0, workspaceWidth, workspaceWidth+40,  this);
                                g2.drawImage (oc, 0, 350, 500, 690, 0, 50, 200, 200, null);
                                g2.setFont(new Font("MS Serif", Font.PLAIN, 120));
                                if(score != 0){
                                        g2.setFont(new Font("MS Serif", Font.PLAIN, 40));
                                        g2.drawString("Last Score: "+score, 420, 265);
                                        g2.drawString("HighScore: "+score, 430, 265);
                                }
                                else{
                                        g2.setFont(new Font("MS Serif", Font.PLAIN, 40));
                                        g2.drawImage(logoIMG2, 400, 0, 300, 690, null);
                                        g2.drawImage(logoIMG, 420, 40, 250, 180, null);
                                        g2.drawString("PLAY", 480, 280);
                                }
                        }
                        else{
                                for(int i =0; i<14; i++){
                                        g2.drawImage (grass, (i*50), 600, 50, 50, this);
                                        g2.drawImage (road, (i*50), 550, 50, 50, this);
                                        g2.drawImage (road, (i*50), 500, 50, 50, this);
                                        g2.drawImage (road, (i*50), 450, 50, 50, this);
                                        g2.drawImage (road, (i*50), 400, 50, 50, this);
                                        g2.drawImage (road, (i*50), 350, (i*50)+50, 400, 0, 22, 191, 191, null);
                                        g2.drawImage (grass, (i*50), 300, 50, 50, this);
                                        g2.drawImage (water, (i*50), 250, 50, 50, this);
                                        g2.drawImage (water, (i*50), 200, 50, 50, this);
                                        g2.drawImage (water, (i*50), 150, 50, 50, this);
                                        g2.drawImage (water, (i*50), 100, 50, 50, this);
                                        g2.drawImage (water, (i*50), 50, 50, 50, this);
                                        g2.drawImage (water, (i*50), 0, 50, 50, this);
                                        if(i ==1 || i ==5 || i ==8 || i ==12){g2.drawImage (lilypad, (i*50), 0, 50, 50, this);}
                                } 
                                
                                for(int j = 0; j<3; j++){
                                        g2.drawImage(blueCar, cars[0][j].xPos, cars[0][j].yPos, 50, 50, null);
//                                  g2.drawRect(cars[0][j].xPos, cars[0][j].yPos, 50, 50);
                                }
                                for(int j = 0; j<3; j++){
                                        g2.drawImage(carSprites, cars[1][j].xPos, cars[1][j].yPos, cars[1][j].xPos+50, cars[1][j].yPos+50, 50, 16, 207, 111, null);
//                                  g2.drawRect(cars[1][j].xPos, cars[1][j].yPos, 50, 50);
                                }
                                for(int j = 0; j<3; j++){
                                        g2.drawImage(carSprites, cars[2][j].xPos, cars[2][j].yPos, cars[2][j].xPos+50, cars[2][j].yPos+50, 50, 214, 207,  304, null);
//                                  g2.drawRect(cars[2][j].xPos, cars[2][j].yPos, 50, 50);
                                }
                                for(int j = 0; j<3; j++){
                                        g2.drawImage(carSprites, cars[3][j].xPos, cars[3][j].yPos, cars[3][j].xPos+50, cars[3][j].yPos+50, 53, 394, 207,  485, null);
//                                  g2.drawRect(cars[3][j].xPos, cars[3][j].yPos, 50, 50);
                                }
                                for(int j = 0; j<3; j++){
                                        g2.drawImage(redCar, cars[4][j].xPos, cars[4][j].yPos, 100, 50, null);
//                                  g2.drawRect(cars[4][j].xPos, cars[4][j].yPos, 100, 50);
                                }
                                
                                for(int i = 0; i<5; i++){
                                        for(int j = 0; j<4; j++){
                                          if(i == 3 || i == 5){ g2.drawImage(log, wtrObs[i][j].xPos, wtrObs[i][j].yPos, 150, 50, null);}
                                          else if(i == 1){g2.drawImage(log, wtrObs[i][j].xPos, wtrObs[i][j].yPos, 100, 50, null);}
                                          else{ g2.drawImage(turtle, wtrObs[i][j].xPos, wtrObs[i][j].yPos, 150, 50, null);}
//                                                g2.drawRect(wtrObs[i][j].xPos, wtrObs[i][j].yPos, 150, 50);
                                        }
                                }
                                
                                if(!death){g2.drawImage(frog, frogX, frogY, frogX+50,frogY+50, sourceX, sourceY, sourceX2, sourceY2, null);}
                                if(death){g2.drawImage(frog, frogX, frogY, 50, 50, null);}
//                                g2.drawRect(frogX, frogY, 50, 50);
//                                g2.fillRect(carRow[1][2].xPos, carRow[1][2].xPos, 50, 50);
                                
                                if(wins[0]){ g2.drawImage(frog, 60, 0, 50+40, 40, 342, 203, 378, 236, null); }
                                if(wins[1]){ g2.drawImage(frog, 260, 0, 250+40, 40, 342, 203, 378, 236, null); }
                                if(wins[2]){ g2.drawImage(frog, 410, 0, 400+40, 40, 342, 203, 378, 236, null); }
                                if(wins[3]){ g2.drawImage(frog, 610, 0, 600+40, 40, 342, 203, 378, 236, null); }
                                g2.setFont(new Font("MS Serif", Font.BOLD, 15));
                                g2.drawString("LIFE: "+life, 30, 670); g2.drawString("SCORE: "+score, 600, 670);
                                g2.setFont(new Font("MS Serif", Font.BOLD, 20));
                                g2.drawString("TIME: "+(60-seconds), 300, 670);
                                
                        }
                }
        }
}