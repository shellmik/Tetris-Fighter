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

	// default
	private static final long serialVersionUID = 1L;

	private boolean isPaused;
	private boolean isNewGame;
	private boolean isGameOver;

	private Level gameLevel;
	private int gameTypeCnt;
	private float gameSpeed;
	private float gameAcceleration;

	private int score;

	private PieceController pc;
	private PieceGenerator pg;

	private static final long FRAME_TIME = 1000L / 50L;
	private Clock logicTimer;

	private int dropCooldown;
	private Random random;

	private BoardPanel board;
	private SidePanel side;

	private GameSaver gameSave;

	private static GameController theInstance = new GameController();

	public static GameController getInstance() {// singleton
		return theInstance;
	}

	
	
	// Constructor
	private GameController() {
		super("Tetris");
		//setting board and side
		this.board = new BoardPanel(this);
		this.side = new SidePanel(this);
		this.gameSave = new GameSaver();
		pg = PieceGenerator.getInstance();
		
		//construct UI
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		add(board, BorderLayout.CENTER);
		add(side, BorderLayout.EAST);
		
		//TODO: is it possible to move the addKeyListener function outside? looks kinda messy
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Tile currentType = pc.getCurrentType();
				int currentCol = pc.getCurrentCol();
				int currentRow = pc.getCurrentRow();
				int currentRotation = pc.getCurrentRotation();
				
				switch (e.getKeyCode()) {

				case KeyEvent.VK_S:
					if (!isPaused && dropCooldown == 0) {
						logicTimer.setCyclesPerSecond(25.0f);
					}
					break;

				case KeyEvent.VK_A:
					if (!isPaused && board.isValidAndEmpty(currentType, currentCol - 1, currentRow,
							currentRotation)) {
						pc.setCurrentCol(currentCol - 1);
					}
					break;

				case KeyEvent.VK_D:
					if (!isPaused && board.isValidAndEmpty(currentType, currentCol + 1, currentRow,
							currentRotation)) {
						pc.setCurrentCol(currentCol + 1);
					}
					break;

				case KeyEvent.VK_J:
					if (!isPaused) {
						pc.rotatePiece((currentRotation == 0) ? 3 : currentRotation - 1);
					}
					break;

				case KeyEvent.VK_K:
					if (!isPaused) {
						pc.rotatePiece((currentRotation == 3) ? 0 : currentRotation + 1);
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
				case KeyEvent.VK_S:
					logicTimer.setCyclesPerSecond(gameSpeed);
					logicTimer.reset();
					break;
				}
			}
		});
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	// Key Functions
	public void startGame() {

		this.random = new Random();
		this.isNewGame = true;
		gameSpeed = this.gameLevel.getSpeed();
		System.out.println("gameSpeed=" + gameSpeed);

		this.logicTimer = new Clock(gameSpeed);
		pc = new PieceController();
		logicTimer.setPaused(true);

		while (true) {
	
			long start = System.nanoTime();

			logicTimer.update();

			if (logicTimer.hasElapsedCycle()) {
				updateGame();
			}
		
			if (dropCooldown > 0) {
				dropCooldown--;
			}

			renderGame();

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
	
		if (board.isValidAndEmpty(pc.getCurrentType(), pc.currentCol, pc.currentRow + 1, pc.currentRotation)) {
			
			pc.currentRow++;
		} else {
			/*
			 * We've either reached the bottom of the board, or landed on another piece, so
			 * we need to add the piece to the board.
			 */
			board.addPiece(pc.getCurrentType(), pc.currentCol, pc.currentRow, pc.currentRotation);

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

			pc.spawnPiece();
		}
	}

	private void renderGame() {
		board.repaint();
		side.repaint();
	}

	private void resetGame() {
		this.score = 0;
		gameSpeed = this.gameLevel.getSpeed();
		gameTypeCnt = this.gameLevel.getTileCnt();
		
		int tile_idx = random.nextInt(gameTypeCnt);
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

	public void setGameOver(boolean b) {
		this.isGameOver = b;
	}

	public void setPause(boolean b) {
		this.isPaused = b;
	}

	// getters and setters
	public boolean isPaused() {
		return isPaused;
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public boolean isNewGame() {
		return isNewGame;
	}

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
