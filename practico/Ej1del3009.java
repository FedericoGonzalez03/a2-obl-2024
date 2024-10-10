package practico;

import java.util.Scanner;

import adt.Graph;
import impl.MatrixGraph;

public class Ej1del3009 {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int vertexCount = sc.nextInt();
            sc.nextLine();
            int edgeCount = sc.nextInt();
            sc.nextLine();
            Graph g = new MatrixGraph(vertexCount, false);
            for (int i = 0; i < edgeCount; i++) {
                String[] edgeDetails = sc.nextLine().split(" ");
                int from = Integer.parseInt(edgeDetails[0]);
                int to = Integer.parseInt(edgeDetails[1]);
                g.addEdge(from, to);
            }
            int qryCount = sc.nextInt();
            sc.nextLine();
            for (int i = 0; i < qryCount; i++) {
                String[] qryDetails = sc.nextLine().split(" ");
                int from = Integer.parseInt(qryDetails[0]);
                int to = Integer.parseInt(qryDetails[1]);
                int[][] bfs = ((MatrixGraph) g).bfs(from);
                System.out.println(bfs[0][to]);
            }
        }
    }
}
