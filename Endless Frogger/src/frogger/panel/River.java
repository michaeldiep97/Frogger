package frogger.panel;

import java.awt.Image;
import java.awt.Graphics;
import javax.swing.ImageIcon;

public class River extends Lane {

	public River(int x, int y) {
		super(x, y);
		img = new ImageIcon("River.png");
		scaledImg = img.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST);//Scales image
		img = new ImageIcon(scaledImg);
	}

}
