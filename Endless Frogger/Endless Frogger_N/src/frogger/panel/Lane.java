package frogger.panel;
//**********************************************//
//Abstract class representing a single lane     //
//**********************************************//
import java.awt.Image;
import javax.swing.ImageIcon;

public class Lane {
	protected final int WIDTH = 1400, HEIGHT = 140, SPEED = 5;
	protected int x, y;
	protected ImageIcon img;
	protected Image scaledImg;
	
	protected Lane(int x, int y){
		this.x = x;
		this.y = y;
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
}
