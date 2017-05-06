package entity;
//**************************************
//-Designed by Carla
//**************************************
import java.awt.Image;

import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Turtle extends Entity{
	protected static ImageIcon turtle;
	private final int WIDTH = 200;
	public Turtle( int y, int direction) {
		super( y, direction);
		
		if( direction == LEFT){
			turtle = new ImageIcon("turle-left.jpg");
			scaledImg = turtle.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST);//Scales image
			turtle = new ImageIcon(scaledImg);
		}
		else{
			turtle = new ImageIcon("turle-right.jpg");
			scaledImg = turtle.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST);//Scales image
			turtle = new ImageIcon(scaledImg);
		}
	}
	
	public void drawEntity(Graphics page){
		page.drawImage(turtle.getImage(), x, (y+SPEED), WIDTH, HEIGHT, null);
	}
}
	
	
	