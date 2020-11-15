package TestGame;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import game.LevelLow;

public class TestLevelLow {
	@Test
	public void test_getTileCnt() {
		
		LevelLow levellow = LevelLow.getInstance();
		int res = levellow.getTileCnt();
		assertEquals( 2, res );
	}
	
	@Test
	public void test_getSpeed() {
		
		LevelLow levellow = LevelLow.getInstance();
		float res = levellow.getSpeed();
		assertEquals( 1.0, res, 0.001 );
	}
	
	@Test
	public void test_getAccelaration() {
		
		LevelLow levellow = LevelLow.getInstance();
		float res = levellow.getAccelaration();
		assertEquals( 0.04, res, 0.001 );
	}
}
