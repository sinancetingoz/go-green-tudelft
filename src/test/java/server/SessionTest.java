package server;

import services.SessionService;
import org.junit.Before;
import org.junit.Test;
import pojos.Account;
import pojos.Session;

import java.time.LocalDateTime;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class SessionTest {

    Account testuser;
    Session testsession;

    @Before
    public void configure()
    {
        testsession = new Session("user1", LocalDateTime.now());
        testuser = new Account("1","1","1","1","1");
    }
    @Test
    public void testSessionConstructor()
    {
        LocalDateTime testtime = LocalDateTime.now();
        Session testsession = new Session("1",testtime);
        assertTrue(testsession.getUsername().equals("1"));
        assertTrue(testtime.equals(testsession.getTime()));
    }

    @Test
    public void testSessionGetUsernameTest()
    {
        LocalDateTime testtime = LocalDateTime.now();
        Session testsession = new Session("1",testtime);
        assertTrue(testsession.getUsername().equals("1"));
    }

    @Test
    public void testSessionGetTime()
    {
        LocalDateTime testtime = LocalDateTime.now();
        Session testsession = new Session("1",testtime);
        assertTrue(testtime.equals(testsession.getTime()));
    }

    @Test
    public void testSessionSetTime()
    {
        LocalDateTime testtime = LocalDateTime.now();
        Session testsession = new Session("1",testtime);
        testtime = LocalDateTime.now();
        testsession.setTime(testtime);
        assertTrue(testsession.getTime().equals(testtime));
    }

    @Test
    public void testSessionSetUsername()
    {
        LocalDateTime testtime = LocalDateTime.now();
        Session testsession = new Session("1",testtime);
        testsession.setUsername("2");
        assertTrue(testsession.getUsername().equals("2"));
    }

    @Test
    public void testSessionControl()
    {
        SessionService ss = new SessionService();
        SessionController sc = new SessionController();
        assertNull(sc.getUser("1"));
        ss.putSession("1",testsession);
        testsession.setTime(LocalDateTime.now().minusHours(100));
        assertFalse(ss.sessionExists("1"));
        SessionService.removeSession("1");
        assertNull(sc.getUser("1"));
        ss.putSession("1", testsession);
        testsession.setTime(LocalDateTime.now());
        assertTrue(sc.getUser("1").equals("user1"));
        sc.logOut("0");
        sc.logOut("1");
        assertNull(sc.getUser("1"));
    }

}
