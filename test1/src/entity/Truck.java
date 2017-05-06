package entity;
//**************************************
//-Designed by Carla
//**************************************
import java.awt.Image;
import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Truck extends Entity {
	protected static ImageIcon truck;
	private final int WIDTH = 300;

	public Truck( int y, int direction) {
		super( y, direction);
		if( direction == LEFT){
			truck = new ImageIcon("Truck-left.png");
			scaledImg = truck.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST);//Scales image
			truck = new ImageIcon(scaledImg);
		}
		else {
			truck = new ImageIcon("Truck-right.png");
			scaledImg = truck.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST);//Scales image
			truck = new ImageIcon(scaledImg);
		}
	}
	public void drawEntity(Graphics page){
		page.drawImage(truck.getImage(), x, y, WIDTH, HEIGHT, null);
	}
}
