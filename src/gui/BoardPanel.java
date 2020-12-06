package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

import game.GameController;

public class BoardPanel extends JPanel implements Panel {
	// attributes about the left board
	private static final long serialVersionUID = 1L;
	private static final int board_wide = 5;
	private static final int colCount = 10;
	private static final int visible_row = 20;
	private static final int hide_row = 2;
	private static final int allRow_count = visible_row + hide_row;
	private static final int tileSize = 24;
	private static final int shade_width = 4;
	
	private static final int centerX = colCount * tileSize / 2;
	private static final int centerY = visible_row * tileSize / 2;

	private static final Font LARGE_FONT = new Font("Impact", Font.BOLD, 46);
	private static final Font MID_FONT = new Font("Impact", Font.BOLD, 20);
	private static final Font SMALL_FONT = new Font("Comic", Font.BOLD, 12);
	private static final int LARGE_INSET = 40;
	private static final int CONTROLS_INSET = 300;
	private static final int TEXT_STRIDE = 25;

	private static final int PANEL_WIDTH = colCount * tileSize + board_wide * 2;
	private static final int PANEL_HEIGHT = visible_row * tileSize + board_wide * 2;
	
	private GameController tetris;

	private Tile[][] tiles;
	private Drawer drawer = new Drawer(tileSize, shade_width);
	
	//constructor
	public BoardPanel(GameController tetris) {
		this.tetris = tetris;
		this.tiles = new Tile[allRow_count][colCount];
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setBackground(Color.BLACK);
	}
	
	// key functions
	// Resets the board and clears away any tiles
	public void clearBoard() {
		for (int i = 0; i < allRow_count; i++) {
			for (int j = 0; j < colCount; j++) {
				tiles[i][j] = null;
			}
		}
	}

	
	public boolean isValidAndEmpty(Tile type, int x, int y, int rotation) {

		// Ensure the piece is in a valid column.
		if (x < -type.getLeftInset(rotation) || x + type.getDimension() - type.getRightInset(rotation) >= colCount) {
			return false;
		}

		// Ensure the piece is in a valid row.
		if (y < -type.getTopInset(rotation) || y + type.getDimension() - type.getBottomInset(rotation) >= allRow_count) {
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
	// add the piece if it is empty
	public void addPiece(Tile type, int x, int y, int rotation) {
		for (int col = 0; col < type.getDimension(); col++) {
			for (int row = 0; row < type.getDimension(); row++) {
				if (type.isTile(col, row, rotation)) {
					setTile(col + x, row + y, type);
				}
			}
		}
	}
	// check if lines that are full
	public int checkLines() {
		int completedLines = 0;
		for (int row = 0; row < allRow_count; row++) {
			if (checkLine(row)) {
				completedLines++;
			}
		}
		return completedLines;
	}
	// check a single line
	private boolean checkLine(int line) {
		for (int col = 0; col < colCount; col++) {
			if (!isOccupied(col, line)) {
				return false;
			}
		}
		for (int row = line - 1; row >= 0; row--) {
			for (int col = 0; col < colCount; col++) {
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
	// draw all the components of left panel
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.translate(board_wide, board_wide);
		if (tetris.isPaused()) {
			g.setFont(LARGE_FONT);
			g.setColor(Color.WHITE);
			String msg = "PAUSED";
			g.drawString(msg, centerX - g.getFontMetrics().stringWidth(msg) / 2, centerY);
		} 
		else if (tetris.isNewGame() || tetris.isGameOver()) {
			g.setFont(LARGE_FONT);
			g.setColor(new Color(255, 182, 193));
			String msg = tetris.isGameOver() ? "GAME OVER" : "TETRIS";
			
			g.drawString(msg, centerX - g.getFontMetrics().stringWidth(msg) / 2, 130);
			g.setFont(MID_FONT);
			msg = "SUBMIT";
			g.drawString(msg, centerX - g.getFontMetrics().stringWidth(msg) / 2, 180);
			msg = "and press ENTER" + (tetris.isNewGame() ? "" : " Again");
			g.drawString(msg, centerX - g.getFontMetrics().stringWidth(msg) / 2, 200);

			g.setFont(new Font("Impact", Font.BOLD, 16));
			g.drawString("<  CONTROLS  >", centerX - g.getFontMetrics().stringWidth("[Controls]") / 2 - 5, 290);// on the
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
			for (int x = 0; x < colCount; x++) {
				for (int y = hide_row; y < allRow_count; y++) {
					
					Tile tile = getTile(x, y);
					if (tile != null) {
						drawer.drawTile(tile, x * tileSize, (y - hide_row) * tileSize, g);
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
						drawer.drawTile(type, (pieceCol + col) * tileSize,
								(pieceRow + row - hide_row) * tileSize, g);
					}
				}
			}
		
			//draw grid
			g.setColor(Color.DARK_GRAY);
			for (int x = 0; x < colCount; x++) {
				for (int y = 0; y < visible_row; y++) {
					g.drawLine(0, y * tileSize, colCount * tileSize, y * tileSize);
					g.drawLine(x * tileSize, 0, x * tileSize, visible_row * tileSize);
				}
			}
		
		}

		g.setColor(Color.WHITE);
		g.drawRect(0, 0, tileSize * colCount, tileSize * visible_row);
	}
	
	
	//getter and setter
	public static int getPANEL_HEIGHT() {	
		return PANEL_HEIGHT;
	}
	
	public static int getCOL_COUNT() {
		return colCount;
	}
	
	public static int getROW_COUNT() {
		return allRow_count;
	}
	
	public static int getTILE_SIZE () {
		return tileSize ;
	}
	
	public static int getSHADE_WIDTH () {	
		return shade_width ;
	}

}
