package database;

import database.AccountDao;
import database.FriendshipDao;
import database.LeaderboardDao;
import org.junit.Before;
import org.junit.Test;
import pojos.Account;
import pojos.Friendship;

import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LeaderboardDaoTest {

    private LeaderboardDao ld = new LeaderboardDao();
    private AccountDao ad = new AccountDao();
    private FriendshipDao fd = new FriendshipDao();
    private Account test_account = new Account("test","test","test","test","test");
    private AccountDao ad1 = mock(AccountDao.class);

    @Before
    public void configure() {
        ad.changeDatabase("test");
        ld.changeDatabase("test");
        fd.changeDatabase("test");
        ld.setAd(ad);
        ld.setFd(fd);
        fd.setAd(ad);
    }

    @Test
    public void testGetResetPoints() throws SQLException{

        ad.createAccount(test_account);
        ld.resetPoints("test");
        assertEquals(0,ld.getPoints("test"));
        ld.addPoints(1,"test");
        assertEquals(ld.getPoints("test"), 1);
        ld.resetPoints("test");
        assertEquals(ld.getPoints("test"),0);
        ad.deleteAccount(test_account);

    }

    @Test
    public void testGetResetUserNotExisting() throws SQLException
    {
        ld.resetPoints("test");
        ld.addPoints(100,"test");
        assertEquals(0,ld.getPoints("test"));
    }

    @Test
    public void testGetLeaderboardTest() throws SQLException {

        assertEquals(0,ld.getLeaderboard("not_existing_user").getUsernames().size());
        Account testaccount1 = new Account("test1","test1","test1","test1","test1");
        Account testaccount2 = new Account("test2","test2","test2","test2","test2");
        Account testaccount3 = new Account("test3","test2","test2","test2","test2");
        ad.createAccount(testaccount1);
        ad.createAccount(testaccount2);
        ad.createAccount(testaccount3);
        fd.sendRequest(new Friendship("test1","test2"));
        fd.sendRequest(new Friendship("test3","test1"));
        fd.acceptRequest(new Friendship("test1","test2"));
        assertEquals(2,ld.getLeaderboard("test1").getUsernames().size());
        fd.acceptRequest(new Friendship("test3","test1"));
        assertEquals(3,ld.getLeaderboard("test1").getUsernames().size());
        fd.removeFriendship(new Friendship("test1","test2"));
        ad.deleteAccount(testaccount1);
        ad.deleteAccount(testaccount2);
        ad.deleteAccount(testaccount3);
    }

    @Test
    public void testGetPoints() throws SQLException {
        ld.setAd(ad1);
        when(ad1.exists(test_account.getUsername())).thenReturn(true);
        assertEquals(0, ld.getPoints(test_account.getUsername()));
        ld.setAd(ad);
    }
}
