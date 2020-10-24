package gui;

public class Level {
	
	private int tile_cnt;
	private float speed;
	private float accelaration;
	
	//singleton
	private static Level theInstance = new Level();
	public static Level getInstance() {return theInstance;}	 
	private Level() {}
	
	int getTileCnt() {
		return this.tile_cnt;	
	}
	
	float getSpeed() {
		return this.speed;
	}

	float getAccelaration() {
		return this.accelaration;
	}
	
	void setTileCnt(int tile_cnt) {
		this.tile_cnt=tile_cnt;
	}
	
	void setSpeed(float speed) {
		this.speed=speed;
	}

	
	void setAccelaration(float accelaration) {
		this.accelaration=accelaration;	
	}

}
