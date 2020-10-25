package gui;

import java.awt.Color;

public class TileT extends Tiles {

	public TileT(Color color, int dimension, int cols, int rows, boolean[][] tiles) {
		super(color, dimension, cols, rows, tiles);
	}
	
	private static TileT instance = null;
	
	public static TileT getInstance(){
		
		if (instance == null)
			instance = new TileT(new Color(128, BoardPanel.COLOR_MIN, 128), 3, 3, 2, new boolean[][] {
				{
					false,	true,	false,
					true,	true,	true,
					false,	false,	false,
				},
				{
					false,	true,	false,
					false,	true,	true,
					false,	true,	false,
				},
				{
					false,	false,	false,
					true,	true,	true,
					false,	true,	false,
				},
				{
					false,	true,	false,
					true,	true,	false,
					false,	true,	false,
				}
			});
		
		return instance;
	}

}
