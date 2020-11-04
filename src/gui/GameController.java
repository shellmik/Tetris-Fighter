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

	boolean isPaused;

	private boolean isNewGame;

	public boolean isGameOver;

	private static final long FRAME_TIME = 1000L / 50L;

	private int TYPE_COUNT;

	private int score;

	private int dropCooldown;

	private float gameSpeed, gameAcceleration;
	
	public BoardPanel board;

	public SidePanel side;
	
	private Level gameLevel;

	public GameSaver gameSave;

	private PieceController pc;
	
	private PieceGenerator pg;
	
	public Random random;

	public Clock logicTimer;

	private static GameController theInstance = new GameController();

	public static GameController getInstance() {// singleton
		return theInstance;
	}
	

	// Constructor
	private GameController() {
		super("Tetris");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		this.board = new BoardPanel(this);
		this.side = new SidePanel(this);
		pg=PieceGenerator.getInstance();
		// this.gameLevel=side.getLevel();
		this.gameSave = new GameSaver();
		add(board, BorderLayout.CENTER);// CENTER
		add(side, BorderLayout.EAST);
		
		
		
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				switch (e.getKeyCode()) {

				case KeyEvent.VK_S:
					if (!isPaused && dropCooldown == 0) {
						logicTimer.setCyclesPerSecond(25.0f);
					}
					break;

				case KeyEvent.VK_A:
					if (!isPaused && board.isValidAndEmpty(pc.currentType, pc.currentCol - 1, pc.currentRow,
							pc.currentRotation)) {
						pc.currentCol--;
					}
					break;

				case KeyEvent.VK_D:
					if (!isPaused && board.isValidAndEmpty(pc.currentType, pc.currentCol + 1, pc.currentRow,
							pc.currentRotation)) {
						pc.currentCol++;
					}
					break;

				case KeyEvent.VK_J:
					if (!isPaused) {
						pc.rotatePiece((pc.currentRotation == 0) ? 3 : pc.currentRotation - 1);
					}
					break;

				case KeyEvent.VK_K:
					if (!isPaused) {
						pc.rotatePiece((pc.currentRotation == 3) ? 0 : pc.currentRotation + 1);
					}
					break;

				case KeyEvent.VK_P:
					if (!isGameOver && !isNewGame) {
						isPaused = !isPaused;
						logicTimer.setPaused(isPaused);
					}

					break;

				case KeyEvent.VK_ENTER:
					if (isGameOver || isNewGame) {
						resetGame();
					}
					break;

				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

				switch (e.getKeyCode()) {

				/*
				 * Drop - When released, we set the speed of the logic timer back to whatever
				 * the current game speed is and clear out any cycles that might still be
				 * elapsed.
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

	// Key Functions
	public void startGame() {
		/*
		 * Initialize our random number generator, logic timer, and new game variables.
		 */
		this.random = new Random();
		this.isNewGame = true;
		// this.gameSpeed = 1.0f;
		this.gameSpeed = this.gameLevel.getSpeed();
		System.out.println("gameSpeed=" + gameSpeed);

		this.logicTimer = new Clock(gameSpeed);
		pc = new PieceController();
		/*
		 * Setup the timer to keep the game from running before the user presses enter
		 * to start it.
		 */

		logicTimer.setPaused(true);

		while (true) {
			// Get the time that the frame started.
			long start = System.nanoTime();

			// Update the logic timer.
			logicTimer.update();

			/*
			 * If a cycle has elapsed on the timer, we can update the game and move our
			 * current piece down.
			 */
			if (logicTimer.hasElapsedCycle()) {
				updateGame();

			}
			// Decrement the drop cool down if necessary.
			if (dropCooldown > 0) {
				dropCooldown--;
			}

			// Display the window to the user.
			renderGame();

			/*
			 * Sleep to cap the framerate.
			 */
			long delta = (System.nanoTime() - start) / 1000000L;
			if (delta < FRAME_TIME) {
				try {
					Thread.sleep(FRAME_TIME - delta);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void updateGame() {
		// Check to see if the piece's position can move down to the next row.
		if (board.isValidAndEmpty(pc.currentType, pc.currentCol, pc.currentRow + 1, pc.currentRotation)) {
			// Increment the current row if it's safe to do so.
			pc.currentRow++;
		} else {
			/*
			 * We've either reached the bottom of the board, or landed on another piece, so
			 * we need to add the piece to the board.
			 */
			board.addPiece(pc.currentType, pc.currentCol, pc.currentRow, pc.currentRotation);

			/*
			 * Check to see if adding the new piece resulted in any cleared lines. If so,
			 * increase the player's score. (Up to 4 lines can be cleared in a single go; [1
			 * = 100pts, 2 = 200pts, 3 = 400pts, 4 = 800pts]).
			 */
			int cleared = board.checkLines();
			if (cleared > 0) {
				score += 50 << cleared;
			}

			/*
			 * Increase the speed slightly for the next piece and update the game's timer to
			 * reflect the increase.
			 */
			System.out.println("gbefore change: " + gameSpeed);
			this.gameAcceleration = this.gameLevel.getAccelaration();
			gameSpeed += this.gameAcceleration;// 0.035f
			System.out.println("gameSpeed chaned to" + gameSpeed);
			logicTimer.setCyclesPerSecond(gameSpeed);
			logicTimer.reset();

			/*
			 * Set the drop cooldown so the next piece doesn't automatically come flying in
			 * from the heavens immediately after this piece hits if we've not reacted yet.
			 * (~0.5 second buffer).
			 */
			dropCooldown = 25;

			/*
			 * Spawn a new piece to control.
			 */
			pc.spawnPiece();
		}
	}

	private void renderGame() {
		board.repaint();
		side.repaint();
		// System.out.println("repaint!");
	}

	private void resetGame() {
		this.score = 0;
		// this.gameSpeed = 1.0f;
		this.gameSpeed = this.gameLevel.getSpeed();
		
		this.TYPE_COUNT=this.gameLevel.getTileCnt();
		int tile_idx=random.nextInt(TYPE_COUNT);
		pc.nextType = pg.getType(tile_idx);
		
		
		System.out.println(this.gameLevel.getTileCnt());
		pc.nextType = pc.nextType;
		this.isNewGame = false;
		this.isGameOver = false;
		board.clear();
		logicTimer.reset();
		logicTimer.setCyclesPerSecond(gameSpeed);
		pc.spawnPiece();
	}
	
	public boolean isPaused() {
		return isPaused;
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public boolean isNewGame() {
		return isNewGame;
	}

	
	// Additional Functions
	public void saveCurrent() {
		Calendar now = Calendar.getInstance();
		String name = side.getUserName();
		String date = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-"
				+ now.get(Calendar.DAY_OF_MONTH) + "  " + now.get(Calendar.HOUR_OF_DAY) + ":"
				+ now.get(Calendar.MINUTE);
		gameSave.save(name, score, date);
	}

	public void showRank() {
		gameSave.display();

	}

	public void clearStorage() {
		gameSave.clean();
	}

	public boolean check(Tile currentType, int currentCol, int currentRow, int currentRotation) {
		return board.isValidAndEmpty(currentType, currentCol, currentRow, currentRotation);
	}

	public void pauseTime() {
		logicTimer.setPaused(true);

	}
	
	//getters and setters
	public void setLevel(Level level) {
		this.gameLevel = level;
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

	public int getPieceCol() {
		return pc.currentCol;
	}

	public int getPieceRow() {
		return pc.currentRow;
	}

	public int getPieceRotation() {
		return pc.currentRotation;
	}

	public int getTypeCnt() {
		return this.gameLevel.getTileCnt();
	}


}
