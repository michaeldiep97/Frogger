package lanes;
//**********************************************
// Abstract class representing a single lane.
// Children classes will be instantiated in 
// GamePanel.
//  -Designed by Michael Diep
//**********************************************
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import java.awt.Graphics;

public class Lane {
	protected final int WIDTH = 1400, HEIGHT = 140, SPEED = 2;
	protected int x, y;
	protected Rectangle hitBox;
	protected ImageIcon img;
	protected Image scaledImg;
	protected Coin cn;
	protected Boolean disableCoin;
	protected Boolean isRiver = false;
	protected Boolean isRoad = false;
	
	protected Lane(int x, int y){
		hitBox = new Rectangle(x, y, WIDTH, HEIGHT);
		disableCoin = false;
		this.x = x;
		this.y = y;
		cn = new Coin(y + 20);
	}
	
	//-------------
	// Accessors
	//-------------
	public Boolean isRiver(){ return isRiver; }
	public Boolean isRoad(){ return isRoad; }
	public int getHEIGHT(){ return HEIGHT; }
	public int getWIDTH(){ return WIDTH; }
	public int getSPEED(){ return SPEED; }
	public int getX(){ return x; }
	public int getY(){ return y; }
	public Coin getCoin(){ return cn; }
	public Rectangle getHitBox(){ return hitBox; }
	
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
	public void disableCoin(Boolean o){ 
		disableCoin = o; 
		cn.setY(HEIGHT * 8); //moves coin off screen until deletion
	}
	
	//------------
	// Actions
	//------------
	public void drawLane(Graphics page){
		// Lane
		page.drawImage(img.getImage(), x, y, WIDTH, HEIGHT, null);
		// Coin
		if (disableCoin == false)
			cn.setY(y + 20);
			cn.drawCoin(page);
	}
}
