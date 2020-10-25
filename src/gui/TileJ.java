package gui;

import java.awt.Color;

public class TileJ extends Tiles {

	public TileJ(Color color, int dimension, int cols, int rows, boolean[][] tiles) {
		super(color, dimension, cols, rows, tiles);
	}
	
	private static TileJ instance = null;
	
	public static TileJ getInstance(){
		
		if (instance == null)
			instance = new TileJ(new Color(BoardPanel.COLOR_MIN, BoardPanel.COLOR_MIN, BoardPanel.COLOR_MAX), 3, 3, 2, new boolean[][] {
				{
					true,	false,	false,
					true,	true,	true,
					false,	false,	false,
				},
				{
					false,	true,	true,
					false,	true,	false,
					false,	true,	false,
				},
				{
					false,	false,	false,
					true,	true,	true,
					false,	false,	true,
				},
				{
					false,	true,	false,
					false,	true,	false,
					true,	true,	false,
				}
			});
		
		return instance;
	}

}
