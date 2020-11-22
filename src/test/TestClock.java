package test;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import timer.Clock;


public class TestClock {
	
	@Test
	public void testSetCyclesPerSencond() {
		float f = 1;
		Clock clock = new Clock(f);
	}
	
	@Test
	public void testUpdate01() {
		float f = 1;
		Clock clock = new Clock(f);
		clock.update();
	}
	
	@Test
	public void testUpdate02() {
		float f = 1;
		Clock clock = new Clock(f);
		clock.setPaused(true);
		clock.update();
	}
	
	@Test
	public void testSetPaused() {
		float f = 1;
		Clock clock = new Clock(f);
		clock.setPaused(false);
		boolean test_val = clock.isPaused();
		assertEquals(false, test_val);
	}
	
	@Test
	public void testHasElapsedCycle01() {
		float f = 1;
		Clock clock = new Clock(f);
		boolean val = clock.hasElapsedCycle();
		assertEquals(false, val);
	}
	
	@Test
	public void testHasElapsedCycle02() throws InterruptedException {
		float f = 1;
		Clock clock = new Clock(f);
		TimeUnit.SECONDS.sleep(1);
		clock.update();
		boolean val = clock.hasElapsedCycle();
		assertEquals(true, val);
	}
	
	@Test
	public void testPeekElapsedCycle01() {
		float f = 1;
		Clock clock = new Clock(f);
		boolean val = clock.peekElapsedCycle();
		assertEquals(false, val);
	}
	
	@Test
	public void testPeekElapsedCycle02() throws InterruptedException {
		float f = 1;
		Clock clock = new Clock(f);
		TimeUnit.SECONDS.sleep(1);
		clock.update();
		boolean val = clock.peekElapsedCycle();
		assertEquals(true, val);
	}
	
}
