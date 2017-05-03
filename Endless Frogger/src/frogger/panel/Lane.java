package frogger.panel;
//**********************************************
// Abstract class representing a single lane.
// Children classes will be instantiated in 
// GamePanel.
//**********************************************
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics;

public class Lane {
	protected final int WIDTH = 1400, HEIGHT = 140, SPEED = 5;
	protected int x, y;
	protected ImageIcon img;
	protected Image scaledImg;
	private Coin cn;
	
	protected Lane(int x, int y){
		this.x = x;
		this.y = y;
		cn = new Coin(y + 20);
	}
	
	//-------------
	// Accessors
	//-------------
	public int getHEIGHT(){ return HEIGHT; }
	public int getWIDTH(){ return WIDTH; }
	public int getSPEED(){ return SPEED; }
	public int getX(){ return x; }
	public int getY(){ return y; }
	
	//------------
	// Mutators
	//------------
	public void setX(int x){ this.x = x; }
	public void setY(int y){ this.y = y; }
	
	//------------
	// Actions
	//------------
	public void drawLane(Graphics page){
		// Lane
		page.drawImage(img.getImage(), x, y, WIDTH, HEIGHT, null);
		// Coin
		cn.setY(y + 20);
		cn.drawCoin(page);
	}
}
