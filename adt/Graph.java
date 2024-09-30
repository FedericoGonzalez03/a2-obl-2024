package adt;

public interface Graph {

    public interface Edge{
        public int getFrom();
    
        public int getTo();
    
        public int getWeight();
    }

    public void addEdge(int i, int j);
    
    public void addWeightedEdge(int i, int j, int w);
    
    public void removeEdge(int i, int j);
    
    public int vertexCount();
    
    public int edgeCount();
    
    public Iterable<Edge> edges();
    
    public Iterable<Edge> adjacents(int i);
    
    public boolean isAdjacent(int i, int j);
    
    public int getWeight(int i, int j);
}
