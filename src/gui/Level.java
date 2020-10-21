package gui;

public class Level {
	private int tile_cnt;
	private float speed;
	private float accelaration;
	
	public Level(int tile_cnt,float speed,float accelaration) {
	 this.tile_cnt=tile_cnt;
	 this.speed=speed;
	 this.accelaration=accelaration;
		
	}
	
	int getTileCnt() {
		return this.tile_cnt;
		
	}
	
	float getSpeed() {
		return this.speed;
		
	}

	
	float getAccelaration() {
		return this.accelaration;
		
	}


}
