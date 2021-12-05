package services;

import database.ActivityDao;
import pojos.Activity;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class ActivityService {

    private ActivityDao db = new ActivityDao();
    private AccountService as = new AccountService();

    /**
     * Get all activities with a certain description.
     * @param description the description of the activities we are looking for
     * @return a list containing all the activities with the wanted description
     */
    public ArrayList<Activity> getActivities(String description) {
        try {
            return db.getActivities(description);
        } catch (SQLException e) {
            return null;
        }
    }


    /**
     * Create an activity.
     * @param act the activity to be created
     * @return true if the activity was created successfully, false otherwise
     */
    public boolean createActivity(Activity act) {
        try {
            as.updatePoints(act.getUsername(), act.getPoints());
            return db.createActivity(act);
        } catch (SQLException e) {
            return false;
        }
    }


    /**
     * Returns activities of a user since a date.
     * @param user the user whose activities we are interested in
     * @param date the date since which we want all the activities
     * @return an array list containing all activities
     */
    public ArrayList<Activity> getActivitiesOfUserSince(String user, Date date) {
        try {
            ArrayList<Activity> activities = db.getActivities(user);
            ArrayList<Activity> sinceDate = new ArrayList<>();

            for (Activity act : activities) {
                Date dateOfActivity = act.getDate();
                if (dateOfActivity.before(date)) {
                    continue;
                }

                sinceDate.add(act);
            }

            return sinceDate;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }


    public void setDb(ActivityDao db) {
        this.db = db;
    }
}



