package frogger.panel;

import java.awt.Image;
import javax.swing.ImageIcon;

public class River extends Lane {

	public River(int x, int y) {
		super(x, y);
		img = new ImageIcon("Road_mid.png");
		scaledImg = img.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST);//Scales image
		img = new ImageIcon(scaledImg);
	}

}
