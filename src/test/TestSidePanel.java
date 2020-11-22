package test;

import static org.junit.Assert.*;

import java.awt.Graphics;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.junit.Test;

import game.GameController;
import game.Level;
import game.LevelCustom;
import gui.SidePanel;

public class TestSidePanel {
	
	@Test
	public void testSetIsCustom() {
		GameController tetris = GameController.getInstance();
		tetris.setGameOver(true);
		SidePanel sp = new SidePanel(tetris);
		sp.setIsCustom(true);
	}
	
	@Test
	public void testLockSetting() {
		GameController tetris = GameController.getInstance();
		tetris.setGameOver(true);
		SidePanel sp = new SidePanel(tetris);
		sp.lockSetting();
	}
	
	@Test
	public void testUnLockSetting() {
		GameController tetris = GameController.getInstance();
		tetris.setGameOver(true);
		SidePanel sp = new SidePanel(tetris);
		sp.unlockSetting();
	}
	
	@Test
	public void testGetUserName() {
		GameController tetris = GameController.getInstance();
		tetris.setGameOver(true);
		SidePanel sp = new SidePanel(tetris);
		String str = sp.getUserName();
		assertEquals(null, str);
	}
	
}
