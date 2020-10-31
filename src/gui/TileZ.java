package gui;

import java.awt.Color;

public class TileZ extends Tile {

	public TileZ(Color color, int dimension, int cols, int rows, boolean[][] tiles) {
		super(color, dimension, cols, rows, tiles);
	}

	private static TileZ instance = null;

	public static TileZ getInstance() {

		if (instance == null)
<<<<<<< HEAD
			instance = new TileZ(new Color(BoardPanel.COLOR_MAX, BoardPanel.COLOR_MIN, BoardPanel.COLOR_MIN), 3, 3, 2,
					new boolean[][] { { true, true, false, false, true, true, false, false, false, },
							{ false, false, true, false, true, true, false, true, false, },
							{ false, false, false, true, true, false, false, true, true, },
							{ false, true, false, true, true, false, true, false, false, } });

=======
			instance = new TileZ(new Color(COLOR_MAX, COLOR_MIN, COLOR_MIN), 3, 3, 2, new boolean[][] {
				{
					true,	true,	false,
					false,	true,	true,
					false,	false,	false,
				},
				{
					false,	false,	true,
					false,	true,	true,
					false,	true,	false,
				},
				{
					false,	false,	false,
					true,	true,	false,
					false,	true,	true,
				},
				{
					false,	true,	false,
					true,	true,	false,
					true,	false,	false,
				}
			});
		
>>>>>>> 86e9120... refactor
		return instance;
	}

}
