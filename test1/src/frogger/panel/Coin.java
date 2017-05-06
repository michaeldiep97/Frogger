package frogger.panel;
//********************************************************
// Coin class intended for use with Lane class. Each
// Lane that is instantiated passes a y coordinate
// to instantiate its coin. Coin's x is selected at
// random.
//  -Designed by Michael Diep
//********************************************************
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.ImageIcon;

public class Coin {
	private final int WIDTH = 100, HEIGHT = 100, SPEED = 5;
	private static int coinCount = 0;
	private int randomX, y;
	private ImageIcon img;
	private Image scaledImg;
	private Random r;
	private Rectangle hitBox; //for collision detection
	
	public Coin(int y) {
		r = new Random();
		randomX = (r.nextInt(10)) * 140 + 20;
		this.y = y;
		img = new ImageIcon("coin.png");
		scaledImg = img.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST);//Scales image
		img = new ImageIcon(scaledImg);
		hitBox = new Rectangle(randomX, y, WIDTH, HEIGHT);
	}
	
	//-------------
	// Accessors
	//-------------
	public static int getCoinCount(){ return coinCount; }
	public int getHEIGHT(){ return HEIGHT; }
	public int getWIDTH(){ return WIDTH; }
	public int getSPEED(){ return SPEED; }
	public int getX(){ return randomX; }
	public int getY(){ return y; }
	public Rectangle getHitBox(){ return hitBox; }
	
	//------------
	// Mutators
	//------------
	public void setX(int x){ randomX = x; }
	public void setY(int y){ 
		this.y = y; 
		hitBox.setLocation(new Point(randomX, y));
	}
	public static void setCoinCount(int c){ coinCount = c; }
	public static void coinIncrement(){ coinCount++; }
	
	//------------
	// Actions
	//------------
	public void drawCoin(Graphics page){
		page.drawImage(img.getImage(), randomX, y, WIDTH, HEIGHT, null);
	}
}
