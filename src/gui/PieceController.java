package gui;

import java.util.Random;

import timer.Clock;

public class PieceController {
	public Tile currentType;

	public Tile nextType;

	private GameController game = GameController.getInstance();

	private PieceGenerator pg = PieceGenerator.getInstance();
	
	public int currentCol;

	public int currentRow;

	public int currentRotation;

	public PieceController() {

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

}
