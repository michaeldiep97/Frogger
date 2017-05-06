package lanes;
//*******************************************************************
// -Designed by Michael Diep -Entity implementation by Carla Cardenas
//*******************************************************************
import java.awt.Image;
import java.util.Random;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import entity.*;

public class Road extends Lane {
	protected Car car;
	private static int direction = 1;
	private static int roadCount = 1;
	private Boolean isLastRoad;
	public Road(int x, int y, boolean last) {
		super(x, y);
		isRoad = true;
		if (direction == 1)
			direction = -1;
		else
			direction = 1;
		isLastRoad = last;
		//positions road medians correctly
		if (roadCount == 1)
			img = new ImageIcon("Road_bottom.png");
		else if (isLastRoad == true)
			img = new ImageIcon("Road_top.png");
			else
				img = new ImageIcon("Road_mid.png");
		scaledImg = img.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST);//Scales image
		img = new ImageIcon(scaledImg);
		
		car = new Car( y + 10, direction);
		
		roadCount++;
	}
	public void drawLane(Graphics page){
		//draw lane
		page.drawImage(img.getImage(), x, y, WIDTH, HEIGHT, null);
		
		if (car.getX() < -400)
			car.setX(WIDTH);
		else if (car.getX() > WIDTH)
			car.setX(-400);
		car.setX(car.getX() + (car.getEntitySpeed() * car.getDirection()));
		car.setY(y + 10);
		car.drawEntity(page);
		
		if (disableCoin == false)
			cn.setY(y + 20);
			cn.drawCoin(page);
	}

	public static int getRoadCount(){ return roadCount; }
	public Car getCar(){ return car; }
	
	public static void resetRoadCount(){ roadCount = 1; }

}