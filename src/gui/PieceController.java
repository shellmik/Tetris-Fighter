package gui;

import java.util.Random;

import timer.Clock;

public class PieceController {
	public Tile currentType;
<<<<<<< HEAD

	public Tile nextType;

	private GameController game;

	private Random random;

	public int currentCol;

	public int currentRow;

	public int currentRotation;

	private BoardPanel board;

	private Clock logicTimer;

	public PieceController(GameController g) {
		this.game = g;
		this.board = g.board;
		this.logicTimer = g.logicTimer;
		this.random = g.random;
	}

	@SuppressWarnings("static-access")
	public void spawnPiece() {
		/*
		 * Poll the last piece and reset our position and rotation to their default
		 * variables, then pick the next piece to use.
		 */

		this.currentType = nextType;
		// game.currentType = this.currentType;

		this.currentCol = currentType.getSpawnColumn();
		// game.currentCol = this.currentCol;

		this.currentRow = currentType.getSpawnRow();
		// game.currentRow = this.currentRow;

		this.currentRotation = 0;
		// game.currentRotation = 0;
		// System.out.println(board.isValidAndEmpty(currentType, currentCol, currentRow,
		// currentRotation));

		int num = random.nextInt(this.game.getTypeCnt());
		System.out.println("random:  " + num);
		this.nextType = PieceGenerator.getInstance().piecesCollection[num];
		// game.nextType = this.nextType;

		/*
		 * If the spawn point is invalid, we need to pause the game and flag that we've
		 * lost because it means that the pieces on the board have gotten too high.
		 */
		if (!board.isValidAndEmpty(currentType, currentCol, currentRow, currentRotation)) {
			game.isGameOver = true;
			logicTimer.setPaused(true);
		}
	}

	public void rotatePiece(int newRotation) {
		/*
		 * Sometimes pieces will need to be moved when rotated to avoid clipping out of
		 * the board (the I piece is a good example of this). Here we store a temporary
		 * row and column in case we need to move the tile as well.
		 */
		int newColumn = currentCol;
		int newRow = currentRow;

		/*
		 * Get the insets for each of the sides. These are used to determine how many
		 * empty rows or columns there are on a given side.
		 */
=======

	public Tile nextType;

	private GameController game = GameController.getInstance();

	private PieceGenerator pg = PieceGenerator.getInstance();
	
	public int currentCol;

	public int currentRow;

	public int currentRotation;

	public void spawnPiece() {

		this.currentType = nextType;

		this.currentCol = currentType.getSpawnColumn();

		this.currentRow = currentType.getSpawnRow();

		this.currentRotation = 0;

		int num = new Random().nextInt(this.game.getTypeCnt());
		System.out.println("random:  " + num);
		this.nextType = pg.getType(num);

		if (!game.check(currentType, currentCol, currentRow, currentRotation)) {
			game.isGameOver = true;
			game.pause();
		}
	}

	public void rotatePiece(int newRotation) {

		int newColumn = currentCol;
		int newRow = currentRow;
>>>>>>> 86e9120... refactor
		int left = currentType.getLeftInset(newRotation);
		int right = currentType.getRightInset(newRotation);
		int top = currentType.getTopInset(newRotation);
		int bottom = currentType.getBottomInset(newRotation);

<<<<<<< HEAD
		/*
		 * If the current piece is too far to the left or right, move the piece away
		 * from the edges so that the piece doesn't clip out of the map and
		 * automatically become invalid.
		 */
=======
>>>>>>> 86e9120... refactor
		if (currentCol < -left) {
			newColumn -= currentCol - left;
		} else if (currentCol + currentType.getDimension() - right >= BoardPanel.COL_COUNT) {
			newColumn -= (currentCol + currentType.getDimension() - right) - BoardPanel.COL_COUNT + 1;
		}

<<<<<<< HEAD
		/*
		 * If the current piece is too far to the top or bottom, move the piece away
		 * from the edges so that the piece doesn't clip out of the map and
		 * automatically become invalid.
		 */
=======
>>>>>>> 86e9120... refactor
		if (currentRow < -top) {
			newRow -= currentRow - top;
		} else if (currentRow + currentType.getDimension() - bottom >= BoardPanel.ROW_COUNT) {
			newRow -= (currentRow + currentType.getDimension() - bottom) - BoardPanel.ROW_COUNT + 1;
		}

<<<<<<< HEAD
		/*
		 * Check to see if the new position is acceptable. If it is, update the rotation
		 * and position of the piece.
		 */
		if (board.isValidAndEmpty(currentType, newColumn, newRow, newRotation)) {
=======
		if (game.check(currentType, newColumn, newRow, newRotation)) {
>>>>>>> 86e9120... refactor
			currentRotation = newRotation;
			currentRow = newRow;
			currentCol = newColumn;
		}
	}

	public Tile getCurrentType() {
		// TODO Auto-generated method stub
		return currentType;
	}

	public void setCurrentType(Tile t) {
		// TODO Auto-generated method stub
		currentType = t;
	}

	public Tile getNextType() {
		// TODO Auto-generated method stub
		return nextType;
	}

	public void setNextType(Tile t) {
		System.out.println("???");
		nextType = t;
	}

}
