package database;

import database.AccountDao;
import database.ActivityDao;
import database.FriendshipDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pojos.Account;
import pojos.Friendship;

import javax.validation.constraints.AssertTrue;

import java.sql.SQLException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class AccountDaoTest {


    private AccountDao accountDao = new AccountDao();
    private AccountDao accountDao1 = Mockito.spy(accountDao);
    private FriendshipDao friendshipDao;
    private Account newaccount;
    private ActivityDao activityDao;

    @Before
    public void configure() {
        accountDao.changeDatabase("test");
        newaccount = new Account("user_test", "user", "user", "user", "user");
    }

    @Test
    public void testCreateExistsAccount ()throws SQLException
    {
        accountDao.deleteAccount(newaccount);
        assertFalse(accountDao.exists("user_test"));
        assertFalse(accountDao.updatePoints("user_test",100));
        assertTrue(accountDao.createAccount(newaccount));
        assertFalse(accountDao.createAccount(newaccount));
        assertTrue(accountDao.exists("user_test"));
        accountDao.deleteAccount(newaccount);

    }

    @Test
    public void testGetAccount() throws SQLException
    {
        assertNull(accountDao.getAccount("user_test"));
        accountDao.createAccount(newaccount);
        assertNotNull(accountDao.getAccount("user_test"));
        accountDao.deleteAccount(newaccount);

    }


    @Test
    public void testUpdatePoints() throws SQLException {
        assertTrue(accountDao.createAccount(newaccount));
        assertTrue(accountDao.updatePoints(newaccount.getUsername(), 100));
        accountDao.deleteAccount(newaccount);
    }


    @Test
    public void testSetEnergy() throws SQLException {
        assertFalse(accountDao.setEnergy(newaccount.getUsername(), 350));
        assertTrue(accountDao.createAccount(newaccount));
        assertTrue(accountDao.setEnergy(newaccount.getUsername(), 350));
        accountDao.deleteAccount(newaccount);
    }

    @Test
    public void testGetAccounts() throws SQLException {
        ArrayList<Account> exp = new ArrayList<>();
        assertEquals(exp, accountDao.getAccounts());
        assertTrue(accountDao.createAccount(newaccount));
        exp.add(newaccount);
        assertEquals(exp, accountDao.getAccounts());
        accountDao.deleteAccount(newaccount);
    }

    @Test
    public void testSetHeating() throws SQLException {
        assertFalse(accountDao.setHeating("lel", true));
        accountDao.createAccount(newaccount);
        assertTrue(accountDao.setHeating("user_test", true));
        accountDao.deleteAccount(newaccount);
    }

    @Test
    public void testUpdatePassword() throws SQLException {
        assertFalse(accountDao.updatePassword("user_test", "blabla"));
        accountDao.createAccount(newaccount);
        assertTrue(accountDao.updatePassword("user_test", "blabla"));
        accountDao.deleteAccount(newaccount);
    }

    @Test
    public void testGetEmail() throws SQLException {
        assertNull(accountDao.getEmail(newaccount.getMail()));
        accountDao.createAccount(newaccount);
        assertEquals(newaccount, accountDao.getEmail(newaccount.getMail()));
    }

}
