package frogger.panel;
//*****************************
// Designed by Nicole M.
//*****************************
import java.awt.*;
import javax.swing.*;

public class Frog {
	//----------------
	// Frog Dimensions
	//----------------
	private final static int HEIGHT = 100, WIDTH = 100;
	private static Rectangle hitBox; //added for collision detection
	
	//-------------
	// coordinates
	//-------------
	protected static int x, y, maxY;
	
	//-------------
	// direction
	//-------------
	protected static char u;
	protected static char d;
	protected static char l;
	protected static char r;
	
	//------------
	// Frog images
	//------------
	protected static ImageIcon up, down, left, right;
	protected static ImageIcon currentImage;
	protected static Image scaledImg;
	private final int frogDimension = 100;

	//-----------------------------------
	// static constants for frog movement
	//-----------------------------------
	protected static final int LEFT = -140;
	protected static final int RIGHT = 140;
	protected static final int UP = -140;
	protected static final int DOWN = 140;
   
	//------------
    // Constructor
	//------------
    public Frog (int x, int y) {
    	
		//---------------
		// Initialization
		//---------------
    	//////////////////////////////////////////////
    	hitBox = new Rectangle(x, y, WIDTH, HEIGHT);
    	//////////////////////////////////////////////
    	Frog.x = x;
        Frog.y = y;
        
        maxY = GamePanel.getLANE_HEIGHT();
        
        u = 'u';
        d = 'd';
        l = 'l';
        r = 'r';
        
		//--------------------------------------------
		// Initialize frog images to correct direction
		//--------------------------------------------
        up = new ImageIcon("Frog_up.png");
        scaledImg = up.getImage().getScaledInstance(frogDimension, frogDimension, Image.SCALE_FAST);
		up = new ImageIcon(scaledImg);
		
		down = new ImageIcon("Frog_down.png");
        scaledImg = down.getImage().getScaledInstance(frogDimension, frogDimension, Image.SCALE_FAST);
        down = new ImageIcon(scaledImg);
		
		left = new ImageIcon("Frog_left.png");
        scaledImg = left.getImage().getScaledInstance(frogDimension, frogDimension, Image.SCALE_FAST);
        left = new ImageIcon(scaledImg);
		
		right = new ImageIcon("Frog_right.png");
        scaledImg = right.getImage().getScaledInstance(frogDimension, frogDimension, Image.SCALE_FAST);
        right = new ImageIcon(scaledImg);

        currentImage = up;

    }

	//-------------
	// Accessors
	//-------------
    public static int getFrogX() { return x; }
    public static int getFrogY() { return y; }
    /////////////////////////////////////////////////////
    public static int getHEIGHT(){ return HEIGHT; }
    public static int getWIDTH(){ return WIDTH; }
    public static Rectangle getHitBox(){ return hitBox; }
    /////////////////////////////////////////////////////
    public static ImageIcon getCurrentImage () { return currentImage; }
    public static ImageIcon getImage (char i) { 
    	ImageIcon temp = up;
    	switch (i) {
		case 'u':
			temp = up;
			break;
		case 'd':
			temp = down;
			break;
		case 'l':
			temp = left;
			break;
		case 'r':
			temp = right;
			break;
    	}
		return temp;
    }
    
	//------------
	// Mutators
	//------------
    /////////////////////////////////////////////////////
    public static void setFrogX(int xnew) { 
    	x = xnew; 
    	hitBox.setLocation(xnew, y);
    }
    public static void setFrogY(int ynew) { 
    	y = ynew; 
    	hitBox.setLocation(x, ynew);
    }
    ///////////////////////////////////////////
    public static void setCurrentImage (ImageIcon newImage) { currentImage = newImage; }
    
	//------------
	// Die
	//------------
    public void die () {
    	
    }
	
	//------------
	// Draw
	//------------
    public static ImageIcon draw() {
        return currentImage;
    }
    
	//------------
	// Reset
	//------------
    public void resetFrog() {
    	new Frog((GamePanel.getWIDTH() / 2) +20, (GamePanel.getTOTAL_HEIGHT() - (GamePanel.getLANE_HEIGHT() * 4)) + 20);
    }
    
} // end of Frog class
