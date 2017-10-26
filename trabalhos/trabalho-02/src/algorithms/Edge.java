package algorithms;

public class Edge  {
    private final String id;
    private final Vertex source;
    private final Vertex destination;
    private final int weight;

    public Edge(String id, Vertex source, Vertex destination, int weight) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
    
    public Edge(Vertex source, Vertex destination, int weight) {
        this("Edge_" + source.getId() + "_to_" + destination.getId(), source, destination, weight);
    }

    public String getId() {
        return id;
    }
    
    public Vertex getDestination() {
        return destination;
    }

    public Vertex getSource() {
        return source;
    }
    
    public int getWeight() {
        return weight;
    }
    
    public boolean equals(Object object) {
    	if (object == null || !(object instanceof Edge)) {
    		return false;
    	}
    	
    	Edge other = (Edge) object;
    	return (
    		(this.getSource().equals(other.getSource()) && this.getDestination().equals(other.getDestination()))
    		|| (this.getSource().equals(other.getDestination()) && this.getDestination().equals(other.getSource()))
    	);
    }

    @Override
    public String toString() {
        return "<Edge @source=" + source + " @destination=" + destination + " @weight=" + weight + ">";
    }
}