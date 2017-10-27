package entity;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Car extends Entity{
	protected ImageIcon car;
	private final int WIDTH = 190;
	public Car( int y, int direction) {
		super( y, direction);
		hitBox = new Rectangle(x, y, WIDTH, HEIGHT);
		if( direction == RIGHT){
			car = new ImageIcon("car-right-new.png");
			scaledImg = car.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST);//Scales image
			car = new ImageIcon(scaledImg);
		}
		else if(direction == LEFT){
			car = new ImageIcon("car-left-new.png");
			scaledImg = car.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST);//Scales image
			car = new ImageIcon(scaledImg);
		}
	}
	public void drawEntity(Graphics page){
		page.drawImage(car.getImage(), x, y, WIDTH, HEIGHT, null);
	}
}
	