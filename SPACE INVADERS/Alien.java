/**
 * Auto Generated Java Class.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.awt.Rectangle;

public class Alien {
  
  int x_pos;
  int y_pos;
  int y_posLaser;
  int x_posLaser;
  boolean shot;
  int x_speed = 1;
  int y_speedLaser = 1;
  
  int checkPos;
  Rectangle hitBox;
  Rectangle laserHitBox;
  
  public void create()
  {
    x_pos = 10;
    y_pos = 35;
    x_speed = 1;
    shot = false;
  }
    
  public void move(){
    x_pos+=x_speed;
    if( x_pos == 750 || x_pos == 0){
      x_speed = -x_speed;
          y_pos =y_pos +15;
    }
  }
  
  public void shift(){
    x_speed = -x_speed;
    y_pos =y_pos +15;
  }
  
  public void shoot(){
    y_posLaser += y_speedLaser;
    laserHitBox = new Rectangle(x_posLaser, y_posLaser, 10 ,20);
  }
}

