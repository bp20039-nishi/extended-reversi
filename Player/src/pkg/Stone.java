package pkg;

public class Stone {
	
	private pkg.Color color = pkg.Color.None;
	private int score = 0;
	
	
	public void setColor(pkg.Color color) {
		this.color = color;
	}
	
	public pkg.Color getColor() {
		return this.color;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return this.score;
	}
	
}