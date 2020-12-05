package game;

import gui.Tile;
import gui.TileI;
import gui.TileJ;
import gui.TileL;
import gui.TileO;
import gui.TileS;
import gui.TileT;
import gui.TileZ;

public class PieceGenerator {
	// this class generate tile
	public static final Tile[] piecesCollection = {
			TileI.getInstance(), TileO.getInstance(), TileJ.getInstance(), TileL.getInstance(), TileT.getInstance(),
			TileS.getInstance(), TileZ.getInstance()
	};

	private static PieceGenerator instance = null;

	public static PieceGenerator getInstance() {
		if (instance == null) {
			instance = new PieceGenerator();
		}
		return instance;
	}

	public Tile getType(int num) {
		return piecesCollection[num];
	}

}
