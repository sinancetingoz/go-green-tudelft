package server;

import database.AccountDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.springframework.web.servlet.ModelAndView;
import pojos.Account;
import pojos.Activity;
import pojos.Session;
import services.AccountService;
import services.ActivityService;
import services.SessionService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountControllerTest {

    private AccountController ac = new AccountController();
    private Account testaccount = new Account("user", "user", "password", "user", "user");
    @Before
    public void configure() {
    }

    @Test
    public void testLogInOk() throws SQLException {
        AccountService test = mock(AccountService.class);
        when(test.checkLogin("user", "password")).thenReturn(true);
        ac.setLs(test);
        assertNotNull(ac.logIn("user:password"));
    }
    @Test
    public void testLogInFailed() throws SQLException
    {
        AccountService test = mock(AccountService.class);
        AccountController ac = new AccountController();
        when(test.checkLogin("user", "password")).thenReturn(false);
        ac.setLs(test);
        assertNull(ac.logIn("user:password"));
    }
    @Test
    public void testGetAccountNotFound () throws SQLException
    {
        AccountDao test = mock(AccountDao.class);
        AccountController ac = new AccountController();
        when(test.getAccount("user")).thenReturn(null);
        ac.setDb(test);
        //assertNull(ac.getAccounts("user"));
    }
    /*@Test
    public void GetAccountFound() throws SQLException
    {
        AccountDao test = mock(AccountDao.class);
        AccountController ac = new AccountController();
        when(test.getAccount("user")).thenReturn(new Account());
        ac.setDb(test);
        assertNotNull(ac.getAccounts("user"));
    }*/
    @Test
    public void testRegisterUserOk() throws SQLException
    {
        Account accounttoadd = new Account("user","user","user","user","user");
        AccountService test = mock(AccountService.class);
        AccountController ac = new AccountController();
        ac.setLs(test);
        when(test.createAccount(accounttoadd)).thenReturn(true);
        assertTrue(ac.registerUser("username",accounttoadd));
    }
    @Test
    public void testRegisterUserFailed() throws SQLException
    {
        Account accounttoadd = new Account("user","user","user","user","user");
        AccountService test = mock(AccountService.class);
        AccountController ac = new AccountController();
        ac.setLs(test);
        when(test.createAccount(accounttoadd)).thenReturn(false);
        assertFalse(ac.registerUser("username",accounttoadd));
    }


    @Test
    public void testSetEnergy() {
        SessionService ss = mock(SessionService.class);
        ac.setSs(ss);
        when(ss.sessionExists("test")).thenReturn(false);
        assertFalse(ac.setEnergy(25, "test"));

        when(ss.sessionExists("test")).thenReturn(true);
        HashMap<String, Session> hs = new HashMap<>();
        hs.put("test", new Session("user", LocalDateTime.now()));
        when(ss.getAllSessions()).thenReturn(hs);
        //when(ss.getAllSessions().get("test").getUsername()).thenReturn("user");
        AccountService acc = mock(AccountService.class);
        ac.setLs(acc);
        when(acc.setEnergy("user", 25)).thenReturn(true);
        assertTrue(ac.setEnergy(25, "test"));
    }


    @Test
    public void testGetAccounts() {
        SessionService ss = mock(SessionService.class);
        ac.setSs(ss);
        when(ss.sessionExists("test")).thenReturn(false);
        assertEquals(new Account(), ac.getAccounts("test"));

        AccountService acc = mock(AccountService.class);
        ac.setLs(acc);
        when(ss.sessionExists("test")).thenReturn(true);
        HashMap<String, Session> hs = new HashMap<>();
        hs.put("test", new Session("user", LocalDateTime.now()));
        when(ss.getAllSessions()).thenReturn(hs);
        when(acc.getAccount("user")).thenReturn(testaccount);
        assertEquals(testaccount, ac.getAccounts("test"));
    }

    @Test
    public void testSetHeating() {
        SessionService ss = mock(SessionService.class);
        ac.setSs(ss);
        when(ss.sessionExists("test")).thenReturn(false);
        assertFalse(ac.setHeating(true, "test"));

        AccountService acc = mock(AccountService.class);
        ac.setLs(acc);
        when(ss.sessionExists("test")).thenReturn(true);
        HashMap<String, Session> hs = new HashMap<>();
        hs.put("test", new Session("user", LocalDateTime.now()));
        when(ss.getAllSessions()).thenReturn(hs);
        when(acc.setHeating("user", true)).thenReturn(true);
        assertTrue(ac.setHeating(true, "test"));
    }

    @Test
    public void testConfirmUser() {
        AccountService as = mock(AccountService.class);
        ac.setLs(as);
        when(as.createAccount(testaccount)).thenReturn(false);
        assertFalse(ac.confirmUser(testaccount));

        when(as.createAccount(testaccount)).thenReturn(true);
        assertTrue(ac.confirmUser(testaccount));
    }

}


