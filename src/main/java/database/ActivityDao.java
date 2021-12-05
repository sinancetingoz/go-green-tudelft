package database;

import pojos.Activity;
import pojos.Category;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ActivityDao extends Dao {

    public ActivityDao() {
        super();
    }


    /**
     * Makes a query to the database for the activity described.
     * @param username the description of the activity
     * @return the activity details if it exists, null otherwise
     */
    public ArrayList<Activity> getActivities(String username) throws SQLException {
        String query = "SELECT * FROM activity WHERE username = ?";
        PreparedStatement ps = this.conn.prepareStatement(query);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        ArrayList<Activity> activities = new ArrayList<Activity>();
        while (rs.next()) {
            String description = rs.getString(2);
            Category cat = Category.valueOf(rs.getString(3));
            int points = rs.getInt(4);
            Date date = rs.getDate(5);
            activities.add(new Activity(description, cat, points, date, username));
        }
        return activities;

    }

    /**
     * Creates an activity and adds it to the database.
     *
     * @param act the activity to add
     * @return true if the insertion was successful, false otherwise
     */
    public boolean createActivity(Activity act) throws SQLException {
        if (act.getPoints() == 0) {
            return false;
        }

        String query = "INSERT INTO activity (description, category, points, date, username) "
                + "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = this.conn.prepareStatement(query);
        ps.setString(1, act.getDescription());
        ps.setString(2, act.getCategory().name());
        ps.setInt(3, act.getPoints());
        ps.setDate(4, act.getDate());
        ps.setString(5, act.getUsername());

        ps.execute();
        return true;

    }

    /**
     * Removes an activity from the database.
     *
     * @param act the activity to be removed
     */
    public void removeActivity(Activity act) throws SQLException {
        String query = "DELETE FROM activity WHERE username = ? AND date = ?";
        PreparedStatement st = this.conn.prepareStatement(query);
        st.setString(1, act.getUsername());
        st.setDate(2, act.getDate());
        st.execute();
        st.close();

    }
}
