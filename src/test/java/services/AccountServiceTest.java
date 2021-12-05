package services;

import database.AccountDao;
import database.ActivityDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import pojos.Account;
import pojos.Activity;
import services.AccountService;
import services.ActivityService;

import java.sql.SQLException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    private AccountDao db = mock(AccountDao.class);
    private AccountService as = new AccountService();
    private Account testaccount;

    @Before
    public void configure()
    {
        testaccount = new Account("user", "user", "password",
                "user", "user");
        as.setDb(db);
    }

    @Test
    public void testExistsAccount() throws SQLException
    {
        when(db.exists("username")).thenReturn(true);
        assertTrue(as.userExists("username"));
    }

    @Test
    public void testAccountServiceOk() throws SQLException
    {
        when(db.exists("user")).thenReturn(true);
        when(db.getAccount("user")).thenReturn(testaccount);
        assertTrue(as.checkLogin("user", "password"));
    }

    @Test
    public void testAccountServiceFailed() throws SQLException
    {
        when(db.exists("user")).thenReturn(false);
        assertFalse(as.checkLogin("user", "password"));
    }

    @Test
    public void testAccountServiceCreateFailed() throws SQLException
    {
        when(db.exists(Matchers.anyString())).thenReturn(true);
        when(db.createAccount(Matchers.any(Account.class))).thenReturn(false);
        assertFalse(as.createAccount(testaccount));
    }

    @Test
    public void testAccountServiceCreateOk() throws SQLException
    {
        when(db.exists("user")).thenReturn(false);
        when(db.createAccount(testaccount)).thenReturn(true);
        assertTrue(as.createAccount(testaccount));
    }


    @Test
    public void testHandleException() throws SQLException
    {
        when(db.exists(Matchers.anyString())).thenThrow(new SQLException());
        when(db.getAccount(Matchers.anyString())).thenThrow(new SQLException());
        when(db.updatePoints(Matchers.anyString(),Matchers.anyInt())).thenThrow(new SQLException());
        assertFalse(as.userExists("username"));
        assertFalse(as.createAccount(testaccount));
        assertNull(as.getAccount("username"));
        assertFalse(as.checkLogin("username","password"));
        assertFalse(as.updatePoints("username",0));
    }

    @Test
    public void testResetHeating() throws SQLException {
        when(db.getAccounts()).thenReturn(new ArrayList<>());
        assertTrue(as.resetHeating());

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(testaccount);
        when(db.getAccounts()).thenReturn(accounts);
        when(db.setHeating("user", false)).thenReturn(true);
        assertTrue(as.resetHeating());

        when(db.setHeating("user", false)).thenReturn(false);
        assertFalse(as.resetHeating());
    }

}
