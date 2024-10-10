package impl;

import adt.Graph;
import adt.Iterable;
import adt.Iterator;
import adt.List;
import adt.PriorityQueue;
import adt.Queue;

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

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }
    
    private int[][] adjMatrix;
    private boolean directed = false;
    private int edges = 0;

    public MatrixGraph(int vertexCount) {
        this.adjMatrix = new int[vertexCount + 1][vertexCount + 1];
    }

    public MatrixGraph(int vertexCount, boolean directed) {
        this.adjMatrix = new int[vertexCount + 1][vertexCount + 1];
        this.directed = directed;
    }

    @Override
    public void addEdge(int i, int j) {
        if (!isAdjacent(i, j)) this.edges++;
        this.adjMatrix[i][j] = 1;
        if (!this.directed) this.adjMatrix[j][i] = 1;
    }

    @Override
    public void addWeightedEdge(int i, int j, int w) {
        if (!isAdjacent(i, j)) this.edges++;
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
        List<Graph.Edge> list = new LinkedList<>();
        for (int i = 1; i < this.adjMatrix.length; i++) {
            if (this.directed) {
                for (int j = 1; j < this.adjMatrix.length; j++) {
                    if (isAdjacent(i, j)) list.add(new Edge(i, j, getWeight(i, j)));
                }
            } else {
                for (int j = i; j < this.adjMatrix.length; j++) {
                    if (isAdjacent(i, j)) list.add(new Edge(i, j, getWeight(i, j)));
                }
            }
        }
        return list;
    }

    public Iterable<Graph.Edge> adjacents(int i) {
        List<Graph.Edge> list = new LinkedList<>();
        for (int j = 1; j < this.adjMatrix.length; j++) {
            if (isAdjacent(i, j)) list.add(new Edge(i, j, getWeight(i, j)));
        }
        return list;
    }

    public int[][] bfs(int o) {
        boolean[] visited = new boolean[this.adjMatrix.length];
        int[] cost = new int[this.adjMatrix.length];
        for (int i = 0; i < cost.length; i++) if (i != o) cost[i] = Integer.MAX_VALUE;
        int[] prev = new int[this.adjMatrix.length];
        for (int i = 0; i < prev.length; i++) prev[i] = -1;
        Queue<Integer> queue = new ListQueue<>();
        queue.insert(o);
        while (!queue.isEmpty()) {
            int i = queue.pop();
            if (visited[i]) continue;
            visited[i] = true;
            Iterator<Graph.Edge> adjs = this.adjacents(i).iterator();
            while (adjs.hasNext()) {
                int j = adjs.next().getTo();
                if (cost[i] == Integer.MAX_VALUE) continue;
                int newCost = cost[i] + this.adjMatrix[i][j];
                if (!visited[j] && newCost < cost[j]) {
                    cost[j] = newCost;
                    prev[j] = i;
                    queue.insert(j);
                }
            }
        }
        int[][] ret = new int[2][this.adjMatrix.length];
        ret[0] = cost;
        ret[1] = prev;
        return ret;
    }

    public int[][] dijkstra(int o) {
        boolean[] visited = new boolean[this.adjMatrix.length];
        int[] cost = new int[this.adjMatrix.length];
        for (int i = 0; i < cost.length; i++) if (i != o) cost[i] = Integer.MAX_VALUE;
        int[] prev = new int[this.adjMatrix.length];
        for (int i = 0; i < prev.length; i++) prev[i] = -1;
        PriorityQueue<Integer, Integer> queue = new MinHeapPriorityQueue<>(this.adjMatrix.length*2);
        queue.insert(o, 0);
        while (!queue.isEmpty()) {
            int i = queue.pop();
            if (visited[i]) continue;
            visited[i] = true;
            Iterator<Graph.Edge> adjs = this.adjacents(i).iterator();
            while (adjs.hasNext()) {
                int j = adjs.next().getTo();
                if (cost[i] == Integer.MAX_VALUE) continue;
                int newCost = cost[i] + this.adjMatrix[i][j];
                if (!visited[j] && newCost < cost[j]) {
                    cost[j] = newCost;
                    prev[j] = i;
                    queue.insert(j, newCost);
                }
            }
        }
        int[][] ret = new int[2][this.adjMatrix.length];
        ret[0] = cost;
        ret[1] = prev;
        return ret;
    }

}
