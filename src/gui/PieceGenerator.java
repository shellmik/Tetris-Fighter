package gui;

import java.awt.Color;

public class PieceGenerator {
	
	
	public static final Tiles[] piecesCollection = {
			/**
			 * Piece TypeI.
			 */
			TileI.getInstance(), TileJ.getInstance(), TileL.getInstance(), TileO.getInstance(),
			TileSS.getInstance(), TileT.getInstance(), TileZ.getInstance()	 
			  
	};

	private static PieceGenerator instance = null;
	
	public static PieceGenerator getInstance(){
		
		if (instance == null)
			instance = new PieceGenerator();
		
		return instance;
	}
	
	
}
