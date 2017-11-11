package algorithms;

import java.util.*;

public class WavefrontBuilder {
	private int[][] world;
	
	public WavefrontBuilder(int[][] world) {
		this.world = world;
	}
	
	public int[][] build(Point destination) {
		int[][] newWorld = new int[world.length][world.length];

		for (int i = 0; i < world.length; i++) {
			for (int j = 0; j < world.length; j++) {
				newWorld[i][j] = world[i][j];
			}
		}
		
		return build(destination, this.roboticPosition(), newWorld);
	}
	
	public int[][] build(Point destination, Point currentPosition, int[][] newWorld) {
		int x = currentPosition.getX();
		int y = currentPosition.getY();
		
		List<Point> stack = new ArrayList<Point>();
		
		stack.add(new Point(x, y));
		
		while (!stack.isEmpty()) {
			Point currentPoint = stack.remove(0);

			x = currentPoint.getX();
			y = currentPoint.getY();
			
			if (!isValid(x, y, destination, newWorld)) {
				continue;
			}

			if (populate(x, y + 1, newWorld[y][x] + 1, destination, newWorld)) {
				stack.add(new Point(x, y + 1));
			}

			if (populate(x, y - 1, newWorld[y][x] + 1, destination, newWorld)) {
				stack.add(new Point(x, y - 1));
			}

			if (populate(x + 1, y, newWorld[y][x] + 1, destination, newWorld)) {
				stack.add(new Point(x + 1, y));
			}

			if (populate(x - 1, y, newWorld[y][x] + 1, destination, newWorld)) {
				stack.add(new Point(x - 1, y));
			}
		}
		
		return newWorld;
	}

	private boolean isValid(int x, int y, Point pointExcept, int[][] newWorld) {
		if (x == -1 || y == -1 || x == newWorld.length || y == newWorld.length) {
			return false;
		}

		return true;
	}
	
	private boolean populate(int x, int y, int value, Point pointExcept, int[][] newWorld) {
		if (x == -1 || y == -1 || x == newWorld.length || y == newWorld.length) {
			return false;
		}
		
		if (newWorld[y][x] != 0) {
			return false;
		}
		
		if (x == pointExcept.getX() && y == pointExcept.getY()) {
			return false;
		}
		
		newWorld[y][x] = value;
		return true;
	}
	
	public Point roboticPosition() {
		for (int y = 0; y < world.length; y++) {
			for (int x = 0; x < world.length; x++) {
				if (world[y][x] == 1) {
					return new Point(x, y);
				}
			}
		}
		
		return null;
	}
}
