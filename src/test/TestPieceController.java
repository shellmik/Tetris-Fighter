package test;

import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.Color;

import game.*;
import gui.Tile;
import gui.TileI;
import gui.TileJ;
import gui.TileL;

public class TestPieceController {
	
	private GameController game = GameController.getInstance();

	private PieceGenerator pg = PieceGenerator.getInstance();
	
	@Test
	public void testSpawnPiece_1() {
		
		PieceController pc = new PieceController();
		pc.currentCol = 3;
		pc.currentRotation = 1;
		pc.currentRow = 4;
		pc.currentType = TileJ.getInstance();
		pc.nextType = TileI.getInstance();
		pc.spawnPiece();
	}
	
	@Test
	public void testSpawnPiece_2() {
		
		PieceController pc = new PieceController();
		pc.currentCol = -10000;
		pc.currentRotation = 1000;
		pc.currentRow = -10000;
		pc.currentType = new Tile(new Color(111, 111, 111), 10000, 10000, 10000,
				new boolean[][] { { true, true, false, false, true, true, false, false, false, },
			{ false, false, true, false, true, true, false, true, false, },
			{ false, false, false, true, true, false, false, true, true, },
			{ false, true, false, true, true, false, true, false, false, } });
		pc.nextType = TileI.getInstance();

		pc.spawnPiece();
	}
	
	@Test
	public void testRotatePiece_1() {
		
		PieceController pc = new PieceController();
		pc.currentCol = 3;
		pc.currentRotation = 1;
		pc.currentRow = 4;
		pc.currentType = TileJ.getInstance();
		pc.nextType = TileI.getInstance();
		pc.rotatePiece(2);
	}
	
	@Test
	public void testRotatePiece_2() {
		
		PieceController pc = new PieceController();
		pc.currentCol = -3;
		pc.currentRotation = 1;
		pc.currentRow = 4;
		pc.currentType = TileJ.getInstance();
		pc.nextType = TileI.getInstance();
		pc.rotatePiece(2);
	}
	
	@Test
	public void testRotatePiece_3() {
		
		PieceController pc = new PieceController();
		pc.currentCol = 10;
		pc.currentRotation = 1;
		pc.currentRow = 4;
		pc.currentType = TileJ.getInstance();
		pc.nextType = TileI.getInstance();
		pc.rotatePiece(2);
	}
	
	@Test
	public void testRotatePiece_4() {
		
		PieceController pc = new PieceController();
		pc.currentCol = 3;
		pc.currentRotation = 1;
		pc.currentRow = -2;
		pc.currentType = TileJ.getInstance();
		pc.nextType = TileI.getInstance();
		pc.rotatePiece(2);
	}
	
	@Test
	public void testRotatePiece_5() {
		
		PieceController pc = new PieceController();
		pc.currentCol = 3;
		pc.currentRotation = 1;
		pc.currentRow = 30;
		pc.currentType = TileJ.getInstance();
		pc.nextType = TileI.getInstance();
		pc.rotatePiece(2);
	}
	
	@Test
	public void testGetCurrentType_1() {
		
		PieceController pc = new PieceController();
		pc.setCurrentType( TileI.getInstance() );
		TileI res = TileI.getInstance();
		assertEquals( pc.getCurrentType(), res);
	}
	
	@Test
	public void testGetCurrentType_2() {
		
		PieceController pc = new PieceController();
		pc.setCurrentType( TileJ.getInstance() );
		
		TileJ res = TileJ.getInstance();
		assertEquals( pc.getCurrentType(), res);
	}
	
	@Test
	public void testGetNextType_1() {
		
		PieceController pc = new PieceController();
		pc.setNextType( TileI.getInstance() );
		TileI res = TileI.getInstance();
		assertEquals( pc.getNextType(), res);
	}
	
	@Test
	public void testGetNextType_2() {
		
		PieceController pc = new PieceController();
		pc.setNextType( TileJ.getInstance() );
		
		TileJ res = TileJ.getInstance();
		assertEquals( pc.getNextType(), res);
	}
	
	@Test
	public void testGetCurCol_1() {
		
		PieceController pc = new PieceController();
		pc.setCurrentCol(6);
		int res = pc.getCurrentCol();
		assertEquals( 6, res);
	}
	
	@Test
	public void testGetCurCol_2() {
		
		PieceController pc = new PieceController();
		pc.currentCol = 3;
		pc.currentRotation = 1;
		pc.currentRow = 4;
		pc.currentType = TileJ.getInstance();
		pc.nextType = TileI.getInstance();
		pc.rotatePiece(2);
		int res = pc.getCurrentCol();
		assertEquals( 3, res);
	}
	
	@Test
	public void testGetCurRow_1() {
		
		PieceController pc = new PieceController();
		pc.setCurrentRow(5);
		int res = pc.getCurrentRow();
		assertEquals( 5, res);
	}
	
	@Test
	public void testGetCurRow_2() {
		
		PieceController pc = new PieceController();
		pc.currentCol = 3;
		pc.currentRotation = 1;
		pc.currentRow = 4;
		pc.currentType = TileJ.getInstance();
		pc.nextType = TileI.getInstance();
		pc.rotatePiece(2);
		int res = pc.getCurrentRow();
		assertEquals( 4, res);
	}
	
	@Test
	public void testGetCurRotation_1() {
		
		PieceController pc = new PieceController();
		pc.currentCol = 3;
		pc.currentRotation = 1;
		pc.currentRow = 4;
		pc.currentType = TileJ.getInstance();
		pc.nextType = TileI.getInstance();
		pc.rotatePiece(2);
		int res = pc.getCurrentRotation();
		assertEquals( 2, res);
	}
	
	@Test
	public void testGetCurRotation_2() {
		
		PieceController pc = new PieceController();
		pc.currentCol = 3;
		pc.currentRotation = 1;
		pc.currentRow = 4;
		pc.currentType = TileJ.getInstance();
		pc.nextType = TileI.getInstance();
		pc.rotatePiece(3);
		int res = pc.getCurrentRotation();
		assertEquals( 3, res);
	}
	
	@Test
	public void testGetTileType_1() {
		
		PieceController pc = new PieceController();
		
		TileL res = TileL.getInstance();
		assertEquals( pc.getTileType(3), res);
	}
	
	@Test
	public void testGetTileType_2() {
		
		PieceController pc = new PieceController();
		
		TileJ res = TileJ.getInstance();
		assertEquals( pc.getTileType(2), res);
	}
	
}
