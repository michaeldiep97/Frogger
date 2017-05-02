package frogger.panel;

import java.awt.*;
import javax.swing.*;

public class Frog {
	
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
    	Frog.x = x;
        Frog.y = y;
        
        maxY = GamePanel.LANE_HEIGHT;
        
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
    public static void setFrogX(int xnew) { x = xnew; }
    public static void setFrogY(int ynew) { y = ynew; }
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
    public static void resetFrog() {
    	Frog f = new Frog ((GamePanel.WIDTH / 2) - 50, (GamePanel.TOTAL_HEIGHT - (GamePanel.LANE_HEIGHT * 4)) + 20);
    }
    
} // end of Frog class
