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
	private static final long FRAME_TIME = 1000L / 50L;
	
	private boolean isPaused;
	private boolean isNewGame;
	private boolean isGameOver;

	private int score;
	private int dropCooldown;
	private Random random;
	private Level gameLevel;
	private int gameTypeCnt;
	private float gameAcceleration;
	
	private PieceController pc;
	private PieceGenerator pg;
	private BoardPanel board;
	private SidePanel side;
	private Clock logicTimer;
	private GameSaver gameSaver;

	// singleton
	private static GameController theInstance = new GameController();
	public static GameController getInstance() {
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
		pg = PieceGenerator.getInstance();
		this.gameSaver = new GameSaver();
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
					if (!isPaused && board.isValidAndEmpty(pc.getCurrentType(), pc.getCurrentCol() - 1,
							pc.getCurrentRow(), pc.getCurrentRotation())) {
						pc.setCurrentCol(pc.getCurrentCol() - 1);
					}
					break;

				case KeyEvent.VK_D:
					if (!isPaused && board.isValidAndEmpty(pc.getCurrentType(), pc.getCurrentCol() + 1,
							pc.getCurrentRow(), pc.getCurrentRotation())) {
						pc.setCurrentCol(pc.getCurrentCol() + 1);
					}
					break;

				case KeyEvent.VK_J:
					if (!isPaused) {
						pc.rotatePiece((pc.getCurrentRotation() == 0) ? 3 : pc.getCurrentRotation() - 1);
					}
					break;

				case KeyEvent.VK_K:
					if (!isPaused) {
						pc.rotatePiece((pc.getCurrentRotation() == 3) ? 0 : pc.getCurrentRotation() + 1);
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
				float gameSpeed = gameLevel.getSpeed();
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

		// set speed
		float gameSpeed = this.gameLevel.getSpeed();
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
		if (board.isValidAndEmpty(pc.getCurrentType(), pc.getCurrentCol(), pc.getCurrentRow() + 1, pc.getCurrentRotation())) {
			pc.setCurrentRow(pc.getCurrentRow() + 1);
		} 
		else {
			board.addPiece(pc.getCurrentType(), pc.getCurrentCol(), pc.getCurrentRow(), pc.getCurrentRotation());
			int cleared = board.checkLines();
			if (cleared > 0) {
				score += 50 << cleared;
			}
			float gameSpeed = this.gameLevel.getSpeed();
			System.out.println("gbefore change: " + gameSpeed);
			
			this.gameAcceleration = this.gameLevel.getAccelaration();
			gameSpeed += this.gameAcceleration;// 0.035f
			System.out.println("gameSpeed chaned to" + gameSpeed);
			logicTimer.setCyclesPerSecond(gameSpeed);
			logicTimer.reset();
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
		float gameSpeed = this.gameLevel.getSpeed();

		this.gameTypeCnt = this.gameLevel.getTileCnt();
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
		gameSaver.save(name, score, date);
	}

	public void showRank() {
		gameSaver.display();

	}

	public void clearStorage() {
		gameSaver.clean();
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
