package frogger.panel;
//********************************************************
// Coin class intended for use with Lane class. Each
// Lane that is instantiated passes a y coordinate
// to instantiate its coin. Coin's x is selected at
// random.
//********************************************************
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;

public class Coin {
	private final int WIDTH = 100, HEIGHT = 100, SPEED = 5;
	private static int coinCount = 0;
	private int randomX, y;
	private ImageIcon img;
	private Image scaledImg;
	private Random r;
	
	public Coin(int y) {
		r = new Random();
		randomX = (r.nextInt(10)) * 140 + 20;
		this.y = y;
		img = new ImageIcon("coin.png");
		scaledImg = img.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST);//Scales image
		img = new ImageIcon(scaledImg);
		coinCount++;
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
	
	//------------
	// Mutators
	//------------
	public void setX(int x){ randomX = x; }
	public void setY(int y){ this.y = y; }
	public static void setCoinCount(int c){ coinCount = c; }
	
	//------------
	// Actions
	//------------
	public void drawCoin(Graphics page){
		page.drawImage(img.getImage(), randomX, y, WIDTH, HEIGHT, null);
	}
}
