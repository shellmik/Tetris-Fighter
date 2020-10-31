package gui;

import java.awt.Color;

public class TileI extends Tile {

	public TileI(Color color, int dimension, int cols, int rows, boolean[][] tiles) {
		super(color, dimension, cols, rows, tiles);
	}

	private static TileI instance = null;

	public static TileI getInstance() {

		if (instance == null)
			instance = new TileI(new Color(BoardPanel.COLOR_MIN, BoardPanel.COLOR_MAX, BoardPanel.COLOR_MAX), 4, 4, 1,
					new boolean[][] {
							{ false, false, false, false, true, true, true, true, false, false, false, false, false,
									false, false, false, },
							{ false, false, true, false, false, false, true, false, false, false, true, false, false,
									false, true, false, },
							{ false, false, false, false, false, false, false, false, true, true, true, true, false,
									false, false, false, },
							{ false, true, false, false, false, true, false, false, false, true, false, false, false,
									true, false, false, } });

		return instance;
	}

}
