package game;

import gui.GameController;


public class Main {

	public static void main(String[] args) {
		GameController tetris = GameController.getInstance();
		tetris.startGame();
	}
}
