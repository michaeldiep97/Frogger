package lanes;
//****************************
// -Designed by Michael Diep
//****************************
import java.awt.Image;
import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Grass extends Lane {

	public Grass(int x, int y) {
		super(x, y);
		img = new ImageIcon("grass.png");
		scaledImg = img.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST);//Scales image
		img = new ImageIcon(scaledImg);
	}

}
