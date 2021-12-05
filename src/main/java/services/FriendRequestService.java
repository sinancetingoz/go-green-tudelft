package services;

import database.AccountDao;
import database.FriendshipDao;
import pojos.Account;
import pojos.Friendship;

import java.sql.SQLException;
import java.util.ArrayList;

public class FriendRequestService {

    FriendshipDao db = new FriendshipDao();
    AccountDao db2 = new AccountDao();

    /**
     * Returns whether a friendship exists.
     *
     * @param friendship the friendship we want to check if exists
     * @return true if the friendship exists, false otherwise
     */
    public boolean friendshipExists(Friendship friendship) {
        try {
            return db.friendshipExists(friendship);
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Send a friend request.
     *
     * @param friendship the friend request represented as a Friendship object
     * @return true if the friend request was sent successfully, false otherwise
     */
    public boolean sendRequest(Friendship friendship) {
        try {
            return db.sendRequest(friendship);
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Accept a friend request.
     *
     * @param friendship the friend request to be accepted represented as a Friendship object
     * @return true if the friend request was successfully accepted, false otherwise
     */
    public boolean acceptRequest(Friendship friendship) {
        try {
            return db.acceptRequest(friendship);
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Removes specified friendship.
     * @param friendship to be removed
     * @return true if the friendship was successfully removed, false otherwise
     */
    public boolean removeFriendship(Friendship friendship) {
        try {
            db.removeFriendship(friendship);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void setDb(FriendshipDao fd) {
        this.db = fd;
    }

    /**
     * Returns all active friendships of a certain user.
     *
     * @param user the user whose friendships we want to know
     * @return a list containing all the friendships of the user
     */
    public ArrayList<Friendship> getActiveFriendships(String user) {
        try {
            ArrayList<Friendship> active = new ArrayList<>();
            ArrayList<Friendship> all = db.getFriendships(user);
            for (Friendship search : all) {
                if (search.getStatus()) {
                    active.add(search);
                }
            }
            return active;
        } catch (SQLException e) {
            return new ArrayList<Friendship>();
        }
    }

    /**
     * Returns all inactive friendships of a certain user.
     *
     * @param user the user whose friendships we want to know
     * @return a list containing all the friendships of the user
     */
    public ArrayList<Friendship> getInactiveFriendships(String user) {
        try {
            ArrayList<Friendship> inactive = new ArrayList<>();
            ArrayList<Friendship> all = db.getFriendships(user);
            for (Friendship search : all) {
                if (!search.getStatus()) {
                    inactive.add(search);
                }
            }
            return inactive;
        } catch (SQLException e) {
            return new ArrayList<Friendship>();
        }
    }

    /**
     * Gets the friends of a specified user.
     * @param user The user whose friends are requested
     * @return an arraylist of all the usernames of the users friends
     */
    public ArrayList<String> getFriends(String user) {
        ArrayList<Friendship> friendships = getActiveFriendships(user);
        ArrayList<String> friends = new ArrayList<>();
        for (Friendship fr : friendships) {
            friends.add(fr.getReceiver().equals(user) ? fr.getSender()
                    : fr.getReceiver());
        }

        return friends;
    }

    /**
     * Gets matching usernames from the provided string.
     * @param match string to match usenames to
     * @return an arraylist of all the usernames matching the string
     */
    public ArrayList<String> getMatchings(String match) {
        try {
            return db.getMatchings(match);
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Returns the account of a friend of the user.
     * @param friend the friend we are looking for
     * @return the account of the friend
     */
    public Account getFriendAccount(String friend) {
        try {
            Account account =  db2.getAccount(friend);
            account.setMail("");
            account.setPassword("");
            return account;
        } catch (SQLException e) {
            return new Account();
        }
    }
}
