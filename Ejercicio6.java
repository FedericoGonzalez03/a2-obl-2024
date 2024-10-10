import java.util.Scanner;

import adt.Map;
import adt.Graph;
import impl.ClosedHashMap;
import impl.MatrixGraph;
import impl.ClosedHashMap.CollisionSolvingMethod;
import util.HashFunction;
import util.MissionCityMap;
import util.MissionCityMap.Travel;

public class Ejercicio6 {

    private static class CityIdHash implements HashFunction<Integer> {
        public int hash(Integer i) {
            return i;
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int cityCount = sc.nextInt();
            sc.nextLine();
            
            Map<Integer, String> cities = new ClosedHashMap<>(cityCount, new CityIdHash(), CollisionSolvingMethod.LINEAR);
            for (int i = 0; i < cityCount; i++) {
                String[] cityDetail = sc.nextLine().split(" ");
                Integer id = Integer.parseInt(cityDetail[0]);
                String city = cityDetail[1];
                cities.set(id, city);
            }
            
            int start = sc.nextInt();
            int entity = sc.nextInt();
            int team = sc.nextInt();
            int point = sc.nextInt();

            int travelCount = sc.nextInt();
            sc.nextLine();
            
            MissionCityMap citiesMapFirstEntity = new MissionCityMap(cityCount, true);
            MissionCityMap citiesMapFirstTeam = new MissionCityMap(cityCount, true);
            for (int i = 0; i < travelCount; i++) {
                String[] travelDetails = sc.nextLine().split(" ");
                int from = Integer.parseInt(travelDetails[0]);
                int to = Integer.parseInt(travelDetails[1]);
                int cost = Integer.parseInt(travelDetails[2]);
                citiesMapFirstEntity.addWeightedEdge(from, to, cost);
                citiesMapFirstTeam.addWeightedEdge(from, to, cost);
            }

            Travel travelToEntity = citiesMapFirstEntity.goToCity(start, entity);
            int costFirstEntity = travelToEntity.cost;
            Travel travelFromEntityToTeam = citiesMapFirstEntity.goToCity(entity, team);
            costFirstEntity += travelFromEntityToTeam.cost;
            Travel travelFromTeamToPoint = citiesMapFirstEntity.goToCity(team, point);
            costFirstEntity += travelFromTeamToPoint.cost;

            Travel travelToTeam = citiesMapFirstTeam.goToCity(start, team);
            int costFirstTeam = travelToTeam.cost;
            Travel travelFromTeamToEntity = citiesMapFirstTeam.goToCity(team, entity);
            costFirstTeam += travelFromTeamToEntity.cost;
            Travel travelFromEntityToPoint = citiesMapFirstTeam.goToCity(entity, point);
            costFirstTeam += travelFromEntityToPoint.cost;

            String firstMissionName = "";
            String secondMissionName = "";

            int betterCost = 0;
            int worseCost = 0;

            Travel travelFirstMission = null;
            Travel travelSecondMission = null;
            Travel travelExtractPoint = null;

            if (costFirstEntity <= costFirstTeam) {
                travelFirstMission = travelToEntity;
                travelSecondMission = travelFromEntityToTeam;
                travelExtractPoint = travelFromTeamToPoint;
                firstMissionName = "Desactivar la Entidad";
                secondMissionName = "buscar equipo";
                betterCost = costFirstEntity;
                worseCost = costFirstTeam;
            } else {
                travelFirstMission = travelToTeam;
                travelSecondMission = travelFromTeamToEntity;
                travelExtractPoint = travelFromEntityToPoint;
                firstMissionName = "buscar equipo";
                secondMissionName = "Desactivar la Entidad";
                betterCost = costFirstTeam;
                worseCost = costFirstEntity;
            }

            System.out.println("BE11, la mejor ruta es " + firstMissionName + ", " + secondMissionName + " y punto de extraccion con un costo de " + betterCost);
            System.out.println("La otra opcion tiene un costo de " + worseCost);
            System.out.print("Paso 1: ");
            String stepDetails = "";
            while (travelFirstMission.path.size() > 0) {
                int currentCity = travelFirstMission.path.get(0);
                travelFirstMission.path.remove(0);
                if (stepDetails.length() > 0) {
                    stepDetails += " -> " + cities.get(currentCity);
                } else {
                    stepDetails = cities.get(currentCity);
                }
            }
            System.out.print(stepDetails);
            System.out.println(" -> " + firstMissionName);
            System.out.print("Paso 2: ");
            stepDetails = "";
            while (travelSecondMission.path.size() > 0) {
                int currentCity = travelSecondMission.path.get(0);
                travelSecondMission.path.remove(0);
                if (stepDetails.length() > 0) {
                    stepDetails += " -> " + cities.get(currentCity);
                } else {
                    stepDetails = cities.get(currentCity);
                }
            }
            System.out.print(stepDetails);
            System.out.println(" -> " + secondMissionName);
            System.out.print("Paso 3: ");
            stepDetails = "";
            while (travelExtractPoint.path.size() > 0) {
                int currentCity = travelExtractPoint.path.get(0);
                travelExtractPoint.path.remove(0);
                if (stepDetails.length() > 0) {
                    stepDetails += " -> " + cities.get(currentCity);
                } else {
                    stepDetails = cities.get(currentCity);
                }
            }
            System.out.print(stepDetails);
            System.out.println(" -> Ir a Punto de extraccion");            
        }
    }
}
