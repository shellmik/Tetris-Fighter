package test;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.Test;

import game.GameController;
import gui.BoardPanel;
import gui.SidePanel;
import gui.Tile;
import gui.TileI;
import gui.TileL;
import gui.TileO;
import gui.TileZ;

public class TestBoardPanel {
	
	@Test
	public void testIsValidAndEmpty_01() {
		GameController tetris = GameController.getInstance();
		BoardPanel bp = new BoardPanel(tetris);
		Tile tileZ = TileZ.getInstance();
		boolean val = bp.isValidAndEmpty(tileZ, 5, 5, 1);
		assertEquals(true, val);
	}
	
	@Test
	public void testIsValidAndEmpty_02() {
		GameController tetris = GameController.getInstance();
		BoardPanel bp = new BoardPanel(tetris);
		Tile tile = TileL.getInstance();
		boolean val = bp.isValidAndEmpty(tile, -5, 5, 1);
		assertEquals(false, val);
	}
	
	@Test
	public void testIsValidAndEmpty_03() {
		GameController tetris = GameController.getInstance();
		BoardPanel bp = new BoardPanel(tetris);
		Tile tile = TileL.getInstance();
		boolean val = bp.isValidAndEmpty(tile, 500, 5, 1);
		assertEquals(false, val);
	}
	
	@Test
	public void testIsValidAndEmpty_04() {
		GameController tetris = GameController.getInstance();
		BoardPanel bp = new BoardPanel(tetris);
		Tile tile = TileL.getInstance();
		boolean val = bp.isValidAndEmpty(tile, 5, -5, 1);
		assertEquals(false, val);
	}
	
	@Test
	public void testIsValidAndEmpty_05() {
		GameController tetris = GameController.getInstance();
		BoardPanel bp = new BoardPanel(tetris);
		Tile tile = TileL.getInstance();
		boolean val = bp.isValidAndEmpty(tile, 5, 500, 1);
		assertEquals(false, val);
	}
	
	@Test
	public void testIsValidAndEmpty_06() {
		GameController tetris = GameController.getInstance();
		BoardPanel bp = new BoardPanel(tetris);
		Tile tile = TileL.getInstance();
		bp.addPiece(tile, 5, 5, 1);
		boolean val = bp.isValidAndEmpty(tile, 5, 5, 1);
		assertEquals(false, val);
	}
	
	@Test
	public void testIsValidAndEmpty_07() {
		GameController tetris = GameController.getInstance();
		BoardPanel bp = new BoardPanel(tetris);
		Tile tile = TileL.getInstance();
		bp.addPiece(tile, 5, 5, 1);
		bp.clearBoard();
		boolean val = bp.isValidAndEmpty(tile, 5, 5, 1);
		assertEquals(true, val);
	}
	
	@Test
	public void testCheckLines_01() {
		GameController tetris = GameController.getInstance();
		BoardPanel bp = new BoardPanel(tetris);
		int val = bp.checkLines();
		assertEquals(0, val);
	}
	
	@Test
	public void testCheckLines_02() {
		GameController tetris = GameController.getInstance();
		BoardPanel bp = new BoardPanel(tetris);
		Tile tile = TileI.getInstance();
		bp.addPiece(tile, 0, 18, 0);
		bp.addPiece(tile, 3, 18, 0);
		bp.addPiece(tile, 6, 18, 0);
		int val = bp.checkLines();
		assertEquals(1, val);
	}
	
	@Test
	public void testGetCOL_COUNT() {
		GameController tetris = GameController.getInstance();
		BoardPanel bp = new BoardPanel(tetris);
		int col = bp.getCOL_COUNT();
		assertEquals(10, col);
	}
	
	@Test
	public void testGetROW_COUNT() {
		GameController tetris = GameController.getInstance();
		BoardPanel bp = new BoardPanel(tetris);
		int col = bp.getROW_COUNT();
		assertEquals(22, col);
	}
		
}
