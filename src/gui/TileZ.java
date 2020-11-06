package gui;

import java.awt.Color;

public class TileZ extends Tile {

	private static TileZ instance = null;

	public static TileZ getInstance() {

		if (instance == null)
			instance = new TileZ(new Color(COLOR_MAX, COLOR_MIN, COLOR_MIN), 3, 3, 2,
					new boolean[][] { { true, true, false, false, true, true, false, false, false, },
							{ false, false, true, false, true, true, false, true, false, },
							{ false, false, false, true, true, false, false, true, true, },
							{ false, true, false, true, true, false, true, false, false, } });

		return instance;
	}


	private TileZ(Color color, int dimension, int cols, int rows, boolean[][] tiles) {
		super(color, dimension, cols, rows, tiles);
	}
}
