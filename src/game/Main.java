package game;

public class Main {

	public static void main(String[] args) {
		GameController tetris = GameController.getInstance();
		tetris.startGame();
	}
}
