package gui;

public class LevelCustom extends Level {

	private static LevelCustom theInstance = new LevelCustom();

	public static LevelCustom getInstance() {
		return theInstance;
	}

	public LevelCustom() {

		super(2, 1.0f, 0.04f);

	}

}
