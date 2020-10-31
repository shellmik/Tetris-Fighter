package gui;

import java.awt.Color;

public class TileO extends Tile {

	public TileO(Color color, int dimension, int cols, int rows, boolean[][] tiles) {
		super(color, dimension, cols, rows, tiles);
	}

	private static TileO instance = null;

	public static TileO getInstance() {

		if (instance == null)
<<<<<<< HEAD
			instance = new TileO(new Color(BoardPanel.COLOR_MAX, BoardPanel.COLOR_MAX, BoardPanel.COLOR_MIN), 2, 2, 2,
					new boolean[][] { { true, true, true, true, }, { true, true, true, true, },
							{ true, true, true, true, }, { true, true, true, true, } });

=======
			instance = new TileO(new Color(COLOR_MAX, COLOR_MAX, COLOR_MIN), 2, 2, 2, new boolean[][] {
				{
					true,	true,
					true,	true,
				},
				{
					true,	true,
					true,	true,
				},
				{	
					true,	true,
					true,	true,
				},
				{
					true,	true,
					true,	true,
				}
			});
		
>>>>>>> 86e9120... refactor
		return instance;
	}

}
