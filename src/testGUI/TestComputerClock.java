package testGUI;

import static org.junit.Assert.*;

import java.time.Clock;

import org.junit.Test;

import timer.ComputerClock;

public class TestComputerClock {
	@Test
	public void testSetCyclesPerSencond() {
		ComputerClock compClock = ComputerClock.getInstance();
		final long val = compClock.getCurrentTime();
		final long currentTime = System.nanoTime() / 1000000L;
		assertEquals(currentTime, val);
	}
}
