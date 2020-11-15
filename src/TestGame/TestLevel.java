package TestGame;

import org.junit.Test;
import static org.junit.Assert.*;
import game.*;

public class TestLevel {
	
	@Test
	public void test_getTileCnt_1() {
		
		Level level = new Level(1, 1, 1);
		int res = level.getTileCnt();
		assertEquals(1, res);
	}
	
	@Test
	public void test_getTileCnt_2() {
		
		Level level = new Level(2, 1, 1);
		int res = level.getTileCnt();
		assertEquals(2, res);
	}
	
	@Test
	public void test_getTileCnt_3() {
		
		Level level = new Level(1, 1, 1);
		level.setTileCnt( 3 );
		int res = level.getTileCnt();
		assertEquals(3, res);
	}
	
	@Test
	public void test_getTileCnt_4() {
		
		Level level = new Level(4, 1, 1);
		level.setTileCnt( 2 );
		int res = level.getTileCnt();
		assertEquals(2, res);
	}
	
	@Test
	public void test_getSpeed_1() {
		
		Level level = new Level(1, 0.2f, 1);
		double res = level.getSpeed();
		assertEquals( 0.2 ,res, 0.001 );
	}
	
	@Test
	public void test_getSpeed_2() {
		
		Level level = new Level(1, 0.4f, 1);
		float res = level.getSpeed();
		assertEquals( 0.4 ,res, 0.001 );
	}
	
	@Test
	public void test_getSpeed_3() {
		
		Level level = new Level(1, 0.2f, 1);
		level.setSpeed( 0.6f);
		float res = level.getSpeed();
		assertEquals( 0.6 ,res, 0.001 );
	}
	
	@Test
	public void test_getSpeed_4() {
		
		Level level = new Level(1, 0.2f, 1);
		level.setSpeed( 0.8f);
		float res = level.getSpeed();
		assertEquals( 0.8 ,res, 0.001 );
	}
	
	@Test
	public void test_getAccelaration_1() {
		
		Level level = new Level(1, 0.2f, 0.25f);
		float res = level.getAccelaration();
		assertEquals( 0.25, res, 0.001 );
	}
	
	@Test
	public void test_getAccelaration_2() {
		
		Level level = new Level(1, 0.2f, 0.45f);
		float res = level.getAccelaration();
		assertEquals( 0.45, res, 0.001 );
	}
	
	@Test
	public void test_getAccelaration_3() {
		
		Level level = new Level(1, 0.2f, 1);
		level.setAccelaration( 0.88f );
		float res = level.getAccelaration();
		assertEquals( 0.88, res, 0.001 );
	}
	
	@Test
	public void test_getAccelaration_4() {
		
		Level level = new Level(1, 0.2f, 1);
		level.setAccelaration( 0.79f );
		float res = level.getAccelaration();
		assertEquals( 0.79, res, 0.001 );
	}


}
