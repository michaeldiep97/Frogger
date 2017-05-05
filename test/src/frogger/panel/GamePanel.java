package frogger.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics; //for graphical display
import java.awt.Rectangle;
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
	private final Rectangle screenBoundary;
	
	private Lane lanes[];
	private Frog f;
	private Graphics page;
	
	//-----------------------------
	// Components
	//-----------------------------
	private JLabel timeLabel, distanceLabel, maxDistanceLabel, coinsLabel, gameOver;
	private JButton startButton, resetButton;
	
	//-----------------------------
	// Timer and TimerListener
	//-----------------------------
	private TimerListener tl;
	private Timer timer;
	
	//-----------------------------
	// Stats
	//-----------------------------
	private int time, distance, maxDistance, coinCount;
	private int t;  // used to update timer - Nicole
	
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
		maxDistance = 0;
		coinCount = 0;
		
		timeLabel = new JLabel("Time: ");
		distanceLabel = new JLabel("Distance: ");
		maxDistanceLabel = new JLabel("HIGH SCORE: ");
		coinsLabel = new JLabel("Coins: ");
		gameOver = new JLabel("Game Over");
		startButton = new JButton("Start");
		resetButton = new JButton("Reset");
		
		//-----------------------------------------------------------------------------
		// instantiate, initializes, and attach action listener to buttons/panel elements
		//-----------------------------------------------------------------------------
		ButtonListener bListener = new ButtonListener();
		startButton.addActionListener(bListener);
		startButton.setFocusable(true);
		resetButton.addActionListener(bListener);
		resetButton.setFocusable(true); // so focus stays on keyboard when reset button is pressed - Nicole
		
		//--------------------------------------------
		// add all panel elements to the panel
		//--------------------------------------------
		this.add(timeLabel);
		this.add(distanceLabel);
		this.add(maxDistanceLabel);
		this.add(coinsLabel);
		this.add(gameOver);
		this.add(startButton);
		this.add(resetButton);
		gameOver.setVisible(false);
		
		//--------------------------------
		// KeyListener
		//--------------------------------
		this.setFocusable(false); // keeps focusable false until start is pressed
		addKeyListener(new DirectionListener()); // registers the key listener
		
		//--------------------------------------------
		// set basic panel characteristics
		//--------------------------------------------
		setPreferredSize(new Dimension(WIDTH, TOTAL_HEIGHT));
		screenBoundary = new Rectangle(WIDTH, TOTAL_HEIGHT);
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
		
		timeLabel.setFont(new Font(null , Font.PLAIN, 45 ));
		timeLabel.setLocation(0, 0);
		timeLabel.setSize(new Dimension(WIDTH / 5, LANE_HEIGHT));
		
		distanceLabel.setFont(new Font(null , Font.PLAIN, 35 ));
		distanceLabel.setLocation(WIDTH / 5, 0);
		distanceLabel.setSize(new Dimension(WIDTH / 4, LANE_HEIGHT+50));
		
		maxDistanceLabel.setFont(new Font(null , Font.PLAIN, 35 ));
		maxDistanceLabel.setLocation(WIDTH / 5, 0);						
		maxDistanceLabel.setSize(new Dimension(WIDTH / 4, LANE_HEIGHT/2));
		
		coinsLabel.setFont(new Font(null , Font.PLAIN, 45 ));
		coinsLabel.setText("Coins: " + Coin.getCoinCount());
		coinsLabel.setLocation(WIDTH / 2, 0);
		coinsLabel.setSize(new Dimension(WIDTH / 4, LANE_HEIGHT));
		
		startButton.setFont(new Font(null , Font.PLAIN, 45 ));
		startButton.setLocation((int)(WIDTH * (3.0 / 8)), TOTAL_HEIGHT / 2);
		startButton.setSize(new Dimension(WIDTH / 4, LANE_HEIGHT));
		
		resetButton.setFont(new Font(null , Font.PLAIN, 45 ));
		resetButton.setLocation(WIDTH / 4 * 3, 0);
		resetButton.setSize(new Dimension(WIDTH / 4, LANE_HEIGHT));
		
		gameOver.setForeground(Color.red);
		gameOver.setFont(new Font(null , Font.ITALIC, 45 ));
		gameOver.setLocation((int)(WIDTH * (3.0 / 8) + 50), TOTAL_HEIGHT / 2);
		gameOver.setSize(new Dimension(WIDTH / 4, LANE_HEIGHT));
	}
	
	//-------------------------------------------------------------------
	//******* TIMER ************* 
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
			
			//------------------------------------
			//  Checks if frogger is out of panel
			//------------------------------------
			if ( !screenBoundary.contains(Frog.getHitBox()) && Frog.getFrogY() > 0){
				timer.stop();
				gameOver.setVisible(true);
				setFocusable(false);
			}
			//------------------------------------
			// Coin Collision (an event would have handled this well)
			//------------------------------------
			for (int index = 0; index < lanes.length; index++){
				if (lanes[index].getCoin().getHitBox().intersects(Frog.getHitBox())){
					Coin.coinIncrement();
					lanes[index].disableCoin(true); //prevents drawCoin and moves coin location off-screen until lane deletion
				}
			}
			
			//-------------
			// time update
			//-------------
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
				setFocusable(true);
				timer.start();
				startButton.setVisible(false);
			}
			
			//---------------------------------------------
			// Restart action
			//---------------------------------------------
			if (event.getSource() == resetButton){
				
				setFocusable(false);
				gameOver.setVisible(false);
				startButton.setVisible(true);
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

				repaint();
				timer.stop();
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
    	   
          switch (event.getKeyCode())
          {
          	// Up arrow
          	case KeyEvent.VK_UP:
            	Frog.setCurrentImage(Frog.getImage(Frog.u));
            	if (Frog.y > Frog.maxY) { // Keeps frog from going off the screen upward
            		Frog.setFrogY(Frog.y += Frog.UP);
            		distance++; // Increments the distance traveled
            	}
                break;
                
            // Down arrow
          	case KeyEvent.VK_DOWN:
          		Frog.setCurrentImage(Frog.getImage(Frog.d));
            	Frog.setFrogY(Frog.y += Frog.DOWN);
            	distance--; // Decrements and updates the distance traveled
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
          
  		if (distance > maxDistance)
			maxDistance = distance;
  		distanceLabel.setText("Distance: " + distance + "m"); // Updates the distance traveled
  		maxDistanceLabel.setText("HIGH SCORE: " + maxDistance + "m"); // Updates the max distance traveled
        }
       
       //--------------------------------------------------------------
       //  Provide empty definitions for unused event methods.
       //--------------------------------------------------------------
       public void keyTyped(KeyEvent event) {}
       public void keyReleased(KeyEvent event) {}
    }
    private class CollisionEvent extends ActionEvent{
    	
		public CollisionEvent(Object arg0, int arg1, String arg2) {
			super(arg0, arg1, arg2);
			// TODO Auto-generated constructor stub
		}
    	
    }
}
