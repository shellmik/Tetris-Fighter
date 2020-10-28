package gui;

public class LevelHigh extends Level{
	
	private static int typeHigh=7;
	private static float speedHigh=1.0f;
	private static float accHigh=0.12f;

	
	private static LevelHigh theInstance = new LevelHigh();
	public static LevelHigh getInstance() {return theInstance;}	
	
	
	public LevelHigh() {
		
		super(typeHigh,speedHigh,accHigh);
		
	}
	
	public int getType() {
		return typeHigh;
	}
	public float getSpeed() {
		return speedHigh;
	}
	public float getAcc() {
		return accHigh;
	}

}
