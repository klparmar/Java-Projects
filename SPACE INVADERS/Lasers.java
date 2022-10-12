/**
 * Auto Generated Java Class.
 */
import java.awt.Rectangle;

public class Lasers {
  
  /* ADD YOUR CODE HERE */
  boolean shoot = false;
  int y_pos = 490;
  int y_speed;
  int x_pos = -90;
  Rectangle hitBox;
    
  public void shoot(){
    y_speed = -2;
    y_pos+=y_speed;
    hitBox = new Rectangle(x_pos, y_pos, 10 ,20);
  }
}
