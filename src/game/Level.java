package game;

public class Level {
	// this class is the difficulty level
	private int tile_cnt;
	private float speed;
	private float acceleration;

	public Level(int typeCnt, float speed, float acceleration) {
		this.tile_cnt = typeCnt;
		this.speed = speed;
		this.acceleration = acceleration;
	}

	public int getTileCnt() {
		return this.tile_cnt;
	}

	public float getSpeed() {
		return this.speed;
	}

	public float getAccelaration() {
		return this.acceleration;
	}

	public void setTileCnt(int tile_cnt) {
		this.tile_cnt = tile_cnt;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void setAccelaration(float accelaration) {
		this.acceleration = accelaration;
	}

}
