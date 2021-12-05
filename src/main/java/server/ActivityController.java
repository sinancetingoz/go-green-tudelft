package server;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.*;
import pojos.Activity;
import services.ActivityService;
import services.SessionService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class ActivityController {

    ActivityService as = new ActivityService();
    SessionService ss = new SessionService();
    SessionController sc = new SessionController();

    /**
     * Gets the activities of a user.
     * @param sessionId the sessionId of the user whose activities we want
     * @return a list containing all activities of the user
     */
    @RequestMapping("/get_activity/{sessionId}")
    public ArrayList<Activity> getActivities(@PathVariable("sessionId") String sessionId) {
        if (ss.sessionExists(sessionId)) {
            return as.getActivities(sc.getUser(sessionId));
        }
        return new ArrayList<Activity>();
    }

    /**
     * Adds activity to a current user.
     * @param activity the activity to be added
     * @param sessionId the sessionId of the user
     * @return true if the process was successful, false otherwise
     */
    @PostMapping("/add_activity/{sessionId}")
    public boolean addActivity(@RequestBody Activity activity,
                               @PathVariable("sessionId") String sessionId) {
        if (ss.sessionExists(sessionId)) {
            return as.createActivity(activity);
        }
        return false;
    }


    /**
     * Gets the activities of friends of a user since date.
     * @param entity the users and the date since which we want all the activities
     * @param sessionId the sessionId of the current user
     * @return a hash map containing all activities of a certain user
     */
    @PostMapping("/friend_activities/{sessionId}")
    public HashMap<String, ArrayList<Activity>> getActivitiesSince(
        @RequestBody ArrayList<String> entity,
        @PathVariable("sessionId") String sessionId) {
        if (ss.sessionExists(sessionId)) {
            HashMap<String, ArrayList<Activity>> activities = new HashMap<>();
            for (String user : entity) {
                activities.put(user, as.getActivitiesOfUserSince(
                        user, Date.valueOf(LocalDate.now().minusDays(7))));
            }

            return activities;
        }

        return new HashMap<>();
    }

    /**
     * Setup the connections of a current controller.
     * @param sc the session controller to be set
     * @param ss the session service to be set
     * @param as the activity service to be set
     */
    public void setup(SessionController sc, SessionService ss, ActivityService as) {
        this.sc = sc;
        this.ss = ss;
        this.as = as;
    }
}
