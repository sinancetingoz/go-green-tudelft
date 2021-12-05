package database;

import pojos.Friendship;
import pojos.Leaderboard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LeaderboardDao extends Dao {


    private FriendshipDao fd = new FriendshipDao();
    private AccountDao ad = new AccountDao();


    public LeaderboardDao() {
        super();
    }


    public void setAd(AccountDao ad) {
        this.ad = ad;
    }

    public void setFd(FriendshipDao fd) {
        this.fd = fd;
    }

    /**
     * Reset the points of a user.
     *
     * @param username the user whose points are going to be reset
     */
    public void resetPoints(String username) throws SQLException {

        if (!ad.exists(username)) {
            return;
        }

        String query = "UPDATE account SET total_points = 0 WHERE username = ? ";
        PreparedStatement st = this.conn.prepareStatement(query);
        st.setString(1, username);
        st.execute();
        st.close();
        return;
    }

    /**
     * Get the points of a user.
     *
     * @param username the username of the user whose points we want
     * @return the points of the user
     */
    public int getPoints(String username) throws SQLException {
        if (!ad.exists(username)) {
            return 0;
        }

        String query = "SELECT * FROM account WHERE username = ? ";
        PreparedStatement st = this.conn.prepareStatement(query);
        st.setString(1, username);
        ResultSet rs = st.executeQuery();
        int result = 0;
        if (rs.next()) {
            result = rs.getInt("total_points");
        }

        st.close();
        return result;
    }

    /**
     * Adds points to a user.
     *
     * @param toadd    the points to be added
     * @param username the user we want to add points to
     */
    public void addPoints(int toadd, String username) throws SQLException {

        if (!ad.exists(username)) {
            return;
        }
        String query = "UPDATE account SET total_points = ? WHERE username = ? ";
        PreparedStatement st = this.conn.prepareStatement(query);
        st.setInt(1, toadd);
        st.setString(2, username);
        st.execute();
        st.close();
    }

    /**
     * Gets the leader board of the user (himself and all of his friends).
     *
     * @param username the user we want to create a leader board for
     * @return the leader board of the user
     */
    public Leaderboard getLeaderboard(String username) throws SQLException {
        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<Integer> totalPoints = new ArrayList<>();
        if (!ad.exists(username)) {
            return new Leaderboard(usernames, totalPoints);
        }
        usernames.add(username);
        totalPoints.add(getPoints(username));
        ArrayList<Friendship> friendships = fd.getFriendships(username);
        for (Friendship search : friendships) {
            if (search.getStatus()) {
                if (search.getSender().equals(username)) {
                    usernames.add(search.getReceiver());
                    totalPoints.add(getPoints(search.getReceiver()));
                } else {
                    usernames.add(search.getSender());
                    totalPoints.add(getPoints(search.getSender()));
                }
            }
        }
        return new Leaderboard(usernames, totalPoints);
    }
}

