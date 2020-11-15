package testGUI;

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
	
	@Test
	public void testSubmitOperation01() {
		GameController tetris = GameController.getInstance();
		tetris.setGameOver(true);
		SidePanel sp = new SidePanel(tetris);
		sp.submitOperation();
	}
	
	@Test
	public void testSubmitOperation02() {
		GameController tetris = GameController.getInstance();
		tetris.setGameOver(true);
		SidePanel sp = new SidePanel(tetris);
		JTextField textField = new JTextField("test");
		sp.setTextField(textField);
		sp.submitOperation();
	}
	
	@Test
	public void testSubmitOperation03() {
		GameController tetris = GameController.getInstance();
		tetris.setNewGame(true);
		SidePanel sp = new SidePanel(tetris);
		JTextField textField = new JTextField("moreThanTenChar");
		sp.setTextField(textField);
		sp.submitOperation();
	}
	
	@Test
	public void testShowComboBox01() {
		GameController tetris = GameController.getInstance();
		tetris.setNewGame(true);
		SidePanel sp = new SidePanel(tetris);
		sp.showComboBox();
	}
	
	@Test
	public void testShowComboBox() {
		GameController tetris = GameController.getInstance();
		tetris.setNewGame(true);
		SidePanel sp = new SidePanel(tetris);
		sp.endOperation();
	}
	
	
	
}
