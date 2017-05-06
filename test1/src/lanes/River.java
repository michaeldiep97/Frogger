package lanes;
//******************************************
// -Designed by Michael Diep -Entity implementation by Carla Cardenas
//******************************************
import java.awt.Graphics;
//****************************
//-Designed by Michael Diep
//****************************
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import entity.*;

public class River extends Lane {
	protected Log log;
	private static int direction = -1; //logs alternate between -1 (left) and 1 (right)
	public River(int x, int y) {
		super(x, y);
		isRiver = true;
		img = new ImageIcon("River.png");
		scaledImg = img.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST);//Scales image
		img = new ImageIcon(scaledImg);
		//used to rotate between directions
		if (direction == 1)
			direction = -1;
		else
			direction = 1;
		
		log = new Log( y + 10, direction);
					 
	}
	public void drawLane(Graphics page){
		// draws lane
		page.drawImage(img.getImage(), x, y, WIDTH, HEIGHT, null);
		// draws entities & updates positions
		if (log.getX() < -400)
			log.setX(WIDTH);
		else if (log.getX() > WIDTH)
			log.setX(-400);
		log.setX(log.getX() + (log.getEntitySpeed() * log.getDirection()));
		log.setY(y + 10);
		log.drawEntity(page);
			
		if (disableCoin == false)
			cn.setY(y + 20);
			cn.drawCoin(page);
	}
	public Log getLog(){ return log; }

}
