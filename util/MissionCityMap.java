package util;


import adt.List;
import impl.LinkedList;
import impl.MatrixGraph;

public class MissionCityMap extends MatrixGraph {

    public class Travel {
        public int cost;
        public List<Integer> path;
        
        public Travel(int cost, List<Integer> path) {
            this.cost = cost;
            this.path = path;
        }
    }

    int[][] safeTravels = null;
    
    public MissionCityMap(int vertexCount) {
        super(vertexCount);
        safeTravels = new int[super.vertexCount() + 1][this.vertexCount() + 1];
    }
    
    public MissionCityMap(int vertexCount, boolean directed) {
        super(vertexCount, directed);
        safeTravels = new int[super.vertexCount() + 1][this.vertexCount() + 1];
    }

    public Travel goToCity(int start, int end) {
        int[][] dijkstra = this.dijkstra(start);
        int[] pathsStart = dijkstra[1];

        int current = end;
        List<Integer> path = new LinkedList<>();
        while (current != start) {
            int previous = pathsStart[current];
            if (this.safeTravels[previous][current] == 0) {
                this.addWeightedEdge(previous, current, this.getWeight(previous, current) * 2);
                this.safeTravels[previous][current] = 1;
            }
            path.add(0, current);
            current = previous;
        }
        path.add(0, start);

        return new Travel(dijkstra[0][end], path);
    }
}