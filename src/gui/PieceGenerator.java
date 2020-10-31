package gui;

import java.awt.Color;

public class PieceGenerator {

	public static final Tile[] piecesCollection = {

			TileI.getInstance(), TileO.getInstance(), TileJ.getInstance(), TileL.getInstance(), TileT.getInstance(),
			TileS.getInstance(), TileZ.getInstance()

	};

	private static PieceGenerator instance = null;

	public static PieceGenerator getInstance() {

		if (instance == null)
			instance = new PieceGenerator();

		return instance;
	}

<<<<<<< HEAD
=======
	public Tile getType(int num) {
		
		return piecesCollection[num];
	}
	
	
>>>>>>> 86e9120... refactor
}
