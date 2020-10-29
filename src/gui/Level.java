package gui;

public class Level {

	private int tile_cnt;
	private float speed;
	private float acceleration;

	// singleton
	// private static Level theInstance = new Level();
	// public static Level getInstance() {return theInstance;}
	public Level(int typeCnt, float speed, float acceleration) {
		this.tile_cnt = typeCnt;
		this.speed = speed;
		this.acceleration = acceleration;
	}

	int getTileCnt() {
		return this.tile_cnt;
	}

	float getSpeed() {
		return this.speed;
	}

	float getAccelaration() {
		return this.acceleration;
	}

	void setTileCnt(int tile_cnt) {
		this.tile_cnt = tile_cnt;
	}

	void setSpeed(float speed) {
		this.speed = speed;
	}

	void setAccelaration(float accelaration) {
		this.acceleration = accelaration;
	}

}
