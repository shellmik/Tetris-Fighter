package gui;

public class LevelMid extends Level{
	
	private static LevelMid theInstance = new LevelMid();
	public static LevelMid getInstance() {return theInstance;}	
	public LevelMid() {
		super(4,2.0f,0.06f);
		
	}

}
