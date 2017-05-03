package frogger.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics; //for graphical display
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

//import javax.swing.JComponent; //for paintComponent
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel; //for extension

public class GamePanel extends JPanel{
	private static final int LANE_HEIGHT = 140, WIDTH = 1400, TOTAL_HEIGHT = (LANE_HEIGHT * 7);//14 pixels * 7 lanes
	private final int DELAY = 20;
	
	private Lane lanes[];
	private Frog f;
	private Graphics page;
	
	//-----------------------------
	// Components
	//-----------------------------
	private JLabel timeLabel, distanceLabel, coinsLabel;
	private JButton startButton, resetButton;
	
	//-----------------------------
	// Timer and TimerListener
	//-----------------------------
	private TimerListener tl;
	private Timer timer;
	
	//-----------------------------
	// Stats
	//-----------------------------
	private int time, distance, coinCount;
	
	private int t;  // used to update timer - Nicole
	private boolean listen; // used for key listener - Nicole
	
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
		
		f = new Frog((WIDTH / 2) + 20, (TOTAL_HEIGHT - (LANE_HEIGHT * 4)) + 20);
		
		time = 0;
		distance = 0;
		coinCount = 0;
		
		listen = false; // keys won't do anything while listen = false - Nicole
		
		timeLabel = new JLabel("Time: ");
		distanceLabel = new JLabel("Distance: ");
		coinsLabel = new JLabel("Coins: ");
		startButton = new JButton("Start");
		resetButton = new JButton("Reset");
		
		//-----------------------------------------------------------------------------
		// instantiate, initializes, and attach action listener to buttons/panel elements
		//-----------------------------------------------------------------------------
		ButtonListener bListener = new ButtonListener();
		startButton.addActionListener(bListener);
		startButton.setFocusable(false);
		resetButton.addActionListener(bListener);
		resetButton.setFocusable(false); // so focus stays on keyboard when reset button is pressed - Nicole
		
		//--------------------------------------------
		// add all panel elements to the panel
		//--------------------------------------------
		this.add(timeLabel);
		this.add(distanceLabel);
		this.add(coinsLabel);
		this.add(startButton);
		this.add(resetButton);
		
		//--------------------------------
		// KeyListener - Nicole
		//--------------------------------
		setFocusable(true); // focus is still true even when keys don't do anything
		addKeyListener(new DirectionListener()); // registers the key listener
		
		
		//--------------------------------------------
		// set basic panel characteristics
		//--------------------------------------------
		setPreferredSize(new Dimension(WIDTH, TOTAL_HEIGHT));
		setBackground(Color.black);
		//Timer starts with start button
	}
	
	//-------------------
    // Accessors
    //------------------
    public static int getWIDTH(){ return WIDTH; }
    public static int getLANE_HEIGHT(){ return LANE_HEIGHT; }
    public static int getTOTAL_HEIGHT(){ return TOTAL_HEIGHT; }
	
	//---------------------------------------------------
	// Paint component
	//---------------------------------------------------
	public void paintComponent(Graphics page){
		
		//------------------------------------------------------
		// Drawing lanes
		//------------------------------------------------------
		super.paintComponent(page);
		for (int index = 0; index < lanes.length; index++){
			//lanes[index].img.paintIcon(this, page, lanes[index].getX(), lanes[index].getY()); //redundant
			
			// Draw lane and its coins
			lanes[index].drawLane(page);
		}
		
		//------------------------------------------------------
		// Drawing Frog - Nicole
		//------------------------------------------------------
		Frog.draw().paintIcon(this, page, Frog.getFrogX(), Frog.getFrogY());
				
		//---------------------------------------------------------------
		// Drawing labels
		//---------------------------------------------------------------
		page.setColor(Color.white);
		page.fillRect(0, 0, WIDTH, LANE_HEIGHT);
		
		timeLabel.setFont(new Font(null , Font.PLAIN, 40 ));
		timeLabel.setLocation(0, 0);
		timeLabel.setSize(new Dimension(WIDTH / 5, LANE_HEIGHT));
		
		distanceLabel.setFont(new Font(null , Font.PLAIN, 40 ));
		distanceLabel.setLocation(WIDTH / 5, 0);
		distanceLabel.setSize(new Dimension(WIDTH / 4, LANE_HEIGHT));
		
		coinsLabel.setFont(new Font(null , Font.PLAIN, 40 ));
		//coinsLabel.setText("Coins: " + Coin.getCoinCount());
		coinsLabel.setLocation(WIDTH / 2, 0);
		coinsLabel.setSize(new Dimension(WIDTH / 4, LANE_HEIGHT));
		
		startButton.setFont(new Font(null , Font.PLAIN, 50 ));
		startButton.setLocation((int)(WIDTH * (3.0 / 8)), TOTAL_HEIGHT / 2);
		startButton.setSize(new Dimension(WIDTH / 4, LANE_HEIGHT));
		
		resetButton.setFont(new Font(null , Font.PLAIN, 50 ));
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
			
			//-----------------
			// position update
			//-----------------
			for (int index = 0; index < lanes.length; index++)
				lanes[index].setY(lanes[index].getY() + lanes[index].getSPEED());
			Frog.setFrogY(Frog.getFrogY() +lanes[0].getSPEED());
			
			/*//------------------------------------
			//  Checks if frogger is out of panel
			//------------------------------------
			if (Frog.getFrogX() < 0 || Frog.getFrogX() > WIDTH - 120)
				timer.stop();
				startButton.setText("Game Over");
				//startButton.setVisible(true);
				 */
			
			//---------------------
			// time update - Nicole
			//---------------------
			t += 20; // delay is 20
			if (t%1000 == 0) { // for every 1 second that has passed
				time++;
				timeLabel.setText(" Time: " + time + "s");
			}
			
			//-------------------------------------------------------------------------------------------------------------------------
			// shifts lanes down in lanes[], this deletes the Lane stored in lanes[0] by assigning the Lane stored in lanes[1] to it.
			// this occurs for each lane up until lanes[5]. lanes[6] instantiates a new Lane of  subclass road / (grass/river).
			//-------------------------------------------------------------------------------------------------------------------------
			if (lanes[0].getY() >= TOTAL_HEIGHT){
				for (int index = 0; index < lanes.length - 1; index++){
					lanes[index] = lanes[index + 1];
				}
				
				//-----------------
				// grass always comes as single lanes, 
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
				
				//----------------------------------
				// First generated lane is a river/road
				//----------------------------------
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
			// Start action
			//---------------------------------------------
			if (event.getSource() == startButton){
				timer.start();
				startButton.setVisible(false);
				listen = true; // arrow keys now make the character move - Nicole
			}
			
			//---------------------------------------------
			// Restart action
			//---------------------------------------------
			if (event.getSource() == resetButton){
				//startButton.setVisible(false);
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
				f.resetFrog();
				tl.resetGenerationCounter();
				tl.resetNextLaneType();
				
				time = 0;
				distance = 0;
			}
		}
	}
	//----------------------------
    // Keyboard Listener - Nicole
    //----------------------------
    private class DirectionListener implements KeyListener
    {
       //--------------------------------------------------------------
       //  Responds to the user pressing arrow keys by adjusting the
       //  image and image location accordingly.
       //--------------------------------------------------------------
       public void keyPressed(KeyEvent event) {
    	   if (listen == true)  { // listen is assigned to true when startButton is pressed
    		   switch (event.getKeyCode())
    		   {
    		   	// Up arrow
    		   	case KeyEvent.VK_UP:
    		   		Frog.setCurrentImage(Frog.getImage(Frog.u));
    		   		if (Frog.y > Frog.maxY) // Keeps frog from going off the screen upward
    		   			Frog.setFrogY(Frog.y += Frog.UP);
    		   		distance++; // Increments and updates the distance traveled
    		   		distanceLabel.setText("Distance: " + distance + "m");
    		   		break;
                	
    		   	// Down arrow
    		   	case KeyEvent.VK_DOWN:
    		   		Frog.setCurrentImage(Frog.getImage(Frog.d));
    		   		Frog.setFrogY(Frog.y += Frog.DOWN);
    		   		distance--; // Decrements and updates the distance traveled
    		   		distanceLabel.setText("Distance: " + distance + "m");
                	break;
              
                // Left arrow
    		   	case KeyEvent.VK_LEFT:
          			Frog.setCurrentImage(Frog.getImage(Frog.l));
          			Frog.setFrogX(Frog.x += Frog.LEFT);
          			break;
          		
          		// Right arrow
          		case KeyEvent.VK_RIGHT:
          			Frog.setCurrentImage(Frog.getImage(Frog.r));
          			Frog.setFrogX(Frog.x += Frog.RIGHT);
          			break;
    		   }
    	   }
        }
       
       //--------------------------------------------------------------
       //  Provide empty definitions for unused event methods.
       //--------------------------------------------------------------
       public void keyTyped(KeyEvent event) {}
       public void keyReleased(KeyEvent event) {}
    }
}
