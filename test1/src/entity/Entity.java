package entity;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;

public class Entity{
	protected final int HEIGHT = 120, SPEED = 1; 
	protected int x;
	protected int y;
	protected int direction; //left or right
	protected final int LEFT = -1;
	protected final int RIGHT = 1;
	protected int moveX;
	protected static int entitySpeed = 0;
	protected Random r;
	protected int randomX;
	protected ImageIcon img;
	protected Image scaledImg;
	protected Entity( int y, int direction) {
		r = new Random();
		randomX = (r.nextInt(10)) * 140;
		this.x = randomX;
		this.y = y;
		
		this.direction = direction;
	}
	public void setDirection (int direction){
		r = new Random();
		int  n = r.nextInt(1) + 0;
		if( n == 0) 
			direction = LEFT;
		else
			direction = RIGHT;
	}
	public static void setEntitySpeed(int s){ entitySpeed = s; }
	
	public int getEntitySpeed(){
		/*moveX = randomX;
		moveX =+ entitySpeed;
		
        if (moveX <= 0 || moveX >= WIDTH)
        	entitySpeed *= -1;*/
		return entitySpeed;
	}
	
	
	
	public void drawEntity(Graphics page){
		page.drawImage(img.getImage(), 700, y, 300, HEIGHT, null);
		
	}
	//Accessors
	public int getHEIGHT(){ return HEIGHT; }
	
	public int getSPEED(){ return SPEED; }
	public int getX(){ return x; }
	public int getY(){ return y; }
	public int getDirection(){ return direction; }
	//------------
	// Mutators
	//------------
	public void setX(int x){ this.x = x; }
	public void setY(int y){ this.y = y; }//
	
}

	

