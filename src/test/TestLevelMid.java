package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import game.LevelMid;

public class TestLevelMid {
	@Test
	public void test_getTileCnt() {
		
		LevelMid levelmid = LevelMid.getInstance();
		int res = levelmid.getTileCnt();
		assertEquals( 4, res );
	}
	
	@Test
	public void test_getSpeed() {
		
		LevelMid levelmid = LevelMid.getInstance();
		float res = levelmid.getSpeed();
		assertEquals( 3.0, res, 0.001 );
	}
	
	@Test
	public void test_getAccelaration() {
		
		LevelMid levelmid = LevelMid.getInstance();
		float res = levelmid.getAccelaration();
		assertEquals( 0.08, res, 0.001 );
	}
}
