package game;

public class Main {
	// main function and will call startgame() in gameController
	public static void main(String[] args) {
		GameController tetris = GameController.getInstance();
		tetris.startGame();
	}
}
