package pojos;

import org.junit.Test;
import static org.junit.Assert.*;
public class FriendshipTest {
    @Test
    public void TestConstructerNoUsers() {
        Friendship relation1 = new Friendship();
        assertTrue(relation1.getReceiver() == null);
        assertTrue(relation1.getSender() == null);
        assertTrue(relation1.getStatus() == false);
    }
    @Test
    public void TestConstructerWithUsers() {
        String user1 = "Meepo";
        String user2 = "Rylai";
        Friendship relation1 = new Friendship(user1, user2);
        assertTrue(relation1.getSender() == user1);
        assertTrue(relation1.getReceiver() == user2);
        assertTrue(relation1.getStatus() == false);
    }

    @Test
    public void TestAcceptFriendship() {
        String user1 = "Meepo";
        String user2 = "Rylai";
        Friendship relation1 = new Friendship(user1, user2);
        relation1.setStatus(true);
        assertTrue(relation1.getStatus() == true);
    }

    @Test
    public void TestSetReceiver() {
        String user1 = "Meepo";
        String user2 = "Rylai";
        Friendship relation1 = new Friendship(user1, user2);
        relation1.setReceiver("Lanaya");
        assertTrue(relation1.getReceiver() == "Lanaya");
    }

    @Test
    public void TestSetSender() {
        String user1 = "Meepo";
        String user2 = "Rylai";
        Friendship relation1 = new Friendship(user1, user2);
        relation1.setSender("Lanaya");
        assertTrue(relation1.getSender() == "Lanaya");
    }

    @Test
    public void TestEqualsTrue() {
        String user1 = "Meepo";
        String user2 = "Rylai";
        Friendship relation1 = new Friendship(user1, user2);
        Friendship relation2 = new Friendship(user1, user2);
        assertTrue(relation1.equals(relation2));
    }

    @Test
    public void TestEqualsFalse() {
        String user1 = "Meepo";
        String user2 = "Rylai";
        Friendship relation1 = new Friendship(user1, user2);
        Friendship relation2 = new Friendship(user2, user1);
        assertFalse(relation1.equals(relation2));
    }

    @Test
    public void testEqualsFalse1() {
        Friendship friendship = new Friendship("user1", "user2");
        int var = 42;
        assertNotEquals(friendship, var);
    }

    @Test
    public void testEqualsFalse2() {
        Friendship friendship = new Friendship("user1", "user2");
        Friendship friendship1 = new Friendship("user3", "user2");
        assertNotEquals(friendship, friendship1);
    }

    @Test
    public void testEqualsFalse3() {
        Friendship friendship = new Friendship("user1", "user2");
        Friendship friendship1 = new Friendship("user1", "user3");
        assertNotEquals(friendship, friendship1);
    }

    @Test
    public void testEqualsFalse4() {
        Friendship friendship = new Friendship("user1", "user2");
        Friendship friendship1 = new Friendship("user1", "user2");
        friendship.setStatus(true);
        assertNotEquals(friendship, friendship1);
    }
}
