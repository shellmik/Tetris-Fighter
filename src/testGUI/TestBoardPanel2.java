package testGUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import org.junit.Test;

import game.GameController;
import gui.BoardPanel;

public class TestBoardPanel2 {
	
	@Test
	public void testPaintComponent02() {
		
		GameController tetris = GameController.getInstance();
		tetris.setPause(false);
		tetris.setNewGame(true);
		BoardPanel bp = new BoardPanel(tetris);
		
		class Test extends JFrame {
			private static final long serialVersionUID = 1L;
			public Test(BoardPanel bp) {
				super("T");
				add(bp, BorderLayout.CENTER);
				setVisible(true);
			}
		}
		Test test = new Test(bp);
		test.repaint();
	}
	
}
