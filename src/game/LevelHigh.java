package game;

public class LevelHigh extends Level {
	// high level 
	private static LevelHigh theInstance = new LevelHigh();

	public static LevelHigh getInstance() {
		return theInstance;
	}

	private LevelHigh() {
		super(7, 5.0f, 0.12f);
	}

}
