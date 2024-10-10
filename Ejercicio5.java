import java.util.Scanner;

import adt.Graph;
import adt.Iterator;
import adt.Stack;
import impl.ListStack;
import impl.MatrixGraph;
import util.Mission;

public class Ejercicio5 {

    static public boolean[] visitedInDfs = null;
    static public Stack<Integer> missionStack = new ListStack<>();

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int missionCount = sc.nextInt();
            sc.nextLine();
            Mission[] missions = new Mission[missionCount + 1];
            Graph missionDeps = new MatrixGraph(missionCount, true);
            for (int i = 0; i < missionCount; i++) {
                String[] missionDetails = sc.nextLine().split(" ");
                int missionId = Integer.parseInt(missionDetails[0]);
                String missionName = missionDetails[1];
                int missionCityId = Integer.parseInt(missionDetails[2]);
                
                missions[missionId] = new Mission(missionId, missionName, missionCityId);                

                int currDep;
                int j = 3;
                while ((currDep = Integer.parseInt(missionDetails[j])) != 0) {
                    missionDeps.addEdge(missionId, currDep);
                    j++;
                }
            }
            
            String[] cities = null;
            
            String[] cityCountAndOrigin = sc.nextLine().split(" ");
            int cityCount = Integer.parseInt(cityCountAndOrigin[0]);
            cities = new String[cityCount + 1];
            int origin = Integer.parseInt(cityCountAndOrigin[1]);
            
            for (int i = 0; i < cityCount; i++) {
                String[] cityDetails = sc.nextLine().split(" ");
                int cityId = Integer.parseInt(cityDetails[0]);
                String cityName = cityDetails[1];
                cities[cityId] = cityName;
            }
            
            Graph citiesMap = new MatrixGraph(cityCount);
            
            int safeTravelCount = sc.nextInt();
            sc.nextLine();
            for (int i = 0; i < safeTravelCount; i++) {
                String[] safeTravelDetails = sc.nextLine().split(" ");
                int from = Integer.parseInt(safeTravelDetails[0]);
                int to = Integer.parseInt(safeTravelDetails[1]);
                int timeDistance = Integer.parseInt(safeTravelDetails[2]);
                citiesMap.addWeightedEdge(from, to, timeDistance);
            }
            
            visitedInDfs = new boolean[missionCount + 1];
            for (Mission m : missions) {
                if (m != null) dfs(missionDeps, m.missionId);
            }

            int[] missionOrder = new int[missionCount];
            int i = missionCount - 1;
            while (!missionStack.isEmpty() && i >= 0) {
                missionOrder[i] = missionStack.pop();
                i--;
            }

            int totalTravelTime = 0;
            int currentCity = origin;
            System.out.println("Ciudad inicial: " + cities[currentCity]);
            
            for (int missionId : missionOrder) {
                Mission m = missions[missionId];

                int[][] dijkstraFromCurrentCity = ((MatrixGraph) citiesMap).dijkstra(currentCity);
                int[] times = dijkstraFromCurrentCity[0];
                int[] paths = dijkstraFromCurrentCity[1];

                int timeToMissionCity = times[m.missionCityId];
                totalTravelTime += timeToMissionCity;

                int currentCityPath = m.missionCityId;
                Stack<String> path = new ListStack<>();
                while (paths[currentCityPath] != -1) {
                    path.push(cities[currentCityPath]);
                    currentCityPath = paths[currentCityPath];
                }
                path.push(cities[currentCity]);

                while (!path.isEmpty()) {
                    System.out.print(path.pop() + " -> ");
                }
                System.out.println("Mision: " + m.missionName + " - " + cities[m.missionCityId] + " - Tiempo de viaje: " + timeToMissionCity);

                currentCity = m.missionCityId;
            }
            System.out.println("Misiones ejecutadas con exito.");
            System.out.println("Tiempo total de viaje: " + totalTravelTime);
            
        }
    }
    
    public static void dfs(Graph g, int i) {
        visitedInDfs[i] = true;
        Iterator<Graph.Edge> edgeIt = g.adjacents(i).iterator();
        while (edgeIt.hasNext()) {
            int j = edgeIt.next().getTo();
            if (g.isAdjacent(i, j) && !visitedInDfs[j]) {
                dfs(g, j);
            }
        }
        missionStack.push(i);
    }
}
