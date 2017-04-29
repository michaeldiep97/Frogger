package frogger.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics; //for graphical display
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

//import javax.swing.JComponent; //for paintComponent
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel; //for extension

public class GamePanel extends JPanel{
	private final int LANE_HEIGHT = 140, WIDTH = 1400, TOTAL_HEIGHT = (LANE_HEIGHT * 7);//14 pixels * 7 lanes
	private final int DELAY = 20;
	
	private Lane lanes[];
	
	//-----------------------------
	// Components
	//-----------------------------
	private JLabel timeLabel, distanceLabel, coinsLabel;
	private JButton resetButton;
	
	//-----------------------------
	// Timer and TimerListener
	//-----------------------------
	private TimerListener tl;
	private Timer timer;
	
	//-----------------------------
	// Stats
	//-----------------------------
	private int time, distance, coinCount;
	
	//---------------------------
	// Constructor
	//---------------------------
	public GamePanel() {
		//------------------------------------------
		// Initialization
		//------------------------------------------
		tl = new TimerListener();
		timer = new Timer(DELAY, tl);
		
		lanes = new Lane[]{
				new Grass(0, TOTAL_HEIGHT - LANE_HEIGHT),
				new Grass(0, TOTAL_HEIGHT - (LANE_HEIGHT * 2)),
				new River(0, TOTAL_HEIGHT - (LANE_HEIGHT * 3)),
				new Grass(0, TOTAL_HEIGHT - (LANE_HEIGHT * 4)),
				new Road(0, TOTAL_HEIGHT - (LANE_HEIGHT * 5), false),
				new Road(0, TOTAL_HEIGHT - (LANE_HEIGHT * 6), true),
				new Grass(0, 0)	
		};
		Road.resetRoadCount();//this must be reset to ensure correct road generation whenever timer fires an ActionEvent
		
		time = 0;
		distance = 0;
		coinCount = 0;
		
		timeLabel = new JLabel("Time: ");
		distanceLabel = new JLabel("Distance: ");
		coinsLabel = new JLabel("Coins: ");
		resetButton = new JButton("Reset");
		
		//-----------------------------------------------------------------------------
		// instantiate, initialize, and attach action listener to buttons/panel elements
		//-----------------------------------------------------------------------------
		ButtonListener bListener = new ButtonListener();
		resetButton.addActionListener(bListener);
		
		//--------------------------------------------
		// add all panel elements to the panel
		//--------------------------------------------
		this.add(timeLabel);
		this.add(distanceLabel);
		this.add(coinsLabel);
		this.add(resetButton);
		
		//--------------------------------------------
		// set basic panel characteristics
		//--------------------------------------------
		setPreferredSize(new Dimension(WIDTH, TOTAL_HEIGHT));
		setBackground(Color.black);
		timer.start();
	}
	//---------------------------------------------------
	// Paint component
	//---------------------------------------------------
	public void paintComponent(Graphics page){
		
		//------------------------------------------------------
		// Drawing lanes
		//------------------------------------------------------
		super.paintComponent(page);
		for (int index = 0; index < lanes.length; index++)
			lanes[index].img.paintIcon(this, page, lanes[index].getX(), lanes[index].getY());
		//---------------------------------------------------------------
		// Drawing labels
		//---------------------------------------------------------------
		page.setColor(Color.white);
		page.fillRect(0, 0, WIDTH, LANE_HEIGHT);
		
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
		private final int MAX_CONSECUTIVE_LANES = 4;
		private Random r = new Random();
		private Boolean nextLaneType = false; //determines if the next lane is grass(true) or river/road(false). alternates between grass and river/road
		private int consecutiveLanes = r.nextInt(MAX_CONSECUTIVE_LANES) + 1;
		private int generationCounter = 0;//increments until it equals the number of consecutive lanes generated
		private int riverOrRoad = 0;
		
		@Override
		public void actionPerformed(ActionEvent event) {
			
			//-------------
			// position update
			//-------------
			for (int index = 0; index < lanes.length; index++)
				lanes[index].setY(lanes[index].getY() + lanes[index].getSPEED());
			
			//-------------------------------------------------------------------------------------------------------------------------
			// shifts lanes down in lanes[], this deletes the Lane stored in lanes[0] by assigning the Lane stored in lanes[1] to it.
			// this occurs for each lane up until lanes[5]. lanes[6] instantiates a new Lane of  subclass road / (grass/river).
			//-------------------------------------------------------------------------------------------------------------------------
			if (lanes[0].getY() >= TOTAL_HEIGHT){
				for (int index = 0; index < lanes.length - 1; index++){
					lanes[index] = lanes[index + 1];
				}
				
				//-----------------
				// starts as grass, grass always comes as single lanes, 
				// which is why it doesn't use generationCounter
				//-----------------
				if (nextLaneType == true){
					lanes[6] = new Grass(0, 0);
					
					//----------------------------
					// switch to river/road
					//----------------------------
					nextLaneType = false;
					
					//-----------------------------
					// Deciding on river/road
					//-----------------------------
					riverOrRoad = r.nextInt(2);
					
					//----------------------------------------------------
					//determines number of the next consecutive lane type
					//----------------------------------------------------
					consecutiveLanes = r.nextInt(MAX_CONSECUTIVE_LANES - 1) + 2;
				}
				else{ //nextLaneType == false
					if (riverOrRoad == 0){
						lanes[6] = new River(0, 0);
						generationCounter++;
						if (generationCounter == consecutiveLanes){
							nextLaneType = true;//switch back to grass
							generationCounter = 0;
						}
					}
					else{ //riverOrRoad == 1
						if (generationCounter < consecutiveLanes)
							lanes[6] = new Road(0, 0, false);
						generationCounter++;
						if (generationCounter == consecutiveLanes){
							
							//----------------------------------------------------------------------------------
							//the true condition below indicates this is the last road of the consecutive road set, 
							//telling the class to construct with the appropriate road image.
							//----------------------------------------------------------------------------------
							lanes[6] = new Road(0, 0, true);
							Road.resetRoadCount();
							nextLaneType = true;//switch back to grass
							generationCounter = 0;
						}
					}
				}
			}
		repaint();
		}
		
		//------------------------------------------------------------
		// mutators (for use by reset button)
		//------------------------------------------------------------
		public void resetGenerationCounter(){ generationCounter = 0; }
		public void resetNextLaneType(){ nextLaneType = false; }
	}
	
	//-------------------------
	// Button Listener
	//-------------------------
	private class ButtonListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent event){
			
			//---------------------------------------------
			// Restart action
			//---------------------------------------------
			if (event.getSource() == resetButton){
				Road.resetRoadCount();
				lanes = new Lane[]{
						new Grass(0, TOTAL_HEIGHT - LANE_HEIGHT),
						new Grass(0, TOTAL_HEIGHT - (LANE_HEIGHT * 2)),
						new River(0, TOTAL_HEIGHT - (LANE_HEIGHT * 3)),
						new Grass(0, TOTAL_HEIGHT - (LANE_HEIGHT * 4)),
						new Road(0, TOTAL_HEIGHT - (LANE_HEIGHT * 5), false),
						new Road(0, TOTAL_HEIGHT - (LANE_HEIGHT * 6), true),
						new Grass(0, 0)	
				};
				Road.resetRoadCount();
				tl.resetGenerationCounter();
				tl.resetNextLaneType();
				
				time = 0;
				distance = 0;
			}
		}
	}
}
