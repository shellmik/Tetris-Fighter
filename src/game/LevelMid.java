package game;

public class LevelMid extends Level {

	private static LevelMid theInstance = new LevelMid();

	public static LevelMid getInstance() {
		return theInstance;
	}

	private LevelMid() {
		super(4, 3.0f, 0.08f);

	}

}
