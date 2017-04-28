package frogger.panel;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Road extends Lane {
	private static int roadCount = 1;
	private Boolean isLastRoad;
	public Road(int x, int y, boolean last) {
		super(x, y);
		isLastRoad = last;
		if (roadCount == 1)
			img = new ImageIcon("Road_bottom.png");
		else if (isLastRoad == true)
			img = new ImageIcon("Road_top.png");
			else
				img = new ImageIcon("Road_mid.png");
		scaledImg = img.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST);//Scales image
		img = new ImageIcon(scaledImg);
		
		roadCount++;
	}
	
	public static void resetRoadCount(){ roadCount = 1; }

}
