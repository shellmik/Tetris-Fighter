package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gui.BoardPanel;
import timer.Clock;


public class KeyHandler implements KeyListener{
	//private GameController gc;
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		GameController tetris = GameController.getInstance();
		boolean isGamePaused = tetris.isPaused();
		int dropCooldown = tetris.getCooldown();
		Clock logicTimer = tetris.getClock();
		BoardPanel board = tetris.getBoard();
		PieceController pc = tetris.getPC();
		boolean isNewGame = tetris.get
		boolean isGameOver
		//TODO: can we put this function outside?
		switch (e.getKeyCode()) {

			case KeyEvent.VK_S:
				if (!isGamePaused && dropCooldown == 0) {
					logicTimer.setCyclesPerSecond(25.0f);
				}
				break;

			case KeyEvent.VK_A:
				if (!isGamePaused && board.isValidAndEmpty(pc.getCurrentType(), pc.getCurrentCol() - 1,
						pc.getCurrentRow(), pc.getCurrentRotation())) {
					pc.setCurrentCol(pc.getCurrentCol() - 1);
				}
				break;

			case KeyEvent.VK_D:
				if (!isGamePaused && board.isValidAndEmpty(pc.getCurrentType(), pc.getCurrentCol() + 1,
						pc.getCurrentRow(), pc.getCurrentRotation())) {
					pc.setCurrentCol(pc.getCurrentCol() + 1);
				}
				break;

			case KeyEvent.VK_J:
				if (!isGamePaused) {
					pc.rotatePiece((pc.getCurrentRotation() == 0) ? 3 : pc.getCurrentRotation() - 1);
				}
				break;

			case KeyEvent.VK_K:
				if (!isGamePaused) {
					pc.rotatePiece((pc.getCurrentRotation() == 3) ? 0 : pc.getCurrentRotation() + 1);
				}
				break;

			case KeyEvent.VK_P:
				if (!isGameOver && !isNewGame) {
					isGamePaused = !isGamePaused;
					logicTimer.setPaused(isGamePaused);
				}

				break;

			case KeyEvent.VK_ENTER:
				if (isGameOver || isNewGame) {
					resetGame();
				}
				break;

		}
	
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
