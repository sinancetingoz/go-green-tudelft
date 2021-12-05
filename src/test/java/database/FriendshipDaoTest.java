package database;

import database.AccountDao;
import database.FriendshipDao;
import org.junit.Before;
import org.junit.Test;
import pojos.Account;
import pojos.Friendship;

import javax.validation.constraints.AssertTrue;

import java.sql.SQLException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FriendshipDaoTest {

    private FriendshipDao fd = new FriendshipDao();
    private AccountDao ad = new AccountDao();
    private Friendship testfriendships = new Friendship("from", "to");
    private Account testaccount1 = new Account("from","from","from","from","from");
    private Account testaccount2 = new Account("to","to","to","to","to");
    @Before
    public void configure()
    {
        ad.changeDatabase("test");
        fd.changeDatabase("test");
        fd.setAd(ad);
    }

    @Test
    public void testExistsFriendship() throws SQLException
    {
        fd.removeFriendship(testfriendships);
        assertFalse(fd.sendRequest(testfriendships));
        assertFalse(fd.friendshipExists(testfriendships));
        ad.createAccount(testaccount1);
        assertFalse(fd.sendRequest(testfriendships));
        ad.createAccount(testaccount2);
        fd.sendRequest(testfriendships);
        assertTrue(fd.friendshipExists(new Friendship("to","from")));
        assertTrue(fd.friendshipExists(new Friendship("from", "to")));
        assertFalse(fd.friendshipExists(new Friendship("from", "to1")));
        assertFalse(fd.requestExists(new Friendship("to", "from")));
        assertTrue(fd.friendshipExists(testfriendships));
        fd.removeFriendship(testfriendships);
        assertFalse(fd.friendshipExists(testfriendships));
        assertNotNull(fd.getFriendships("from"));
    }

    @Test
    public void testSendAcceptFriendship() throws SQLException
    {

        assertTrue(fd.sendRequest(testfriendships));
        assertTrue(fd.acceptRequest(testfriendships));
        assertFalse(fd.sendRequest(testfriendships));
        fd.removeFriendship(testfriendships);
        assertFalse(fd.acceptRequest(testfriendships));
        ad.deleteAccount(testaccount1);
        ad.deleteAccount(testaccount2);
    }

    @Test
    public void testGetMatching() throws SQLException {
        ad.deleteAccount(testaccount1);
        ad.deleteAccount(testaccount2);
        assertTrue(ad.createAccount(testaccount1));
        ArrayList<String> exp = new ArrayList<>();
        assertEquals(exp, fd.getMatchings("z"));
        exp.add("from");
        assertEquals(exp, fd.getMatchings("f"));
        ad.deleteAccount(testaccount1);
        ad.deleteAccount(testaccount2);
    }

}
