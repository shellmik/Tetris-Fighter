package test;

import static org.junit.Assert.*;
import java.awt.Color;

import org.junit.Test;

import gui.Tile;

public class TestTile {
	
	@Test
	public void testIsTile() {
		Tile tile = new Tile(new Color(120, 150, 150), 2, 2, 2,
				new boolean[][] { { true, true, true, true, }, { true, true, true, true, },
			{ true, true, true, true, }, { true, true, true, true, } });
		boolean res = tile.isTile(0, 0, 0);
		assertEquals(true, res);
	}
	
	@Test
	public void testGetLeftInset01() {
		Tile tile = new Tile(new Color(120, 150, 150), 2, 2, 2,
				new boolean[][] { { true, true, true, true, }, { true, true, true, true, },
			{ true, true, true, true, }, { true, true, true, true, } });
		int res = tile.getLeftInset(0);
		assertEquals(0, res);
	}
	
	@Test
	public void testGetLeftInset02() {
		Tile tile = new Tile(new Color(120, 150, 150), 0, 2, 2,
				new boolean[][] { { true, true, true, true, }, { true, true, true, true, },
			{ true, true, true, true, }, { true, true, true, true, } });
		int res = tile.getLeftInset(0);
		assertEquals(-1, res);
	}
	
	@Test
	public void testGetLeftInset03() {
		Tile tile = new Tile(new Color(120, 150, 150), 1, 3, 2,
				new boolean[][] { { false, true, true, true, true, false, false, false, false, },
			{ false, true, false, false, true, true, false, false, true, },
			{ false, false, false, false, true, true, true, true, false, },
			{ true, false, false, true, true, false, false, true, false, } });
		int res = tile.getLeftInset(0);
		assertEquals(-1, res);
	}
	
	@Test
	public void testGetRightInset01() {
		Tile tile = new Tile(new Color(120, 150, 150), 2, 2, 2,
				new boolean[][] { { true, true, true, true, }, { true, true, true, true, },
			{ true, true, true, true, }, { true, true, true, true, } });
		int res = tile.getRightInset(0);
		assertEquals(1, res);
	}
	
	@Test
	public void testGetRightInset02() {
		Tile tile = new Tile(new Color(120, 150, 150), 0, 2, 2,
				new boolean[][] { { true, true, true, true, }, { true, true, true, true, },
			{ true, true, true, true, }, { true, true, true, true, } });
		int res = tile.getRightInset(0);
		assertEquals(-1, res);
	}
	
	@Test
	public void testGetRightInset03() {
		Tile tile = new Tile(new Color(120, 150, 150), 1, 3, 2,
				new boolean[][] { { false, true, true, true, true, false, false, false, false, },
			{ false, true, false, false, true, true, false, false, true, },
			{ false, false, false, false, true, true, true, true, false, },
			{ true, false, false, true, true, false, false, true, false, } });
		int res = tile.getRightInset(0);
		assertEquals(-1, res);
	}
	
	@Test
	public void testGetBottomInset01() {
		Tile tile = new Tile(new Color(120, 150, 150), 1, 3, 2,
				new boolean[][] { { false, true, true, true, true, false, false, false, false, },
			{ false, true, false, false, true, true, false, false, true, },
			{ false, false, false, false, true, true, true, true, false, },
			{ true, false, false, true, true, false, false, true, false, } });
		int res = tile.getBottomInset(0);
		assertEquals(-1, res);
	}
	
	@Test
	public void testGetBottomInset02() {
		Tile tile = new Tile(new Color(120, 150, 150), 0, 2, 2,
				new boolean[][] { { true, true, true, true, }, { true, true, true, true, },
			{ true, true, true, true, }, { true, true, true, true, } });
		int res = tile.getBottomInset(0);
		assertEquals(-1, res);
	}
	
	@Test
	public void testGetBottomInset03() {
		Tile tile = new Tile(new Color(120, 150, 150), 2, 2, 2,
				new boolean[][] { { true, true, true, true, }, { true, true, true, true, },
			{ true, true, true, true, }, { true, true, true, true, } });
		int res = tile.getBottomInset(0);
		assertEquals(1, res);
	}
	
	@Test
	public void testGetBaseColor() {
		Tile tile = new Tile(new Color(120, 150, 150), 2, 2, 2,
				new boolean[][] { { true, true, true, true, }, { true, true, true, true, },
			{ true, true, true, true, }, { true, true, true, true, } });
		Color res = tile.getBaseColor();
		assertEquals(new Color(120, 150, 150), res);
	}
	
	@Test
	public void testDimension() {
		Tile tile = new Tile(new Color(120, 150, 150), 2, 2, 2,
				new boolean[][] { { true, true, true, true, }, { true, true, true, true, },
			{ true, true, true, true, }, { true, true, true, true, } });
		int res = tile.getDimension();
		assertEquals(2, res);
	}
	
	@Test
	public void testSpawnColumn() {
		Tile tile = new Tile(new Color(120, 150, 150), 2, 2, 2,
				new boolean[][] { { true, true, true, true, }, { true, true, true, true, },
			{ true, true, true, true, }, { true, true, true, true, } });
		int res = tile.getSpawnColumn();
		assertEquals(4, res);
	}
	
	@Test
	public void testSpawnRow() {
		Tile tile = new Tile(new Color(120, 150, 150), 2, 2, 2,
				new boolean[][] { { true, true, true, true, }, { true, true, true, true, },
			{ true, true, true, true, }, { true, true, true, true, } });
		int res = tile.getSpawnRow();
		assertEquals(0, res);
	}
	
	@Test
	public void testRows() {
		Tile tile = new Tile(new Color(120, 150, 150), 2, 2, 2,
				new boolean[][] { { true, true, true, true, }, { true, true, true, true, },
			{ true, true, true, true, }, { true, true, true, true, } });
		int res = tile.getRows();
		assertEquals(2, res);
	}
	
	@Test
	public void testCols() {
		Tile tile = new Tile(new Color(120, 150, 150), 2, 2, 2,
				new boolean[][] { { true, true, true, true, }, { true, true, true, true, },
			{ true, true, true, true, }, { true, true, true, true, } });
		int res = tile.getCols();
		assertEquals(2, res);
	}
}
