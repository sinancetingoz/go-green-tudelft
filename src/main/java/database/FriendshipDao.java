package database;

import pojos.Friendship;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FriendshipDao extends Dao {

    private AccountDao ad = new AccountDao();

    public FriendshipDao() {
        super();
    }


    public void setAd(AccountDao ad) {
        this.ad = ad;
    }

    /**
     * Adds information to the database when a user accepts a friend request.
     *
     * @param friendship the friendship which will be accepted
     * @return true if the friendship was successfully accepted, false otherwise
     */
    public boolean acceptRequest(Friendship friendship) throws SQLException {
        if (!requestExists(friendship)) {
            return false;
        }

        String query = "UPDATE friendship SET accepted = true WHERE "
                + "sender = ?"
                + "AND receiver = ?";

        PreparedStatement update = this.conn.prepareStatement(query);
        update.setString(1, friendship.getSender());
        update.setString(2, friendship.getReceiver());
        update.execute();
        update.close();
        return true;

    }


    /**
     * Returns all the friendships of a single user.
     *
     * @param username the username of the user
     * @return an array list of all the friendships of the user
     */
    public ArrayList<Friendship> getFriendships(String username) throws SQLException {
        String query = "SELECT * FROM friendship WHERE sender = ?"
                + "OR receiver = ?";
        ArrayList<Friendship> friendships = new ArrayList<>();
        PreparedStatement request = this.conn.prepareStatement(query);
        request.setString(1, username);
        request.setString(2, username);
        ResultSet rs = request.executeQuery();

        while (rs.next()) {
            Friendship addfriendship = new Friendship();
            addfriendship.setSender(rs.getString("sender"));
            addfriendship.setReceiver(rs.getString("receiver"));
            addfriendship.setStatus(rs.getBoolean("accepted"));
            friendships.add(addfriendship);
        }
        return friendships;

    }

    /**
     * Checks whether a friendship exists.
     *
     * @param friendship the friendship to be searching for
     * @return true if the friendship exists, false otherwise
     */
    public boolean friendshipExists(Friendship friendship) throws SQLException {

        ArrayList<Friendship> friendships = getFriendships(friendship.getSender());
        for (Friendship search : friendships) {
            if (search.getReceiver().equals(friendship.getReceiver())
                    || search.getSender().equals(friendship.getReceiver())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a friendship from the database.
     *
     * @param friendship the friendship to be removed
     */
    public void removeFriendship(Friendship friendship) throws SQLException {
        String query = "DELETE FROM friendship WHERE (receiver = ? AND sender = ?) "
                + "OR (receiver = ? AND sender = ?)";

        if (!friendshipExists(friendship)) {
            return;
        }

        PreparedStatement st = this.conn.prepareStatement(query);
        st.setString(1, friendship.getSender());
        st.setString(2, friendship.getReceiver());
        st.setString(3, friendship.getReceiver());
        st.setString(4, friendship.getSender());
        st.execute();
        st.close();

    }

    /**
     * Checks whether a friend request exists in the database.
     *
     * @param friendship the friend request to be checked
     * @return true if the friend request exists, false otherwise
     */
    public boolean requestExists(Friendship friendship) throws SQLException {
        ArrayList<Friendship> friendships = getFriendships(friendship.getSender());
        for (Friendship search : friendships) {
            if (search.getReceiver().equals(friendship.getReceiver())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a friend request in the database.
     *
     * @param friendship the friend request to be added to the database
     * @return true if the adding was successful, false otherwise
     */
    public boolean sendRequest(Friendship friendship) throws SQLException {
        if (!ad.exists(friendship.getSender())
                || !ad.exists(friendship.getReceiver())) {
            return false;
        }

        if (friendshipExists(friendship)) {
            return false;
        }

        String query = "INSERT INTO friendship (sender, receiver, accepted)"
                + " VALUES  (?, ?, ?)";

        PreparedStatement st = this.conn.prepareStatement(query);
        st.setString(1, friendship.getSender());
        st.setString(2, friendship.getReceiver());
        st.setBoolean(3, false);

        st.execute();
        st.close();
        return true;

    }

    /**
     * Gets usernames matching the given string
     * @param match The string to be matched to
     * @return an arraylist of all usernames matching the string
     * @throws SQLException when something goes wrong while getting the usernames from the database.
     */
    public ArrayList<String> getMatchings(String match) throws SQLException {
        String query = "SELECT username FROM account WHERE username LIKE ? ";
        match += "%";
        PreparedStatement request = this.conn.prepareStatement(query);
        request.setString(1,match);
        ResultSet rs = request.executeQuery();
        ArrayList<String> matchings = new ArrayList<>();
        while (rs.next()) {
            matchings.add(rs.getString("username"));
        }
        return matchings;
    }

}
