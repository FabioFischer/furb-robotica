package algorithms;

import java.util.ArrayList;
import java.util.List;

public class WavefrontDetector {
	private int[][] map;
	
	public WavefrontDetector(int[][] map) {
		this.map = map;
	}
	
	public Point findFrom(Point source) {
		return this.findFrom(source, new ArrayList<Point>());
	}
	
	private Point findFrom(Point source, List<Point> mappeds) {
		List<Point> points = new ArrayList<>();
		
		if (source.getY() - 1 >= 0) {
			int top = map[source.getY() - 1][source.getX()];
			
			points.add(new Point(source.getX(), source.getY() - 1));
					
			if (top == 0) {
				return new Point(source.getX(), source.getY() - 1);
			}
		}
		
		if (source.getY() + 1 <= map.length - 1) {
			int bottom = map[source.getY() + 1][source.getX()];
			
			points.add(new Point(source.getX(), source.getY() + 1));
			
			if (bottom == 0) {
				return new Point(source.getX(), source.getY() + 1);
			}
		}
		
		if (source.getX() - 1 >= 0) {
			int left = map[source.getY()][source.getX() - 1];
			
			points.add(new Point(source.getX() - 1, source.getY()));
			
			if (left == 0) {
				return new Point(source.getX() - 1, source.getY());
			}
		}

		if (source.getY() + 1 <= map.length - 1) {
			int right = map[source.getY()][source.getX() + 1];
			
			points.add(new Point(source.getX() + 1, source.getY()));
			
			if (right == 0) {
				return new Point(source.getX() + 1, source.getY());
			}
		}
		
		for (Point point : points) {
			if (mappeds.contains(point)) continue;
			
			mappeds.add(point);
			
			Point detected = findFrom(point, mappeds);
			
			if (detected != null) {
				return detected;
			}
		}
		
		return null;
	}
}
