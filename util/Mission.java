package util;

public class Mission {
    public int missionId;
    public String missionName;
    public int missionCityId;
    // public List<Integer> dependencies;
    
    public Mission(int id, String name, int cityId/*, List<Integer> dependencies*/) {
        this.missionId = id;
        this.missionName = name;
        this.missionCityId = cityId;
        // this.dependencies = dependencies;
    }
}
