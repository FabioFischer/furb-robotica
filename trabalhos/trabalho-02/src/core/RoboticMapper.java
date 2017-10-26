package core;

import java.util.ArrayList;
import java.util.List;

import algorithms.DijkstraAlgorithm;
import algorithms.Edge;
import algorithms.Graph;
import algorithms.Point;
import algorithms.Vertex;
import algorithms.WavefrontDetector;

public class RoboticMapper {
	private int[][] world;
	private Point position;
	private Direction direction;
	private ISensorWall sensorWall;
	private List<Edge> edges;
	private Vertex[][] vertexes;
	private List<Vertex> vertexesAsList;
	private WavefrontDetector detector;
	private IRobotic robotic;
	private int totalMapped = 0;
	private boolean foiProComeco = false;
	private int pegouProdutos = 0;
	private List<Point> products = new ArrayList<>();
	private List<Edge> walls;

	public RoboticMapper(int[][] world, Point position, Direction direction, ISensorWall sensorWall, IRobotic robotic) {
		this.world = world;
		this.position = position;
		this.direction = direction;
		this.sensorWall = sensorWall;
		this.robotic = robotic;
		this.vertexes = new Vertex[world.length][world.length];
		this.vertexesAsList = new ArrayList<>();
		this.edges = new ArrayList<>();
		this.walls = new ArrayList<>();
		this.detector = new WavefrontDetector(world);
		
		for (int x = 0; x < world.length; x++) {
			for (int y = 0; y < world.length; y++) {
				Vertex vertex = new Vertex(this.generateIdFromPoint(new Point(x, y)));
				vertexesAsList.add(vertex);
				this.vertexes[y][x] = vertex;
			}
		}
		
		// Fixo porque não foi possível mapear a cor amarela direito
		products.add(new Point(3, 6)); // azul
		products.add(new Point(1, 3)); // verde
		products.add(new Point(2, 3)); // azul forte
		products.add(new Point(3, 3)); // rosa
		products.add(new Point(3, 2)); // amarelo
		products.add(new Point(6, 3)); // vermelho
	}
	
	public int getWorldLength() {
		int length = this.world.length;
		return length * length;
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	private void movimentaAte(Point point) {
		List<Vertex> path = this.getPath(point);
		
		for (int i = 1; i < path.size(); i++) {
			Vertex nextVertex = path.get(i);
			Point nextPosition = fromVertex(nextVertex);
			
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
			

			System.out.println(this.position);
			System.out.println(direction);

			this.movimentWithValidation();
		}
	}
	
	public void discover() throws InterruptedException {
		if (totalMapped == this.getWorldLength()) {
			if (!foiProComeco) {
				this.movimentaAte(new Point(0, 0));
				foiProComeco = true;
			} else if (pegouProdutos <= products.size()) {
//				for (Point product : products) {
				this.movimentaAte(products.get(pegouProdutos));
//				}
				
				pegouProdutos++;
			}
			
			return;
		}
		
		this.map();
		
		if (totalMapped == this.getWorldLength()) {
			return;
		}
		
		if (!this.moviment()) {
			if (!this.hasNextPositionInvalid(this.getNextPosition(this.nextDirection())) &&
				!this.hasWall(Direction.RIGHT, this.getNextPosition(this.nextDirection()))) {
				this.rotate();
				this.map();
				
				if (totalMapped == this.getWorldLength()) {
					return;
				}
				this.moviment();
			} else if (!this.hasNextPositionInvalid(this.getNextPosition(this.nextDirectionLeft()))
				&& !this.hasWall(Direction.LEFT, this.getNextPosition(this.nextDirectionLeft()))) {
				this.rotateLeft();
				this.map();
				
				if (totalMapped == this.getWorldLength()) {
					return;
				}
				
				this.moviment();
			} else {
				Point next = this.nextPointNotMapped();
				this.movimentaAte(next);
//				List<Vertex> path = this.getPath(next);
//				
//				for (int i = 1; i < path.size(); i++) {
//					Vertex nextVertex = path.get(i);
//					Point nextPosition = fromVertex(nextVertex);
//					
//					while (true) {
//						if (nextPosition.getX() == position.getX()) {
//							if (nextPosition.getY() > position.getY()) {
//								if (direction == Direction.BACK) {
//									break;
//								}
//								
//								if (direction == Direction.LEFT) {
//									this.rotateLeft();
//									break;
//								}
//							} else {
//								if (direction == Direction.FRONT) {
//									break;
//								}
//								
//								if (direction == Direction.RIGHT) {
//									this.rotateLeft();
//									break;
//								}
//							}
//						} else {
//							if (nextPosition.getX() > position.getX()) {
//								if (direction == Direction.RIGHT) {
//									break;
//								}
//								
//								if (direction == Direction.BACK) {
//									this.rotateLeft();
//									break;
//								}
//							} else {
//								if (direction == Direction.LEFT) {
//									break;
//								}
//								
//								if (direction == Direction.FRONT) {
//									this.rotateLeft();
//									break;
//								}
//							}
//						}
//						
//						this.rotate();
//					}
//
//					this.movimentWithValidation();
//				}
			}
		}
	}
	
	private Point fromVertex(Vertex vertex) {
		Integer vertexId = Integer.parseInt(vertex.getId());
				
		return new Point(
			vertexId % 7 - 1,
			vertexId / 7
		);
	}
	
	private Point nextPointNotMapped() {
		return detector.findFrom(this.getPosition());
	}
	
	private List<Vertex> getPath(Point destination) {
		List<Edge> edgesBidirectional = new ArrayList<>();
		
		for (Edge edge : this.getEdges()) {
			edgesBidirectional.add(edge);
			edgesBidirectional.add(new Edge(edge.getDestination(), edge.getSource(), 1));
		}

		Graph graph = new Graph(this.getVertexes(), edgesBidirectional);
		DijkstraAlgorithm algorithm = new DijkstraAlgorithm(graph);
		
		return algorithm.getPath(
			this.generateVertexFromPoint(this.position),
			this.generateVertexFromPoint(destination)
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
	
	public List<Vertex> getVertexes() {
		return this.vertexesAsList;
	}
	
	public List<Edge> getEdges() {
		return this.edges;
	}
	
	public boolean hasWall(Direction direction, Point other) {
		other = other == null ? this.getNextPosition() : other;
		Edge edge = null;
		
		if (!this.isPointInvalid(other)) {
			Vertex vertexSource = this.vertexes[this.position.getY()][this.position.getX()];
			Vertex vertexDestination = this.vertexes[other.getY()][other.getX()];
	
			edge = new Edge(
				vertexSource,
				vertexDestination,
				1
			);
			
			if (this.walls.contains(edge)) {
				return true;
			}
		}
		
		boolean hasWall = sensorWall.detect(direction);
		
		if (hasWall && edge != null) {
			this.walls.add(edge);
		}
		
		return hasWall;
	}
	
	private void addEdge(Point source, Point destination) {
		Vertex vertexSource = this.vertexes[source.getY()][source.getX()];
		Vertex vertexDestination = this.vertexes[destination.getY()][destination.getX()];

		Edge edge = new Edge(
			vertexSource,
			vertexDestination,
			1
		);
		
//		if (!edges.contains(edge)) {
			edges.add(edge);
//		}
	}
	
	private boolean hasEdge(Point source, Point destination) {
		Vertex vertexSource = this.vertexes[source.getY()][source.getX()];
		Vertex vertexDestination = this.vertexes[destination.getY()][destination.getX()];

		Edge edge = new Edge(
			vertexSource,
			vertexDestination,
			1
		);
		
		return edges.contains(edge);
	}
	
	public void map() {
		if (!this.isPointInvalid(this.getNextPosition()) &&
			!this.hasEdge(this.position, this.getNextPosition()) &&
			!this.hasWall(Direction.FRONT, this.getNextPosition())) {
			this.addEdge(this.position, this.getNextPosition());
		}
		
		if (!this.isPointInvalid(this.getNextPosition(this.nextDirection())) &&
			!this.hasEdge(this.position, this.getNextPosition(this.nextDirection())) &&
			!this.hasWall(Direction.RIGHT, this.getNextPosition(this.nextDirection()))) {
			this.addEdge(this.position, this.getNextPosition(this.nextDirection()));
		}
		
		if (!this.isPointInvalid(this.getNextPosition(this.nextDirectionLeft())) &&
			!this.hasEdge(this.position, this.getNextPosition(this.nextDirectionLeft())) &&
			!this.hasWall(Direction.LEFT, this.getNextPosition(this.nextDirectionLeft()))) {
			this.addEdge(this.position, this.getNextPosition(this.nextDirectionLeft()));
		}
		
		if (this.world[this.position.getY()][this.position.getX()] == 0) {
			this.totalMapped++;
		}
		this.world[this.position.getY()][this.position.getX()] = 1;
	}
	
	public Vertex generateVertexFromPoint(Point point) {
		return new Vertex(this.generateIdFromPoint(point));
	}
	
	private String generateIdFromPoint(Point point) {
		return String.valueOf(
			(point.getX() + 1) + (point.getY() * 7)
		);
	}
	
	public boolean moviment() {
		if (this.hasWall(Direction.FRONT, null)) {
			return false;
		}
		
		if (this.hasNextPositionInvalid()) {
			return false;
		}
		
		this.movimentWithValidation();
		return true;
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
	
	private boolean hasNextPositionInvalid() {
		Point nextPosition = this.getNextPosition();
		return this.hasNextPositionInvalid(nextPosition);
	}
	
	private boolean hasNextPositionInvalid(Point nextPosition) {
		return (
			isPointInvalid(nextPosition) ||
			world[nextPosition.getY()][nextPosition.getX()] == 1
		);
	}
	
	private boolean isPointInvalid(Point point) {
		return (
			point.getX() == -1 ||
			point.getX() == world.length ||
			point.getY() == -1 ||
			point.getY() == world.length
		);
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
	
	public Edge getLastEdge() {
		return this.edges.get(this.edges.size() - 1);
	}
}
