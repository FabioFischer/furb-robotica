package algorithms;

public class Point {
	private final int x;
	private final int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public boolean equals(Object object) {
		if (object == null || !(object instanceof Point)) {
			return false;
		}
		
		Point other = (Point) object;

		return (
			this.getX() == other.getX() &&
			this.getY() == other.getY()
		);
	}
	
	public String toString() {
		return "X = " + this.getX() + ", Y = " + this.getY();
	}
}
