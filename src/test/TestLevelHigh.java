package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import game.LevelHigh;

public class TestLevelHigh {

	@Test
	public void test_getTileCnt() {
		
		LevelHigh levelhigh = LevelHigh.getInstance();
		int res = levelhigh.getTileCnt();
		assertEquals( 7, res );
	}
	
	@Test
	public void test_getSpeed() {
		
		LevelHigh levelhigh = LevelHigh.getInstance();
		float res = levelhigh.getSpeed();
		assertEquals( 5.0, res, 0.001 );
	}
	
	@Test
	public void test_getAccelaration() {
		
		LevelHigh levelhigh = LevelHigh.getInstance();
		float res = levelhigh.getAccelaration();
		assertEquals( 0.12, res, 0.001 );
	}
}
