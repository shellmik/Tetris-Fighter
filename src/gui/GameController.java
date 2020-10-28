package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import timer.Clock;
import java.util.Calendar;
import java.awt.HeadlessException;
public class GameController extends JFrame {
	private Level gameLevel;
	
	private static final long serialVersionUID = -4722429764792514382L;

	private static final long FRAME_TIME = 1000L / 50L;
	
	//private static final int TYPE_COUNT = PieceGenerator.getInstance().piecesCollection.length;
	private int TYPE_COUNT;
		
	public BoardPanel board;
	
	public SidePanel side;//Felicia modified for storage
	
	public GameSave gameSave;
	
	boolean isPaused;
	
	private boolean isNewGame;
	
	public boolean isGameOver;
	
	private int score;
	
	public Random random;
	
	public Clock logicTimer;
	
	private int dropCooldown;
	
	private float gameSpeed,gameAcceleration;
	
	private PieceController pc;
	
	
	//singleton
	private static GameController theInstance = new GameController();
	public static GameController getInstance() {return theInstance;}	 

	
	public void setLevel(Level level) {
		this.gameLevel=level;
		
	}
	private GameController() {
		super("Tetris");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		this.board = new BoardPanel(this);
		this.side = new SidePanel(this);
		//this.gameLevel=side.getLevel();
		this.gameSave = new GameSave(this);
		add(board, BorderLayout.CENTER);//CENTER
		add(side, BorderLayout.EAST);
		
		addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
								
				switch(e.getKeyCode()) {
				
				/*
				 * Drop - When pressed, we check to see that the game is not
				 * paused and that there is no drop cooldown, then set the
				 * logic timer to run at a speed of 25 cycles per second.
				 */
				case KeyEvent.VK_S:
					if(!isPaused && dropCooldown == 0) {
						logicTimer.setCyclesPerSecond(25.0f);
					}
					break;
					
				/*
				 * Move Left - When pressed, we check to see that the game is
				 * not paused and that the position to the left of the current
				 * position is valid. If so, we decrement the current column by 1.
				 */
				case KeyEvent.VK_A:
					if(!isPaused && board.isValidAndEmpty(pc.currentType, pc.currentCol - 1, pc.currentRow, pc.currentRotation)) {
						pc.currentCol--;
					}
					break;
					
				/*
				 * Move Right - When pressed, we check to see that the game is
				 * not paused and that the position to the right of the current
				 * position is valid. If so, we increment the current column by 1.
				 */
				case KeyEvent.VK_D:
					if(!isPaused && board.isValidAndEmpty(pc.currentType, pc.currentCol + 1, pc.currentRow, pc.currentRotation)) {
						pc.currentCol++;
					}
					break;
					
				/*
				 * Rotate Anticlockwise - When pressed, check to see that the game is not paused
				 * and then attempt to rotate the piece anticlockwise. Because of the size and
				 * complexity of the rotation code, as well as it's similarity to clockwise
				 * rotation, the code for rotating the piece is handled in another method.
				 */
				case KeyEvent.VK_J:
					if(!isPaused) {
						pc.rotatePiece((pc.currentRotation == 0) ? 3 : pc.currentRotation - 1);
					}
					break;
				
				/*
			     * Rotate Clockwise - When pressed, check to see that the game is not paused
				 * and then attempt to rotate the piece clockwise. Because of the size and
				 * complexity of the rotation code, as well as it's similarity to anticlockwise
				 * rotation, the code for rotating the piece is handled in another method.
				 */
				case KeyEvent.VK_K:
					if(!isPaused) {
						pc.rotatePiece((pc.currentRotation == 3) ? 0 : pc.currentRotation + 1);
					}
					break;
					
				/*
				 * Pause Game - When pressed, check to see that we're currently playing a game.
				 * If so, toggle the pause variable and update the logic timer to reflect this
				 * change, otherwise the game will execute a huge number of updates and essentially
				 * cause an instant game over when we unpause if we stay paused for more than a
				 * minute or so.
				 */
				case KeyEvent.VK_P:
					if(!isGameOver && !isNewGame) {
						isPaused = !isPaused;
						logicTimer.setPaused(isPaused);
					}
					
					break;
				
				case KeyEvent.VK_ENTER:
					if(isGameOver || isNewGame) {
						resetGame();
					}
					break;
				
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
				switch(e.getKeyCode()) {
				
				/*
				 * Drop - When released, we set the speed of the logic timer
				 * back to whatever the current game speed is and clear out
				 * any cycles that might still be elapsed.
				 */
				case KeyEvent.VK_S:
					logicTimer.setCyclesPerSecond(gameSpeed);
					logicTimer.reset();
					break;
				}
				
			}
			
		});
		
		/*
		 * Here we resize the frame to hold the BoardPanel and SidePanel instances,
		 * center the window on the screen, and show it to the user.
		 */
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Starts the game running. Initializes everything and enters the game loop.
	 */
	public void startGame() {
		/*
		 * Initialize our random number generator, logic timer, and new game variables.
		 */
		this.random = new Random();
		this.isNewGame = true;
		//this.gameSpeed = 1.0f;
		this.gameSpeed=this.gameLevel.getSpeed();
		System.out.println("gameSpeed="+gameSpeed);
		
		this.logicTimer = new Clock(gameSpeed);
		pc = new PieceController(this);
		/*
		 * Setup the timer to keep the game from running before the user presses enter
		 * to start it.
		 */

		logicTimer.setPaused(true);
		
		while(true) {
			//Get the time that the frame started.
			long start = System.nanoTime();
			
			//Update the logic timer.
			logicTimer.update();
			
			/*
			 * If a cycle has elapsed on the timer, we can update the game and
			 * move our current piece down.
			 */
			if(logicTimer.hasElapsedCycle()) {
				updateGame();
				
			}
			//Decrement the drop cool down if necessary.
			if(dropCooldown > 0) {
				dropCooldown--;
			}
			
			//Display the window to the user.
			renderGame();
			
			/*
			 * Sleep to cap the framerate.
			 */
			long delta = (System.nanoTime() - start) / 1000000L;
			if(delta < FRAME_TIME) {
				try {
					Thread.sleep(FRAME_TIME - delta);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Updates the game and handles the bulk of it's logic.
	 */
	private void updateGame() {
		/*
		 * Check to see if the piece's position can move down to the next row.
		 */
		if(board.isValidAndEmpty(pc.currentType, pc.currentCol, pc.currentRow + 1, pc.currentRotation)) {
			//Increment the current row if it's safe to do so.
			pc.currentRow++;
		} else {
			/*
			 * We've either reached the bottom of the board, or landed on another piece, so
			 * we need to add the piece to the board.
			 */
			board.addPiece(pc.currentType, pc.currentCol, pc.currentRow, pc.currentRotation);
			
			/*
			 * Check to see if adding the new piece resulted in any cleared lines. If so,
			 * increase the player's score. (Up to 4 lines can be cleared in a single go;
			 * [1 = 100pts, 2 = 200pts, 3 = 400pts, 4 = 800pts]).
			 */
			int cleared = board.checkLines();
			if(cleared > 0) {
				score += 50 << cleared;
			}
			
			/*
			 * Increase the speed slightly for the next piece and update the game's timer
			 * to reflect the increase.
			 */
			System.out.println("gbefore change: "+gameSpeed);
			this.gameAcceleration=this.gameLevel.getAccelaration();
			gameSpeed += this.gameAcceleration;//0.035f
			System.out.println("gameSpeed chaned to"+gameSpeed);
			logicTimer.setCyclesPerSecond(gameSpeed);
			logicTimer.reset();
			
			/*
			 * Set the drop cooldown so the next piece doesn't automatically come flying
			 * in from the heavens immediately after this piece hits if we've not reacted
			 * yet. (~0.5 second buffer).
			 */
			dropCooldown = 25;
			
			
			
			/*
			 * Spawn a new piece to control.
			 */
			pc.spawnPiece();
		}		
	}
	
	/**
	 * Forces the BoardPanel and SidePanel to repaint.
	 */
	private void renderGame() {
		board.repaint();
		side.repaint();
		// System.out.println("repaint!");
	}
	
	/**
	 * Resets the game variables to their default values at the start
	 * of a new game.
	 */
	private void resetGame() {
		
		
		this.score = 0;
		//this.gameSpeed = 1.0f;
		this.gameSpeed=this.gameLevel.getSpeed();

		pc.nextType = PieceGenerator.getInstance().piecesCollection[random.nextInt(this.gameLevel.getTileCnt())];
		System.out.println(this.gameLevel.getTileCnt());
		//pc.nextType = PieceGenerator.getInstance().piecesCollection[random.nextInt(TYPE_COUNT)];
		pc.nextType = pc.nextType;
		this.isNewGame = false;
		this.isGameOver = false;		
		board.clear();
		logicTimer.reset();
		logicTimer.setCyclesPerSecond(gameSpeed);
		pc.spawnPiece();
	}
		
	/**
	 * Spawns a new piece and resets our piece's variables to their default
	 * values.
	 */
	

	/**
	 * Attempts to set the rotation of the current piece to newRotation.
	 * @param newRotation The rotation of the new peice.
	 */
	
	
	/**
	 * Checks to see whether or not the game is paused.
	 * @return Whether or not the game is paused.
	 */
	public boolean isPaused() {
		return isPaused;
	}
	
	public void saveCurrent() {
		  Calendar now = Calendar.getInstance();
		  String name = side.getUserName();
		  String date = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH) + ": " + now.get(Calendar.HOUR_OF_DAY) + " h " + now.get(Calendar.MINUTE) + " min";
		  gameSave.save(name, score, date);
		 }	
 public void showRank() {
  
  JFrame frame = new JFrame();
  frame.setTitle("Ranking");
  frame.setSize(500, 700);
  frame.setDefaultCloseOperation(2);
  frame.setLocationRelativeTo(null);
  frame.setLayout(null);
  frame.setResizable(false);
  try {
   ArrayList list = gameSave.openRankingList();
   
   int listSize = list.size();
   
   JLabel lab = new JLabel("User        Max        Time");
   lab.setBounds(30, 20, 400, 50);
   frame.add(lab);
   
   for (int i = 0; i < list.size(); i++) {
       User user = (User) list.get(i);
       JLabel label = new JLabel(user.toString());
       label.setBounds(30, 20 + (i + 1) * 50, 400, 60);
       frame.add(label);
   }
   frame.setVisible(true);
  } catch (Exception e) {
   JLabel lab = new JLabel("Sorry, there is no record at all!");
         lab.setBounds(30, 60, 400, 50);
         frame.add(lab);
         frame.setVisible(true);
  }
        
 }
 
 public void clearStorage() {
  gameSave.clean();
 }
	/**
	 * Checks to see whether or not the game is over.
	 * @return Whether or not the game is over.
	 */
	public boolean isGameOver() {
		return isGameOver;
	}
	
	/**
	 * Checks to see whether or not we're on a new game.
	 * @return Whether or not this is a new game.
	 */
	public boolean isNewGame() {
		return isNewGame;
	}
	
	public int getScore() {
		return score;
	}
	
	public Tile getPieceType() {
		return pc.currentType;
	}

	public Tile getNextPieceType() {
		return pc.nextType;
	}
	
	/**
	 * Gets the column of the current piece.
	 * @return The column.
	 */
	public int getPieceCol() {
		return pc.currentCol;
	}
	
	/**
	 * Gets the row of the current piece.
	 * @return The row.
	 */
	public int getPieceRow() {
		return pc.currentRow;
	}
	
	/**
	 * Gets the rotation of the current piece.
	 * @return The rotation.
	 */
	public int getPieceRotation() {
		return pc.currentRotation;
	}
	
	public int getTypeCnt() {
		return this.gameLevel.getTileCnt();
	}

}
