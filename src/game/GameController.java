package game;

import java.awt.BorderLayout;
import java.util.Random;

import javax.swing.JFrame;
import gui.BoardPanel;
import gui.SidePanel;
import gui.Tile;
import timer.Clock;
import java.util.Calendar;


public class GameController extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static final long FRAME_TIME = 1000L / 50L;
	
	private boolean isGamePaused;
	private boolean isNewGame;
	private boolean isGameOver;
	private boolean isExit=false;

	private int score;
	private int cooltime;
	
	private Random random;
	private Level gameLevel; 
	private float gameSpeed;
	
	private PieceController pc = PieceController.getInstance();
	private BoardPanel board;
	private SidePanel side;
	private Clock logicTimer;
	private GameSaver gameSaver;

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
		this.gameSaver = GameSaver.getInstance();
		add(board, BorderLayout.CENTER);
		add(side, BorderLayout.EAST);
		addKeyListener(new KeyHandler() {});
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		this.isExit=false;
	}

	// Key Functions this function will start the game
	public void startGame() {

		this.isNewGame = true;

		// set speed
		float gameSpeed = this.gameLevel.getSpeed();
		System.out.println("gameSpeed=" + gameSpeed);
		this.logicTimer = new Clock(gameSpeed);

		pc = new PieceController();
		logicTimer.setPaused(true);
		// this while will run all the time 
		while (!isExit) {
			long start = System.nanoTime();
			logicTimer.update();

			if (logicTimer.hasElapsedCycle()) {
				updateGame();
			}
			// this is the cool down time
			if ( cooltime > 0) {
				 cooltime--;
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
		System.out.print("out!");
		//System.exit(0);
	}
    // this function will move the tile and add the speed 
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
			gameSpeed += this.gameLevel.getAccelaration();
			System.out.println("gameSpeed changed to" + gameSpeed);
			logicTimer.setCyclesPerSecond(gameSpeed);
			logicTimer.reset();
			 cooltime = 25;
			pc.spawnPiece();
		}
	}
	//	this function is used to render the panel
	public void renderGame() {
		board.repaint();
		side.repaint();
	}
	//	this function will reset all the attributes
	public void resetGame() {
		this.score = 0;
		this.gameSpeed=this.gameLevel.getSpeed();
		this.random = new Random();
		int tile_idx = random.nextInt(this.gameLevel.getTileCnt());
		pc.nextType = pc.getTileType(tile_idx);
		System.out.println(this.gameLevel.getTileCnt());
		pc.nextType = pc.nextType;
		this.isNewGame = false;
		this.isGameOver = false;
		board.clearBoard();
		logicTimer.reset();
		logicTimer.setCyclesPerSecond(gameSpeed);
		pc.spawnPiece();
	}

	// this function is used to get the store time
	public void saveCurrent() {
		Calendar now = Calendar.getInstance();
		String name = side.getUserName();
		String date = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-"
				+ now.get(Calendar.DAY_OF_MONTH) + "  " + now.get(Calendar.HOUR_OF_DAY) + ":"
				+ now.get(Calendar.MINUTE);
		gameSaver.save(name, score, date);
	}
	//  this function open the rank.txt file
	public void showRank() {
		gameSaver.display();
	}
	//  this function clear the rank
	public void clearStorage() {
		gameSaver.clean();
	}
	//  median function used to check the board
	public boolean check(Tile currentType, int currentCol, int currentRow, int currentRotation) {
		return board.isValidAndEmpty(currentType, currentCol, currentRow, currentRotation);
	}
	// pause the time
	public void pauseTime() {
		logicTimer.setPaused(true);

	}

	// getters and setters
	public Level getLevel() {
		return this.gameLevel;
	}
	
	public void setLevelTileCnt(int tile_cnt) {
		this.gameLevel.setTileCnt(tile_cnt);
	}

	public void setLevelSpeed(float speed) {
		this.gameLevel.setSpeed(speed);
	}

	public void setLevelAccelaration(float accelaration) {
		this.gameLevel.setAccelaration(accelaration);
	}
	
	public void setNewGame(boolean b) {
		this.isNewGame = b;
	}
	
	public void setGameOver(boolean b) {
		this.isGameOver = b;
	}

	public void setPause(boolean b) {
		this.isGamePaused = b;
	}

	public boolean isPaused() {
		return isGamePaused;
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
		return pc.getCurrentType();
	}

	public Tile getNextPieceType() {
		return pc.getNextType();
	}

	public int getPieceCol() {
		return pc.getCurrentCol() ;
	}

	public int getPieceRow() {
		return pc.getCurrentRow();
	}

	public int getPieceRotation() {
		return pc.getCurrentRotation();
	}

	public int getTypeCnt() {
		return this.gameLevel.getTileCnt();
	}

	public int getCooldown() {
		return this. cooltime;
	}

	public Clock getClock() {
		return this.logicTimer;
	}

	public BoardPanel getBoard() {
		return this.board;
	}

	public PieceController getPC() {
		return this.pc;
	}

	public boolean getIsNewGame() {
		return this.isNewGame;
	}

	public boolean getIsGameOver() {
		return this.isGameOver;
	}

	public float getGameSpeed() {
		return this.gameSpeed;
	}
	
	public void setExit() {
		this.isExit=true;
	}

}
