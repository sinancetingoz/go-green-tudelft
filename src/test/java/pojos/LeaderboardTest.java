package pojos;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
public class LeaderboardTest {

    @Test
    public void TestConstructor() {
        ArrayList<String> usernames = new ArrayList<String>();
        ArrayList<Integer> points = new ArrayList<Integer>();
        String user1 = "Meepo";
        String user2 = "Lanaya";
        String user3 = "Rylai";
        int point1 = 1000;
        int point2 = 2000;
        int point3 = 3000;
        usernames.add(user1);
        usernames.add(user2);
        usernames.add(user3);
        points.add(point1);
        points.add(point2);
        points.add(point3);
        Leaderboard leaderboard = new Leaderboard(usernames, points);
        assertTrue(leaderboard.getPoints() == points);
        assertTrue(leaderboard.getUsernames() == usernames);
    }

    @Test
    public void TestConstructorNullLists() {
        ArrayList<String> usernames = null;
        ArrayList<Integer> points = null;
        Leaderboard leaderboard = new Leaderboard(usernames, points);
        assertTrue(leaderboard.getPoints() == points);
        assertTrue(leaderboard.getUsernames() == usernames);
    }

    @Test
    public void TestEqualsTrue() {
        ArrayList<String> usernames1 = new ArrayList<String>();
        ArrayList<Integer> points1 = new ArrayList<Integer>();
        ArrayList<String> usernames2 = new ArrayList<String>();
        ArrayList<Integer> points2 = new ArrayList<Integer>();
        String user1 = "Meepo";
        String user2 = "Lanaya";
        String user3 = "Rylai";
        int point1 = 1000;
        int point2 = 2000;
        int point3 = 3000;
        String user4 = "Meepo";
        String user5 = "Lanaya";
        String user6 = "Rylai";
        int point4 = 1000;
        int point5 = 2000;
        int point6 = 3000;
        usernames1.add(user1);
        usernames1.add(user2);
        usernames1.add(user3);
        points1.add(point1);
        points1.add(point2);
        points1.add(point3);
        usernames2.add(user4);
        usernames2.add(user5);
        usernames2.add(user6);
        points2.add(point4);
        points2.add(point5);
        points2.add(point6);
        Leaderboard leaderboard1 = new Leaderboard(usernames1, points1);
        Leaderboard leaderboard2 = new Leaderboard(usernames2, points2);
        assertTrue(leaderboard1.equals(leaderboard2) == true);
    }

    @Test
    public void TestEqualsFalse() {
        ArrayList<String> usernames1 = new ArrayList<String>();
        ArrayList<Integer> points1 = new ArrayList<Integer>();
        ArrayList<String> usernames2 = new ArrayList<String>();
        ArrayList<Integer> points2 = new ArrayList<Integer>();
        String user1 = "Meepo";
        String user2 = "Lanaya";
        String user3 = "Rylai";
        int point1 = 1000;
        int point2 = 2000;
        int point3 = 3000;
        String user4 = "Meepo";
        String user5 = "Lanaya";
        String user6 = "Rylai";
        int point4 = 1000;
        int point5 = 2000;
        int point6 = 3000;
        usernames1.add(user1);
        usernames1.add(user2);
        usernames1.add(user3);
        points1.add(point1);
        points1.add(point2);
        points1.add(point3);
        usernames2.add(user4);
        usernames2.add(user3);
        usernames2.add(user1);
        points2.add(point4);
        points2.add(point4);
        points2.add(point4);
        Leaderboard leaderboard1 = new Leaderboard(usernames1, points1);
        Leaderboard leaderboard2 = new Leaderboard(usernames2, points2);
        assertFalse(leaderboard1.equals(leaderboard2) == true);
    }

    @Test
    public void TestSetUsers() {
        ArrayList<String> usernames1 = new ArrayList<String>();
        ArrayList<String> usernames2 = new ArrayList<String>();
        ArrayList<Integer> points1 = new ArrayList<Integer>();
        String user1 = "Meepo";
        String user2 = "Lanaya";
        int point1 = 1000;
        usernames1.add(user1);
        points1.add(point1);
        usernames2.add(user2);
        Leaderboard leaderboard1 = new Leaderboard(usernames1, points1);
        leaderboard1.setUsernames(usernames2);
        assertTrue(leaderboard1.getUsernames() == usernames2);
    }

    @Test
    public void TestSetPoints() {
        ArrayList<String> usernames1 = new ArrayList<String>();
        ArrayList<Integer> points1 = new ArrayList<Integer>();
        ArrayList<Integer> points2 = new ArrayList<Integer>();
        String user1 = "Meepo";
        String user2 = "Lanaya";
        int point1 = 1000;
        int point2 = 2000;
        usernames1.add(user1);
        points1.add(point1);
        points2.add(point2);
        Leaderboard leaderboard1 = new Leaderboard(usernames1, points1);
        leaderboard1.setPoints(points2);
        assertTrue(leaderboard1.getPoints() == points2);
    }

    @Test
    public void testEqualsFalse1() {
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("user1");
        usernames.add("user2");

        ArrayList<Integer> points = new ArrayList<>();
        points.add(100);
        points.add(150);

        Leaderboard leaderboard = new Leaderboard(usernames, points);

        int var = 42;

        assertNotEquals(leaderboard, var);
    }

    @Test
    public void testEqualsFalse2() {
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("user1");
        usernames.add("user2");

        ArrayList<Integer> points = new ArrayList<>();
        points.add(100);
        points.add(150);

        Leaderboard leaderboard = new Leaderboard(usernames, points);


        ArrayList<String> usernames1 = new ArrayList<>();
        usernames1.add("user1");
        usernames1.add("user2");

        ArrayList<Integer> points1 = new ArrayList<>();
        points1.add(100);
        points1.add(125);

        Leaderboard leaderboard1 = new Leaderboard(usernames1, points1);

        assertNotEquals(leaderboard, leaderboard1);
    }
}
