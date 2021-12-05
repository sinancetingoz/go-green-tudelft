package pojos;

import java.sql.Date;
import java.time.LocalDate;

public class Activity {

    String description = "meal";
    Category category;
    int points = 10;
    Date date = Date.valueOf(LocalDate.now());
    String username = "test";

    public Activity(){}

    /**
     * Constructs an activity using already known parameters.
     * @param description the description of the activity
     * @param category the category of the activity
     * @param points the points gained from the activity
     * @param date the date of the activity
     * @param username the user who did the activity
     */
    public Activity(String description, Category category, int points, Date date, String username) {
        this.description = description;
        this.category = category;
        this.points = points;
        this.date = date;
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Activity) {
            Activity that = (Activity) obj;

            if (this.description.equals(that.description)
                    && this.category == that.category
                    && this.points == that.points
                    && this.date.equals(that.date)
                    && this.username.equals(that.username)) {
                return true;
            }
        }

        return false;
    }
}
