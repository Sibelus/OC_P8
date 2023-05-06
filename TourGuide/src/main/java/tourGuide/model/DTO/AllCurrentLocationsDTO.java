package tourGuide.model.DTO;

import gpsUtil.location.Location;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AllCurrentLocationsDTO {
    private UUID userId;
    private Location location;


    //TO STRING
    @Override
    public String toString() {
        return "{" + userId.toString() +
                ": {longitude: " + location.longitude + ", latitude: " + location.latitude + "}" +
                '}';
    }


    //GETTERS & SETTERS
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
