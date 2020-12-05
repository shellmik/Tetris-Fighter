package timer;

public class Clock {
	// about the time 
	private float millisPerCycle;
	private long lastUpdate;
	private int elapsedCycles;
	private float excessCycles;
	private boolean isPaused;
	private ComputerClock computer = ComputerClock.getInstance();
	
	public Clock(float cyclesPerSecond) {
		setCyclesPerSecond(cyclesPerSecond);
		reset();
	}
	
	public void setCyclesPerSecond(float cyclesPerSecond) {
		this.millisPerCycle = (1.0f / cyclesPerSecond) * 1000;
	}
	// reset all the time
	public void reset() {
		this.elapsedCycles = 0;
		this.excessCycles = 0.0f;
		this.lastUpdate = computer.getCurrentTime();
		this.isPaused = false;
	}
	// update time 
	public void update() {
		long currUpdate = computer.getCurrentTime();
		float delta = (float)(currUpdate - lastUpdate) + excessCycles;
		if(!isPaused) {
			this.elapsedCycles += (int)Math.floor(delta / millisPerCycle);
			this.excessCycles = delta % millisPerCycle;
		}
		this.lastUpdate = currUpdate;
	}
	
	public void setPaused(boolean paused) {
		this.isPaused = paused;
	}
	
	public boolean isPaused() {
		return isPaused;
	}

	public boolean hasElapsedCycle() {
		if(elapsedCycles > 0) {
			this.elapsedCycles--;
			return true;
		}
		return false;
	}
	
	public boolean peekElapsedCycle() {
		return (elapsedCycles > 0);
	}

}
