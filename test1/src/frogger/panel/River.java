package frogger.panel;
import java.awt.Graphics;
//****************************
//-Designed by Michael Diep
//****************************
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import entity.*;

public class River extends Lane {
	protected Entity entity[];
	private int direction;
	public River(int x, int y) {
		super(x, y);
		//used to rotate between log and Turtle
		Random r = new Random();
		direction = r.nextInt(2);
		if (direction == 0)
			direction = -1;
		img = new ImageIcon("River.png");
		scaledImg = img.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST);//Scales image
		img = new ImageIcon(scaledImg);
		entity = new Entity[]{
				 new Log( y + 10, direction),
				 new Turtle( y + 10, direction),
				 new Log( y + 10, direction),
				 new Turtle( y + 10, direction),
		};
				 
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

}
