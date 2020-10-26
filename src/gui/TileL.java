package gui;

import java.awt.Color;

public class TileL extends Tile {

	public TileL(Color color, int dimension, int cols, int rows, boolean[][] tiles) {
		super(color, dimension, cols, rows, tiles);
	}
	
	private static TileL instance = null;
	
	public static TileL getInstance(){
		
		if (instance == null)
			instance = new TileL(new Color(BoardPanel.COLOR_MAX, 127, BoardPanel.COLOR_MIN), 3, 3, 2, new boolean[][] {
				{
					false,	false,	true,
					true,	true,	true,
					false,	false,	false,
				},
				{
					false,	true,	false,
					false,	true,	false,
					false,	true,	true,
				},
				{
					false,	false,	false,
					true,	true,	true,
					true,	false,	false,
				},
				{
					true,	true,	false,
					false,	true,	false,
					false,	true,	false,
				}
			});
		
		return instance;
	}

}
