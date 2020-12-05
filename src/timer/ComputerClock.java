package timer;


public class ComputerClock {
	// the class about computer time
	private static ComputerClock computer = new ComputerClock();
	public static ComputerClock getInstance() {
		return computer;
	}
	private ComputerClock() {
		
	}
	
	public final long getCurrentTime() {
		return (System.nanoTime() / 1000000L);
	}

}
