package tourGuide;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsoniter.output.JsonStream;

import gpsUtil.location.VisitedLocation;
import rewardCentral.RewardCentral;
import tourGuide.model.DTO.AllCurrentLocationsDTO;
import tourGuide.model.DTO.NearByAttractionsDTO;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;
import tripPricer.Provider;

@RestController
public class TourGuideController {

	@Autowired
	TourGuideService tourGuideService;
    @Autowired
    RewardCentral rewardCentral;
	
    @RequestMapping("/")
    public String index() {
        return "Greetings from TourGuide!";
    }
    
    @RequestMapping("/getLocation") 
    public String getLocation(@RequestParam String userName) throws ExecutionException, InterruptedException {
    	VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
		return JsonStream.serialize(visitedLocation.location);
    }

    @RequestMapping("/getNearbyAttractions") 
    public String getNearbyAttractions(@RequestParam String userName) {
        VisitedLocation visitedLocation = null;
        try {
            visitedLocation = tourGuideService.getUserLocation(getUser(userName));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ArrayList<NearByAttractionsDTO> nearByAttractionsDTOS = new ArrayList<>();
        TreeMap<Double, Attraction> nearByAttractions = tourGuideService.getNearByAttractions(visitedLocation);

        for (Map.Entry<Double, Attraction> entry : nearByAttractions.entrySet()) {
            NearByAttractionsDTO nearByAttractionsDTO = new NearByAttractionsDTO();
            nearByAttractionsDTO.setAttractionName(entry.getValue().attractionName);
            nearByAttractionsDTO.setAttractionLocation(new Location(entry.getValue().latitude, entry.getValue().longitude));
            nearByAttractionsDTO.setUserLocation(visitedLocation.location);
            nearByAttractionsDTO.setDistanceInMiles(entry.getKey());
            nearByAttractionsDTO.setRewardPoints(rewardCentral.getAttractionRewardPoints(entry.getValue().attractionId, tourGuideService.getUser(userName).getUserId()));

            nearByAttractionsDTOS.add(nearByAttractionsDTO);
        }

        return JsonStream.serialize(nearByAttractionsDTOS.toString());
    }
    
    @RequestMapping("/getRewards") 
    public String getRewards(@RequestParam String userName) {
    	return JsonStream.serialize(tourGuideService.getUserRewards(getUser(userName)).toString());
    }
    
    @RequestMapping("/getAllCurrentLocations")
    public String getAllCurrentLocations() {
        List<User> allUsers = new ArrayList<>();
        allUsers = tourGuideService.getAllUsers();
        List<AllCurrentLocationsDTO> allCurrentLocationsDTOS = new ArrayList<>();

        for (User user : allUsers) {
            AllCurrentLocationsDTO allCurrentLocationsDTO = new AllCurrentLocationsDTO();
            allCurrentLocationsDTO.setUserId(user.getUserId());
            allCurrentLocationsDTO.setLocation(user.getLastVisitedLocation().location);

            allCurrentLocationsDTOS.add(allCurrentLocationsDTO);
        }

    	return JsonStream.serialize(allCurrentLocationsDTOS.toString());
    }
    
    @RequestMapping("/getTripDeals")
    public String getTripDeals(@RequestParam String userName) {
    	List<Provider> providers = tourGuideService.getTripDeals(getUser(userName));
    	return JsonStream.serialize(providers);
    }
    
    private User getUser(String userName) {
    	return tourGuideService.getUser(userName);
    }
   

}