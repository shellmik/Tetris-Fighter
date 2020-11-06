package gui;

import java.awt.Color;

public class TileO extends Tile {

	private static TileO instance = null;

	public static TileO getInstance() {

		if (instance == null)
			instance = new TileO(new Color(COLOR_MAX, COLOR_MAX, COLOR_MIN), 2, 2, 2,
					new boolean[][] { { true, true, true, true, }, { true, true, true, true, },
							{ true, true, true, true, }, { true, true, true, true, } });

		return instance;
	}
	
	private TileO(Color color, int dimension, int cols, int rows, boolean[][] tiles) {
		super(color, dimension, cols, rows, tiles);
	}

}
