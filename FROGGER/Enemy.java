/**
 * Auto Generated Java Class.
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.Rectangle;

public class Enemy {
        
        /* ADD YOUR CODE HERE */
        int xPos, yPos = 550;
        int Cap;
        boolean direction = true;
        int speed = 1;
        Rectangle hitBox;
        
        public void move(){
                if(direction){
                        xPos+=speed;
                        if(xPos==900 && speed>=3){
                                xPos = -100;
                                System.out.println("ch");
                        }
                        else if(xPos==750 && speed<3){
                                xPos = -100;
                        }
                        
                }
                if(!direction){
                        xPos-=speed;
                        if(xPos==-250 && speed>=3){
                                xPos = 750;
                                System.out.println("ch");
                        }
                        else if(xPos==-100 && speed<3){
                                xPos = 750;
                        }
                }
        }
        
}

