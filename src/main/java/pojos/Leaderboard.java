package pojos;

import java.util.ArrayList;

public class Leaderboard {

    private ArrayList<String> usernames;
    private ArrayList<Integer> points;

    public Leaderboard(ArrayList<String> usernames, ArrayList<Integer> points) {
        this.usernames = usernames;
        this.points = points;
    }


    public ArrayList<String> getUsernames() {
        return usernames;
    }


    public void setUsernames(ArrayList<String> usernames) {
        this.usernames = usernames;
    }


    public ArrayList<Integer> getPoints() {
        return points;
    }


    public void setPoints(ArrayList<Integer> points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Leaderboard) {
            Leaderboard that = (Leaderboard) obj;

            if (this.usernames.equals(that.usernames)
                    && this.points.equals(that.points)) {
                return true;
            }
        }

        return false;
    }
}
