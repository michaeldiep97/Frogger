package entity;
//**************************************
//-Designed by Carla
//**************************************
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Graphics;
import javax.swing.ImageIcon;

public final class Log extends Entity{
	private final int WIDTH = 300;
	
	public Log( int y, int direction) {
		super( y, direction);
		hitBox = new Rectangle(x, y, WIDTH, HEIGHT);
		
		img = new ImageIcon("Frogger-log.png");
		scaledImg = img.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST);//Scales image
		img = new ImageIcon(scaledImg);
	}

	public void drawEntity(Graphics page){
		page.drawImage(img.getImage(), x, (y+SPEED), WIDTH, HEIGHT, null);
	}

}