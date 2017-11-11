package algorithms;

import java.util.ArrayList;
import java.util.List;

public class EdgesFromWavefront {
	private int[][] world;
	private Point roboticPosition;
	private Point destination;
	private List<Vertex> vertexes;
	
	public EdgesFromWavefront(int[][] world, Point roboticPosition, Point destination) {
		this.world = world;
		this.roboticPosition = roboticPosition;
		this.destination = destination;
	}
	
	public List<Vertex> getVertexes() {
		if (this.vertexes == null) {
			this.vertexes = new ArrayList<Vertex>();

			for (int i = 0; i < world.length; i++) {
				for (int j = 0; j < world.length; j++) {
					vertexes.add(new Vertex(j * 7 + i + 1));
				}
			}
		}
		
		return this.vertexes;
	}
	
	public Vertex fromPoint(int y, int x) {
		return new Vertex(y * 7 + x + 1);
	}
	
	public Vertex fromPoint(Point p) {
		return fromPoint(p.getY(), p.getX());
	}
	
	public List<Edge> getPath() {
		List<Edge> path = new ArrayList<Edge>();
		
		Point currentPosition = destination;
		
		while (currentPosition != null) {
			int x = currentPosition.getX();
			int y = currentPosition.getY();
			
			List<Point> sides = new ArrayList<>();
			
			sides.add(new Point(x, y + 1));
			sides.add(new Point(x, y - 1));
			sides.add(new Point(x + 1, y));
			sides.add(new Point(x - 1, y));
			
			Point bestPoint = null;
			int bestValue = Integer.MAX_VALUE;
			
			for (Point currentSide : sides) {
				if (isValid(currentSide.getX(), currentSide.getY())) {
					int newValue = world[currentSide.getY()][currentSide.getX()];
					
					if (bestValue > newValue) {
						bestValue = newValue;
						bestPoint = currentSide;
					}
				}
			}
			
			if (bestPoint != null) {
				path.add(new Edge(fromPoint(currentPosition), fromPoint(bestPoint)));
			}
			
			currentPosition = bestPoint;
			
			if (bestValue == 1) {
				break;
			}
		}
		
		return path;
	}

	private boolean isValid(int x, int y) {
		if (x == -1 || y == -1 || x == world.length || y == world.length) {
			return false;
		}
		
		if (world[y][x] == 0 || world[y][x] == -1) {
			return false;
		}

		return true;
	}
}
