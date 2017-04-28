package frogger.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics; //for graphical display
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import javax.swing.JComponent; //for paintComponent
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel; //for extension

public class GamePanel extends JPanel{
	private final int LANE_HEIGHT = 140, WIDTH = 1400, TOTAL_HEIGHT = (LANE_HEIGHT * 7);//14 pixels * 7 lanes
	private final int DELAY = 20;
	
	private Lane lanes[];
	private JLabel timeLabel, distanceLabel, coinsLabel;
	private JButton resetButton;
	private Timer timer;
	
	private int time, distance, coinCount;
	
	
	public GamePanel() {
		// Initialization
		timer = new Timer(DELAY, new TimerListener());
		
		lanes = new Lane[7];
		lanes[0] = new Grass(0, TOTAL_HEIGHT - LANE_HEIGHT);
		lanes[1] = new Grass(0, TOTAL_HEIGHT - (LANE_HEIGHT * 2));
		lanes[2] = new Grass(0, TOTAL_HEIGHT - (LANE_HEIGHT * 3));
		lanes[3] = new River(0, TOTAL_HEIGHT - (LANE_HEIGHT * 4));
		lanes[4] = new Grass(0, TOTAL_HEIGHT - (LANE_HEIGHT * 5));		
		lanes[5] = new Road(0, TOTAL_HEIGHT - (LANE_HEIGHT * 6), false);
		lanes[6] = new Road(0, TOTAL_HEIGHT - (LANE_HEIGHT * 7), true);
		
		time = 0;
		distance = 0;
		coinCount = 0;
		
		timeLabel = new JLabel("Time: ");
		distanceLabel = new JLabel("Distance: ");
		coinsLabel = new JLabel("Coins: ");
		resetButton = new JButton("Reset");
		
		//instantiate, initialize, and attach action listener to buttons/panel elements
		ButtonListener bListener = new ButtonListener();
		resetButton.addActionListener(bListener);
		
		//add all panel elements to the panel
		this.add(timeLabel);
		this.add(distanceLabel);
		this.add(coinsLabel);
		this.add(resetButton);
		
		
		
		//set basic panel characteristics
		setPreferredSize(new Dimension(WIDTH, TOTAL_HEIGHT));
		setBackground(Color.black);
		timer.start();
	}
	
	public void paintComponent(Graphics page){
		super.paintComponent(page);
		for (int index = 0; index < lanes.length; index++)
			lanes[index].img.paintIcon(this, page, lanes[index].getX(), lanes[index].getY());
		timeLabel.setFont(new Font(null , Font.PLAIN, 60 ));
		timeLabel.setLocation(0, 0);
		timeLabel.setSize(new Dimension(WIDTH / 5, LANE_HEIGHT));
		distanceLabel.setFont(new Font(null , Font.PLAIN, 60 ));
		distanceLabel.setLocation(WIDTH / 5, 0);
		distanceLabel.setSize(new Dimension(WIDTH / 4, LANE_HEIGHT));
		coinsLabel.setFont(new Font(null , Font.PLAIN, 60 ));
		coinsLabel.setLocation(WIDTH / 2, 0);
		coinsLabel.setSize(new Dimension(WIDTH / 4, LANE_HEIGHT));
		resetButton.setFont(new Font(null , Font.PLAIN, 60 ));
		resetButton.setLocation(WIDTH / 4 * 3, 0);
		resetButton.setSize(new Dimension(WIDTH / 4, LANE_HEIGHT));
	}
	
	//-------------------------------------------------------------------
	// Updates position of each lane whenever timer fires an ActionEvent
	// and propagates lane deletion/creation
	//-------------------------------------------------------------------
	private class TimerListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent event) {
			for (int index = 0; index < lanes.length; index++)
				lanes[index].setY(lanes[index].getY() + lanes[index].getSPEED());
			if (lanes[0].getY() >= TOTAL_HEIGHT){
				for (int index = 0; index < lanes.length - 1; index++){
					lanes[index] = lanes[index + 1];
				}
				lanes[6] = new Grass(0, TOTAL_HEIGHT - LANE_HEIGHT * 7);
			}
		repaint();
		}
	}
	private class ButtonListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent event){
			Road.resetRoadCount();
			
			lanes[0] = new Grass(0, TOTAL_HEIGHT - LANE_HEIGHT);
			lanes[1] = new Grass(0, TOTAL_HEIGHT - (LANE_HEIGHT * 2));
			lanes[2] = new Grass(0, TOTAL_HEIGHT - (LANE_HEIGHT * 3));
			lanes[3] = new River(0, TOTAL_HEIGHT - (LANE_HEIGHT * 4));
			lanes[4] = new Grass(0, TOTAL_HEIGHT - (LANE_HEIGHT * 5));		
			lanes[5] = new Road(0, TOTAL_HEIGHT - (LANE_HEIGHT * 6), false);
			lanes[6] = new Road(0, TOTAL_HEIGHT - (LANE_HEIGHT * 7), true);
			
			time = 0;
			distance = 0;
		}
	}
}
