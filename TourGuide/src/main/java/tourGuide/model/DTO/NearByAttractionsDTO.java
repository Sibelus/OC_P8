package tourGuide.model.DTO;

import gpsUtil.location.Location;
import org.springframework.stereotype.Component;

@Component
public class NearByAttractionsDTO {
    private String attractionName;
    private Location attractionLocation;
    private Location userLocation;
    private double distanceInMiles;
    private int rewardPoints;


    //GETTERS & SETTERS
    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public Location getAttractionLocation() {
        return attractionLocation;
    }

    public void setAttractionLocation(Location attractionLocation) {
        this.attractionLocation = attractionLocation;
    }

    public Location getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(Location userLocation) {
        this.userLocation = userLocation;
    }

    public double getDistanceInMiles() {
        return distanceInMiles;
    }

    public void setDistanceInMiles(double distanceInMiles) {
        this.distanceInMiles = distanceInMiles;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }


    @Override
    public String toString() {
        return "{" +
                "attractionName='" + attractionName + '\'' +
                ", attractionLocation= [longitude: " + attractionLocation.longitude + ", latitude: " + attractionLocation.latitude + "]" +
                ", userLocation= [longitude: " + userLocation.longitude + ", latitude: " + userLocation.latitude + "]" +
                ", distanceInMiles=" + distanceInMiles +
                ", rewardPoints=" + rewardPoints +
                '}';
    }
}
