package services;

import database.LeaderboardDao;
import pojos.Leaderboard;

import java.sql.SQLException;

public class LeaderboardService {

    private LeaderboardDao ld = new LeaderboardDao();

    /**
     * Return the leader board of a user.
     * @param username the username of the user whose leader board we would like to receive
     * @return the leader board of the user and his friends represented as a Leaderboard object
     */
    public Leaderboard getLeaderboard(String username) {
        try {
            return ld.getLeaderboard(username);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Adds points to a certain user.
     * @param toadd the points to be added
     * @param username the username of the user whose points we would like to increase
     */
    public void addPoints(int toadd, String username) {
        try {
            ld.addPoints(toadd,username);
        } catch (SQLException e) {
            return;
        }
    }

    public void setDb(LeaderboardDao db) {
        this.ld = db;
    }

    public LeaderboardDao getDb() {
        return this.ld;
    }

}
