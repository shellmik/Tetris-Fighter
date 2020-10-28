package gui;

public class LevelLow extends Level{
	private static int typeLow=2;
	private static float speedLow=5.0f;
	private static float accLow=0.06f;


    private static LevelLow theInstance = new LevelLow();
	public static LevelLow getInstance() {return theInstance;}	

	
    public LevelLow() {
		
	super(typeLow,speedLow,accLow);
		
	}
	
	public int getType() {
		return typeLow;
	}
	public float getSpeed() {
		return speedLow;
	}
	public float getAcc() {
		return accLow;
	}

	
	

}
