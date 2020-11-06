package game;

import java.util.Random;

import gui.BoardPanel;
import gui.Tile;
import timer.Clock;

public class PieceController {
	public Tile currentType;

	public Tile nextType;

	private GameController game = GameController.getInstance();

	private PieceGenerator pg = PieceGenerator.getInstance();
	
	public int currentCol;

	public int currentRow;

	public int currentRotation;

	private static PieceController instance = new PieceController();

	public static PieceController getInstance() {
		return instance;
	}

	@SuppressWarnings("static-access")
	public void spawnPiece() {

		this.currentType = nextType;

		this.currentCol = currentType.getSpawnColumn();

		this.currentRow = currentType.getSpawnRow();

		this.currentRotation = 0;

		int num = new Random().nextInt(this.game.getTypeCnt());
		System.out.println("random:  " + num);
		
		this.nextType = pg.getType(num);

		if (!game.check(currentType, currentCol, currentRow, currentRotation)) {
			game.setGameOver(true);
			game.pauseTime();
		}
	}

	public void rotatePiece(int newRotation) {

		int newColumn = currentCol;
		int newRow = currentRow;

		int left = currentType.getLeftInset(newRotation);
		int right = currentType.getRightInset(newRotation);
		int top = currentType.getTopInset(newRotation);
		int bottom = currentType.getBottomInset(newRotation);

		if (currentCol < -left) {
			newColumn -= currentCol - left;
		} else if (currentCol + currentType.getDimension() - right >= BoardPanel.getCOL_COUNT()) {
			newColumn -= (currentCol + currentType.getDimension() - right) - BoardPanel.getCOL_COUNT() + 1;
		}

		if (currentRow < -top) {
			newRow -= currentRow - top;
		} else if (currentRow + currentType.getDimension() - bottom >= BoardPanel.getROW_COUNT()) {
			newRow -= (currentRow + currentType.getDimension() - bottom) - BoardPanel.getROW_COUNT() + 1;
		}

		if (game.check(currentType, newColumn, newRow, newRotation)) {
			currentRotation = newRotation;
			currentRow = newRow;
			currentCol = newColumn;
		}
	}

	//getters and setters
	public Tile getCurrentType() {

		return currentType;
	}

	public void setCurrentType(Tile t) {

		currentType = t;
	}

	public Tile getNextType() {

		return nextType;
	}

	public void setNextType(Tile t) {
		System.out.println("???");
		nextType = t;
	}

	public int getCurrentCol() {

		return currentCol;
	}
	
	public void setCurrentCol(int col) {

		currentCol = col;
	}
	
	public int getCurrentRow() {

		return currentRow;
	}
	
	public void setCurrentRow(int row) {

		currentRow = row;
	}
	
	public int getCurrentRotation() {

		return currentRotation;
	}

	public Tile getTileType(int tile_idx) {
		return pg.getType(tile_idx);
	}
	

}
