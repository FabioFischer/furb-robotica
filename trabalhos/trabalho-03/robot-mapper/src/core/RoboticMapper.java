package core;

import java.util.ArrayList;
import java.util.List;

import algorithms.DijkstraAlgorithm;
import algorithms.Edge;
import algorithms.EdgesFromWavefront;
import algorithms.Graph;
import algorithms.Point;
import algorithms.Vertex;
import algorithms.WavefrontBuilder;
import algorithms.WavefrontDetector;

public class RoboticMapper {
	private int[][] world;
	private Point position;
	private Direction direction;
	private IRobotic robotic;

	public RoboticMapper(int[][] world, Point position, Direction direction, IRobotic robotic) {
		this.world = world;
		this.position = position;
		this.direction = direction;
		this.robotic = robotic;
	}
	
	public int getWorldLength() {
		int length = this.world.length;
		return length * length;
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	private void movimentaAte(List<Edge> path) {
		for (int i = path.size() - 1; i >= 0; i--) {
			Edge edge = path.get(i);
			Point nextPosition = fromVertex(edge.getSource());
			
			while (true) {
				if (nextPosition.getX() == position.getX()) {
					if (nextPosition.getY() > position.getY()) {
						if (direction == Direction.BACK) {
							break;
						}
						
						if (direction == Direction.LEFT) {
							this.rotateLeft();
							break;
						}
						
						if (direction == Direction.FRONT) {
							this.rotate();
							this.rotate();
							break;
						}
						
						this.rotate();
						break;
					} else {
						if (direction == Direction.FRONT) {
							break;
						}
						
						if (direction == Direction.RIGHT) {
							this.rotateLeft();
							break;
						}
						
						if (direction == Direction.BACK) {
							this.rotate();
							this.rotate();
							break;
						}
						
						this.rotate();
						break;
					}
				} else {
					if (nextPosition.getX() > position.getX()) {
						if (direction == Direction.RIGHT) {
							break;
						}
						
						if (direction == Direction.BACK) {
							this.rotateLeft();
							break;
						}
						
						if (direction == Direction.LEFT) {
							this.rotate();
							this.rotate();
							break;
						}
						
						this.rotate();
						break;
					} else {
						if (direction == Direction.LEFT) {
							break;
						}
						
						if (direction == Direction.FRONT) {
							this.rotateLeft();
							break;
						}
						
						if (direction == Direction.RIGHT) {
							this.rotate();
							this.rotate();
							break;
						}
						
						this.rotate();
						break;
					}
				}
			}

			this.movimentWithValidation();
		}
	}
	
	public void discover(int[][] worldMapped, Point destination) throws InterruptedException {
		WavefrontBuilder builder = new WavefrontBuilder(worldMapped);
		EdgesFromWavefront formWavefront = new EdgesFromWavefront(builder.build(destination), this.position, destination);
		
		movimentaAte(formWavefront.getPath());
	}
	
	private Point fromVertex(Vertex vertex) {
		Integer vertexId = Integer.parseInt(vertex.getId());
				
		return new Point(
			vertexId % 7 - 1,
			vertexId / 7
		);
	}
	
	
	public void rotate() {
		this.robotic.rotate(Direction.RIGHT);
		this.direction = this.nextDirection();
	}
	
	private List<Direction> directions = new ArrayList<Direction>() {{
		add(Direction.RIGHT);
		add(Direction.BACK);
		add(Direction.LEFT);
		add(Direction.FRONT);
	}};

	public Direction nextDirection() {
		int position = directions.indexOf(direction);
		return directions.get((position + 1) % directions.size());
	}
	
	public void rotateLeft() {
		this.robotic.rotate(Direction.LEFT);
		this.direction = this.nextDirectionLeft();
	}

	public Direction nextDirectionLeft() {
		int position = directions.indexOf(direction);
		int directionIndex = (position - 1) % directions.size();
		
		if (directionIndex == -1) {
			directionIndex = directions.size() - 1;
		}
		
		return directions.get(directionIndex);
	}
	
	public Vertex generateVertexFromPoint(Point point) {
		return new Vertex(this.generateIdFromPoint(point));
	}
	
	private String generateIdFromPoint(Point point) {
		return String.valueOf(
			(point.getX() + 1) + (point.getY() * 7)
		);
	}
	
	public void movimentWithValidation() {
		this.robotic.moviment();
		this.position = this.getNextPosition();
	}
	
	public Point getPosition() {
		return this.position;
	}
	
	public Point getNextPosition() {
		return new Point(nextX(this.direction), nextY(this.direction));
	}
	
	public Point getNextPosition(Direction direction) {
		return new Point(nextX(direction), nextY(direction));
	}
	
	private int nextX(Direction direction) {
		if (direction == Direction.RIGHT) {
			return this.position.getX() + 1;
		}
		
		if (direction == Direction.LEFT) {
			return this.position.getX() - 1;
		}
		
		return this.position.getX();
	}
	
	private int nextY(Direction direction) {
		if (direction == Direction.BACK) {
			return this.position.getY() + 1;
		}
		
		if (direction == Direction.FRONT) {
			return this.position.getY() - 1;
		}
		
		return this.position.getY();
	}
}
