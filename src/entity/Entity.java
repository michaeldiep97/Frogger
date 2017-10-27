package entity;
//**************************************
// -Designed by Carla
//**************************************
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
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
	protected Rectangle hitBox;//added for collision detection
	protected Entity( int y, int direction) {
		r = new Random();
		randomX = (r.nextInt(10)) * 140;
		this.x = randomX;
		this.y = y;
		
		this.direction = direction;
	}
	
	public void drawEntity(Graphics page){
		page.drawImage(img.getImage(), 700, y, 300, HEIGHT, null);
		
	}
	//----------------
	// Accessors
	//----------------
	public int getHEIGHT(){ return HEIGHT; }
	public int getSPEED(){ return SPEED; }
	public int getX(){ return x; }
	public int getY(){ return y; }
	public int getDirection(){ return direction; }
	public Rectangle getHitBox(){ return hitBox; }
	public static int getEntitySpeed(){
		return entitySpeed;
	}
	//------------
	// Mutators
	//------------
	public void setX(int x){ 
		this.x = x; 
		hitBox.setLocation(new Point(x, y));
	}
	public void setY(int y){ 
		this.y = y; 
		hitBox.setLocation(new Point(x, y));
	}
	public static void setEntitySpeed(int s){ entitySpeed = s; }
	
}

	

