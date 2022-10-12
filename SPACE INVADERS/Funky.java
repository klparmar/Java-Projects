/**
 * Auto Generated Java Class.
 */
import java.awt.Rectangle;

public class Funky {
  
  /* ADD YOUR CODE HERE */
    int x_pos;
  int y_pos;
  boolean shot;
  int x_speed;
  
  Rectangle hitBox;
  
  public void create()
  {
    x_pos = -3050;
    y_pos = 15;
    x_speed = 1;
    shot = false;
    hitBox = new Rectangle(x_pos, y_pos,50,45);
  }
    
  public void move(){
  x_pos+=x_speed;
  if(x_pos == -3051 || x_pos == 3050){
    x_speed = -x_speed;
  }
}
}
