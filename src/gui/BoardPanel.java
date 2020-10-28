package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BoardPanel extends JPanel implements Panel{

	private static final long serialVersionUID = 5055679736784226108L;

	/**
	 * Minimum color component values for tiles. This is required if we
	 * want to show both light and dark shading on our tiles.
	 */
	public static final int COLOR_MIN = 35;
	public static final int COLOR_MAX = 255 - COLOR_MIN;
	
	private static final int BORDER_WIDTH = 5;//border around the game board width
	public static final int COL_COUNT = 10;//number of columns on the board
	private static final int VISIBLE_ROW_COUNT = 20;//number of visible rows on the board
	private static final int HIDDEN_ROW_COUNT = 2;//number of rows that are hidden from view.
	public static final int ROW_COUNT = VISIBLE_ROW_COUNT + HIDDEN_ROW_COUNT;//total row number the board contains
	
	public static final int TILE_SIZE = 24;//number of pixels that a tile takes up
	
	public static final int SHADE_WIDTH = 4;//tile shading width
	
	private static final int CENTER_X = COL_COUNT * TILE_SIZE / 2;//central x coordinate on the game board
	private static final int CENTER_Y = VISIBLE_ROW_COUNT * TILE_SIZE / 2;
		
	public static final int PANEL_WIDTH = COL_COUNT * TILE_SIZE + BORDER_WIDTH * 2;//total panel width
	public static final int PANEL_HEIGHT = VISIBLE_ROW_COUNT * TILE_SIZE + BORDER_WIDTH * 2;
	
	private static final Font LARGE_FONT = new Font("Arial", Font.BOLD, 38);
	private static final Font SMALL_FONT = new Font("Arial", Font.BOLD, 12);
	private static final int SMALL_INSET = 20;//pixel number used on a small insets (generally used for categories
	private static final int LARGE_INSET = 40;//number of pixels used on a large insets
	private static final int CONTROLS_INSET = 300;//y coordinate of the controls category
	private static final int TEXT_STRIDE = 25;//number of pixels to offset between each string
	
	private GameController tetris;
	
	private Tile[][] tiles;//tiles that make up the board
	
	private drawer draw = new drawer(TILE_SIZE, SHADE_WIDTH);
	
	

	public BoardPanel(GameController tetris) {
		this.tetris = tetris;
		this.tiles = new Tile[ROW_COUNT][COL_COUNT];
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setBackground(Color.BLACK);
	}

	public void clear() {//Resets the board and clears away any tiles
		for(int i = 0; i < ROW_COUNT; i++) {
			for(int j = 0; j < COL_COUNT; j++) {
				tiles[i][j] = null;
			}
		}
	}
	
	/**
	 * Determines whether or not a piece can be placed at the coordinates.
	 * @param type THe type of piece to use.
	 * @param x The x coordinate of the piece.
	 * @param y The y coordinate of the piece.
	 * @param rotation The rotation of the piece.
	 * @return Whether or not the position is valid.
	 */
	public boolean isValidAndEmpty(Tile type, int x, int y, int rotation) {
				
		//Ensure the piece is in a valid column.
		if(x < -type.getLeftInset(rotation) || x + type.getDimension() - type.getRightInset(rotation) >= COL_COUNT) {
			return false;
		}
		
		//Ensure the piece is in a valid row.
		if(y < -type.getTopInset(rotation) || y + type.getDimension() - type.getBottomInset(rotation) >= ROW_COUNT) {
			return false;
		}
		
		/*
		 * Loop through every tile in the piece and see if it conflicts with an existing tile.
		 * 
		 * Note: It's fine to do this even though it allows for wrapping because we've already
		 * checked to make sure the piece is in a valid location.
		 */
		for(int col = 0; col < type.getDimension(); col++) {
			for(int row = 0; row < type.getDimension(); row++) {
				if(type.isTile(col, row, rotation) && isOccupied(x + col, y + row)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Adds a piece to the game board. Note: Doesn't check for existing pieces,
	 * and will overwrite them if they exist.
	 * @param type The type of piece to place.
	 * @param x The x coordinate of the piece.
	 * @param y The y coordinate of the piece.
	 * @param rotation The rotation of the piece.
	 */
	public void addPiece(Tile type, int x, int y, int rotation) {
		for(int col = 0; col < type.getDimension(); col++) {
			for(int row = 0; row < type.getDimension(); row++) {
				if(type.isTile(col, row, rotation)) {
					setTile(col + x, row + y, type);
				}
			}
		}
	}
	
	/**
	 * Checks the board to see if any lines have been cleared, and
	 * removes them from the game.
	 * @return The number of lines that were cleared.
	 */
	public int checkLines() {
		int completedLines = 0;
		
		/*
		 * Here we loop through every line and check it to see if
		 * it's been cleared or not. If it has, we increment the
		 * number of completed lines and check the next row.
		 * 
		 * The checkLine function handles clearing the line and
		 * shifting the rest of the board down for us.
		 */
		for(int row = 0; row < ROW_COUNT; row++) {
			if(checkLine(row)) {
				completedLines++;
			}
		}
		return completedLines;
	}
			
	/**
	 * Checks whether or not {@code row} is full.
	 * @param line The row to check.
	 * @return Whether or not this row is full.
	 */
	private boolean checkLine(int line) {
		/*
		 * Iterate through every column in this row. If any of them are
		 * empty, then the row is not full.
		 */
		for(int col = 0; col < COL_COUNT; col++) {
			if(!isOccupied(col, line)) {
				return false;
			}
		}
		
		/*
		 * Since the line is filled, we need to 'remove' it from the game.
		 * To do this, we simply shift every row above it down by one.
		 */
		for(int row = line - 1; row >= 0; row--) {
			for(int col = 0; col < COL_COUNT; col++) {
				setTile(col, row + 1, getTile(col, row));
			}
		}
		return true;
	}
	
	
	private boolean isOccupied(int x, int y) {
		return tiles[y][x] != null;
	}
	
	/**
	 * Sets a tile located at the desired column and row.
	 * @param x The column.
	 * @param y The row.
	 * @param type The value to set to the tile to.
	 */
	private void setTile(int  x, int y, Tile type) {
		tiles[y][x] = type;
	}
		
	/**
	 * Gets a tile by it's column and row.
	 * @param x The column.
	 * @param y The row.
	 * @return The tile.
	 */
	private Tile getTile(int x, int y) {
		return tiles[y][x];
	}
	
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//This helps simplify the positioning of things.
		g.translate(BORDER_WIDTH, BORDER_WIDTH);
		
		/*
		 * Draw the board differently depending on the current game state.
		 */
		if(tetris.isPaused()) {
			g.setFont(LARGE_FONT);
			g.setColor(Color.WHITE);
			String msg = "PAUSED";
			g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg) / 2, CENTER_Y);
		} else if(tetris.isNewGame() || tetris.isGameOver()) {
			g.setFont(LARGE_FONT);
			g.setColor(new Color(255,182,193));
			
			/*
			 * Because both the game over and new game screens are nearly identical,
			 * we can handle them together and just use a ternary operator to change
			 * the messages that are displayed.
			 */
			String msg = tetris.isNewGame() ? "TETRIS" : "GAME OVER";
			g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg) / 2, 130);
			g.setFont(SMALL_FONT);
			msg = "Please SUBMIT your name and level";
			g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg) / 2, 180);
			msg = "Then press ENTER to play" + (tetris.isNewGame() ? "" : " Again");
			g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg) / 2, 200);
			
			g.setFont(new Font("Arial", Font.BOLD, 16));
			//g.drawString("[CONTROLS]", CENTER_X - g.getFontMetrics().stringWidth("[Controls]") / 2, 290);
			g.drawString("[CONTROLS]", CENTER_X - g.getFontMetrics().stringWidth("[Controls]") / 2-15, 290);//on the centre
			int offset= CONTROLS_INSET;
			g.setFont(SMALL_FONT);
			g.drawString("[A]- Move Left", LARGE_INSET, offset += TEXT_STRIDE);
			g.drawString("[D]- Move Right", LARGE_INSET, offset += TEXT_STRIDE);
			g.drawString("[J]- Rotate Anticlockwise", LARGE_INSET, offset += TEXT_STRIDE);
			g.drawString("[K]- Rotate Clockwise", LARGE_INSET, offset += TEXT_STRIDE);
			g.drawString("[S]- Drop", LARGE_INSET, offset += TEXT_STRIDE);
			g.drawString("[P]- Pause Game", LARGE_INSET, offset += TEXT_STRIDE);

			
			
		} else {
			
			/*
			 * Draw the tiles onto the board.
			 */
			for(int x = 0; x < COL_COUNT; x++) {
				for(int y = HIDDEN_ROW_COUNT; y < ROW_COUNT; y++) {
					Tile tile = getTile(x, y);
					if(tile != null) {
						draw.drawTile(tile, x * TILE_SIZE, (y - HIDDEN_ROW_COUNT) * TILE_SIZE, g);
					}
				}
			}
			
			/*
			 * Draw the current piece. This cannot be drawn like the rest of the
			 * pieces because it's still not part of the game board. If it were
			 * part of the board, it would need to be removed every frame which
			 * would just be slow and confusing.
			 */
			Tile type = tetris.getPieceType();
			int pieceCol = tetris.getPieceCol();
			int pieceRow = tetris.getPieceRow();
			int rotation = tetris.getPieceRotation();
			
			//Draw the piece onto the board.
			for(int col = 0; col < type.getDimension(); col++) {
				for(int row = 0; row < type.getDimension(); row++) {
					if(pieceRow + row >= 2 && type.isTile(col, row, rotation)) {
						draw.drawTile(type, (pieceCol + col) * TILE_SIZE, (pieceRow + row - HIDDEN_ROW_COUNT) * TILE_SIZE, g);
					}
				}
			}
			
			/*
			 * Draw the ghost (semi-transparent piece that shows where the current piece will land). I couldn't think of
			 * a better way to implement this so it'll have to do for now. We simply take the current position and move
			 * down until we hit a row that would cause a collision.
			 */
			Color base = type.getBaseColor();
			base = new Color(base.getRed(), base.getGreen(), base.getBlue(), 20);
			for(int lowest = pieceRow; lowest < ROW_COUNT; lowest++) {
				//If no collision is detected, try the next row.
				if(isValidAndEmpty(type, pieceCol, lowest, rotation)) {					
					continue;
				}
				
				//Draw the ghost one row higher than the one the collision took place at.
				lowest--;
				
				//Draw the ghost piece.
				for(int col = 0; col < type.getDimension(); col++) {
					for(int row = 0; row < type.getDimension(); row++) {
						if(lowest + row >= 2 && type.isTile(col, row, rotation)) {
							draw.drawTile(base, base.brighter(), base.darker(), (pieceCol + col) * TILE_SIZE, (lowest + row - HIDDEN_ROW_COUNT) * TILE_SIZE, g);
						}
					}
				}
				
				break;
			}
			
			/*
			 * Draw the background grid above the pieces (serves as a useful visual
			 * for players, and makes the pieces look nicer by breaking them up.
			 */
			g.setColor(Color.DARK_GRAY);
			for(int x = 0; x < COL_COUNT; x++) {
				for(int y = 0; y < VISIBLE_ROW_COUNT; y++) {
					g.drawLine(0, y * TILE_SIZE, COL_COUNT * TILE_SIZE, y * TILE_SIZE);
					g.drawLine(x * TILE_SIZE, 0, x * TILE_SIZE, VISIBLE_ROW_COUNT * TILE_SIZE);
				}
			}
		}
		
		/*
		 * Draw the outline.
		 */
		g.setColor(Color.WHITE);
		g.drawRect(0, 0, TILE_SIZE * COL_COUNT, TILE_SIZE * VISIBLE_ROW_COUNT);
	}
	

}
