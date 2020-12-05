package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

import game.GameController;

public class BoardPanel extends JPanel implements Panel {

	private static final long serialVersionUID = 1L;
	private static final int BORDER_WIDTH = 5;
	private static final int COL_COUNT = 10;
	private static final int VISIBLE_ROW_COUNT = 20;
	private static final int HIDDEN_ROW_COUNT = 2;
	private static final int ROW_COUNT = VISIBLE_ROW_COUNT + HIDDEN_ROW_COUNT;
	private static final int TILE_SIZE = 24;
	private static final int SHADE_WIDTH = 4;
	
	private static final int CENTER_X = COL_COUNT * TILE_SIZE / 2;
	private static final int CENTER_Y = VISIBLE_ROW_COUNT * TILE_SIZE / 2;

	private static final Font LARGE_FONT = new Font("Impact", Font.BOLD, 46);
	private static final Font MID_FONT = new Font("Impact", Font.BOLD, 20);
	private static final Font SMALL_FONT = new Font("Comic", Font.BOLD, 12);
	private static final int SMALL_INSET = 20;
	private static final int LARGE_INSET = 40;
	private static final int CONTROLS_INSET = 300;
	private static final int TEXT_STRIDE = 25;

	private static final int PANEL_WIDTH = COL_COUNT * TILE_SIZE + BORDER_WIDTH * 2;
	private static final int PANEL_HEIGHT = VISIBLE_ROW_COUNT * TILE_SIZE + BORDER_WIDTH * 2;
	
	private GameController tetris;

	private Tile[][] tiles;
	private Drawer drawer = new Drawer(TILE_SIZE, SHADE_WIDTH);
	
	//constructor
	public BoardPanel(GameController tetris) {
		this.tetris = tetris;
		this.tiles = new Tile[ROW_COUNT][COL_COUNT];
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setBackground(Color.BLACK);
	}
	
	// key functions
	// Resets the board and clears away any tiles
	public void clearBoard() {
		for (int i = 0; i < ROW_COUNT; i++) {
			for (int j = 0; j < COL_COUNT; j++) {
				tiles[i][j] = null;
			}
		}
	}

	
	public boolean isValidAndEmpty(Tile type, int x, int y, int rotation) {

		// Ensure the piece is in a valid column.
		if (x < -type.getLeftInset(rotation) || x + type.getDimension() - type.getRightInset(rotation) >= COL_COUNT) {
			return false;
		}

		// Ensure the piece is in a valid row.
		if (y < -type.getTopInset(rotation) || y + type.getDimension() - type.getBottomInset(rotation) >= ROW_COUNT) {
			return false;
		}

		for (int col = 0; col < type.getDimension(); col++) {
			for (int row = 0; row < type.getDimension(); row++) {
				if (type.isTile(col, row, rotation) && isOccupied(x + col, y + row)) {
					return false;
				}
			}
		}
		return true;
	}

	public void addPiece(Tile type, int x, int y, int rotation) {
		for (int col = 0; col < type.getDimension(); col++) {
			for (int row = 0; row < type.getDimension(); row++) {
				if (type.isTile(col, row, rotation)) {
					setTile(col + x, row + y, type);
				}
			}
		}
	}

	public int checkLines() {
		int completedLines = 0;
		for (int row = 0; row < ROW_COUNT; row++) {
			if (checkLine(row)) {
				completedLines++;
			}
		}
		return completedLines;
	}

	private boolean checkLine(int line) {
		for (int col = 0; col < COL_COUNT; col++) {
			if (!isOccupied(col, line)) {
				return false;
			}
		}

		for (int row = line - 1; row >= 0; row--) {
			for (int col = 0; col < COL_COUNT; col++) {
				setTile(col, row + 1, getTile(col, row));
			}
		}
		return true;
	}

	
	private boolean isOccupied(int x, int y) {
		return tiles[y][x] != null;
	}

	private void setTile(int x, int y, Tile type) {
		tiles[y][x] = type;
	}

	private Tile getTile(int x, int y) {
		return tiles[y][x];
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.translate(BORDER_WIDTH, BORDER_WIDTH);
		if (tetris.isPaused()) {
			g.setFont(LARGE_FONT);
			g.setColor(Color.WHITE);
			String msg = "PAUSED";
			g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg) / 2, CENTER_Y);
		} 
		else if (tetris.isNewGame() || tetris.isGameOver()) {
			g.setFont(LARGE_FONT);
			g.setColor(new Color(255, 182, 193));
			String msg = tetris.isGameOver() ? "GAME OVER" : "TETRIS";
			
			g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg) / 2, 130);
			g.setFont(MID_FONT);
			msg = "SUBMIT";
			g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg) / 2, 180);
			msg = "and press ENTER" + (tetris.isNewGame() ? "" : " Again");
			g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg) / 2, 200);

			g.setFont(new Font("Impact", Font.BOLD, 16));
			g.drawString("<  CONTROLS  >", CENTER_X - g.getFontMetrics().stringWidth("[Controls]") / 2 - 5, 290);// on the
																												// centre
			int offset = CONTROLS_INSET;
			g.setFont(SMALL_FONT);
			g.drawString("[A]- Move Left", LARGE_INSET, offset += TEXT_STRIDE);
			g.drawString("[D]- Move Right", LARGE_INSET, offset += TEXT_STRIDE);
			g.drawString("[J]- Rotate Anticlockwise", LARGE_INSET, offset += TEXT_STRIDE);
			g.drawString("[K]- Rotate Clockwise", LARGE_INSET, offset += TEXT_STRIDE);
			g.drawString("[S]- Drop", LARGE_INSET, offset += TEXT_STRIDE);
			g.drawString("[P]- Pause Game", LARGE_INSET, offset += TEXT_STRIDE);

		} else {
			//draw tiles that have fallen
			for (int x = 0; x < COL_COUNT; x++) {
				for (int y = HIDDEN_ROW_COUNT; y < ROW_COUNT; y++) {
					
					Tile tile = getTile(x, y);
					if (tile != null) {
						drawer.drawTile(tile, x * TILE_SIZE, (y - HIDDEN_ROW_COUNT) * TILE_SIZE, g);
					}
				}
			}

			Tile type = tetris.getPieceType();
			int pieceCol = tetris.getPieceCol();
			int pieceRow = tetris.getPieceRow();
			int rotation = tetris.getPieceRotation();

			// draw tiles that are falling
			for (int col = 0; col < type.getDimension(); col++) {
				for (int row = 0; row < type.getDimension(); row++) {
					if (pieceRow + row >= 2 && type.isTile(col, row, rotation)) {
						drawer.drawTile(type, (pieceCol + col) * TILE_SIZE,
								(pieceRow + row - HIDDEN_ROW_COUNT) * TILE_SIZE, g);
					}
				}
			}
		
			//draw grid
			g.setColor(Color.DARK_GRAY);
			for (int x = 0; x < COL_COUNT; x++) {
				for (int y = 0; y < VISIBLE_ROW_COUNT; y++) {
					g.drawLine(0, y * TILE_SIZE, COL_COUNT * TILE_SIZE, y * TILE_SIZE);
					g.drawLine(x * TILE_SIZE, 0, x * TILE_SIZE, VISIBLE_ROW_COUNT * TILE_SIZE);
				}
			}
		
		}

		g.setColor(Color.WHITE);
		g.drawRect(0, 0, TILE_SIZE * COL_COUNT, TILE_SIZE * VISIBLE_ROW_COUNT);
	}
	
	
	//getter and setter
	public static int getPANEL_HEIGHT() {	
		return PANEL_HEIGHT;
	}
	
	public static int getCOL_COUNT() {
		return COL_COUNT;
	}
	
	public static int getROW_COUNT() {
		return ROW_COUNT;
	}
	
	public static int getTILE_SIZE () {
		return TILE_SIZE ;
	}
	
	public static int getSHADE_WIDTH () {	
		return SHADE_WIDTH ;
	}

}
