package frogger.panel;
//****************************
//-Designed by Michael Diep & Carla Cardenas
//****************************
import java.awt.Image;
import java.util.Random;
import java.awt.Graphics;
import javax.swing.ImageIcon;

import entity.*;

public class Road extends Lane {
	protected Entity entity[];
	private int direction;
	private static int roadCount = 1;
	private Boolean isLastRoad;
	//private Car car;
	//private Truck truck;
	public Road(int x, int y, boolean last) {
		super(x, y);
		//switch between car and truck
		Random r = new Random();
		direction = r.nextInt(2);
		if (direction == 0)
			direction = -1;
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
		
		entity = new Entity[]{
				 new Car( y + 10, direction)
		};
		roadCount++;
	}
	public void drawLane(Graphics page){
		
		page.drawImage(img.getImage(), x, y, WIDTH, HEIGHT, null);
		
		for (int index = 0; index < entity.length; index++){
			Entity temp = entity[index];
			if (temp.getX() < -400)
				temp.setX(WIDTH);
			else if (temp.getX() > WIDTH)
				temp.setX(-400);
			temp.setX(temp.getX() + (temp.getEntitySpeed() * temp.getDirection()));
			temp.setY(y + 10);
			temp.drawEntity(page);
		}
		if (disableCoin == false)
			cn.setY(y + 20);
			cn.drawCoin(page);
	}

	public static int getRoadCount(){ return roadCount; }
	
	public static void resetRoadCount(){ roadCount = 1; }

}