package game;

public class User {
	// this is the user class
	private String name;
	private int score;
	private String date;

	public User() {}

	public User(String name, int score, String date) {
		this.name = name;
		this.score = score;
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getScore() {

		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String toString() {
		String info = String.format("%-10s%-6s%-20s", name, score, date);
		return info;
	}

}