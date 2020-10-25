package gui;

import java.awt.Color;

public class PieceGenerator {
	
	public static final Tile[] piecesCollection = {
		TileI.getInstance(), TileJ.getInstance(), TileL.getInstance(), TileO.getInstance(),
		TileS.getInstance(), TileT.getInstance(), TileZ.getInstance()	  
	};

	private static PieceGenerator instance = null;
	
	public static PieceGenerator getInstance(){
		
		if (instance == null)
			instance = new PieceGenerator();
		
		return instance;
	}
	
	
}
