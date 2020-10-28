package gui;

public class LevelMid extends Level{
	private static int typeMid=4;
	private static float speedMid=2.0f;
	private static float accMid=0.06f;

	
	private static LevelMid theInstance = new LevelMid();
	public static LevelMid getInstance() {return theInstance;}	
	
    public LevelMid() {
		
	super(typeMid,speedMid,accMid);
		
	}
	
	public int getType() {
		return typeMid;
	}
	public float getSpeed() {
		return speedMid;
	}
	public float getAcc() {
		return accMid;
	}

	
	

}
