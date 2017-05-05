package entity;
import java.awt.Image;
import java.awt.Graphics;
import javax.swing.ImageIcon;

public final class Log extends Entity{
	protected static ImageIcon log;
	private final int WIDTH = 300;
	
	public Log( int y, int direction) {
		super( y, direction);
		
		log = new ImageIcon("Frogger-log.png");
		scaledImg = log.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST);//Scales image
		log = new ImageIcon(scaledImg);
	}

	public void drawEntity(Graphics page){
		page.drawImage(log.getImage(), x, (y+SPEED), WIDTH, HEIGHT, null);
	}

}