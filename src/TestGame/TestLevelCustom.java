package TestGame;

import org.junit.Test;
import static org.junit.Assert.*;
import game.*;

public class TestLevelCustom {

	@Test
	public void test_getTileCnt() {
		
		LevelCustom levelcus = LevelCustom.getInstance();
		int res = levelcus.getTileCnt();
		assertEquals( 2, res );
	}
	
	@Test
	public void test_getSpeed() {
		
		LevelCustom levelcus = LevelCustom.getInstance();
		float res = levelcus.getSpeed();
		assertEquals( 1.0, res, 0.001 );
	}
	
	@Test
	public void test_getAccelaration() {
		
		LevelCustom levelcus = LevelCustom.getInstance();
		float res = levelcus.getAccelaration();
		assertEquals( 0.04, res, 0.001 );
	}

}
