package impl;

import adt.Graph;
import adt.Iterable;

public class MatrixGraph implements Graph {

    private class Edge implements Graph.Edge {
        private int from;
        private int to;
        private int weight;

        public int getFrom() {
            return this.from;
        }

        public int getTo() {
            return this.to;
        }

        public int getWeight() {
            return this.weight;
        }
    }
    
    private int[][] adjMatrix;
    private boolean directed = false;
    private int edges = 0;

    public MatrixGraph(int vertexCount) {
        this.adjMatrix = new int[vertexCount][vertexCount];
    }

    public MatrixGraph(int vertexCount, boolean directed) {
        this.adjMatrix = new int[vertexCount + 1][vertexCount + 1];
        this.directed = directed;
    }

    @Override
    public void addEdge(int i, int j) {
        this.edges++;
        this.adjMatrix[i][j] = 1;
        if (!this.directed) this.adjMatrix[j][i] = 1;
    }

    @Override
    public void addWeightedEdge(int i, int j, int w) {
        this.edges++;
        this.adjMatrix[i][j] = w;
        if (!this.directed) this.adjMatrix[j][i] = w;
    }

    @Override
    public void removeEdge(int i, int j) {
        this.edges--;
        this.adjMatrix[i][j] = 0;
        if (!this.directed) this.adjMatrix[j][i] = 0;
    }

    @Override
    public int vertexCount() {
        return this.adjMatrix.length - 1;
    }

    @Override
    public int edgeCount() {
        return this.edges;
    }

    @Override
    public boolean isAdjacent(int i, int j) {
        return this.adjMatrix[i][j] != 0;
    }

    @Override
    public int getWeight(int i, int j) {
        if (!this.isAdjacent(i, j)) throw new RuntimeException("Not an edge");
        return this.adjMatrix[i][j];
    }
    
    public Iterable<Graph.Edge> edges() {
        return null; //TODO: implement
    }

    public Iterable<Graph.Edge> adjacents(int i) {
        return null; //TODO: implement
    }

}
