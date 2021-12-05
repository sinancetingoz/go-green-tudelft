package server;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pojos.Leaderboard;
import services.LeaderboardService;

import java.sql.SQLException;


@RestController
public class LeaderboardController {

    private LeaderboardService ls = new LeaderboardService();

    /**
     * Gets the leader board of the user.
     *
     * @param username the user whose leader board we are interested in
     * @return a leader board object containing the user and his friends
     */
    @RequestMapping("/leaderboard/{username}")
    public Leaderboard getLeaderboard(@PathVariable("username") String username)
            throws SQLException {
        Leaderboard myleaderboard = ls.getLeaderboard(username);
        return myleaderboard;
    }

    /**
     * Adds points to the current user.
     *
     * @param username the user who we want to add points to
     * @return a string saying that we succeeded in adding points, an exception otherwise
     */
    @RequestMapping("/addpoints/{username}")
    public boolean addPoints(@PathVariable("username") String username) {
        ls.addPoints(1000, username);
        return true;
    }

    public void setLs(LeaderboardService ls) {
        this.ls = ls;
    }


}
