package server;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import pojos.Account;
import pojos.Friendship;
import pojos.Session;
import services.AccountService;
import services.FriendRequestService;
import services.SessionService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FriendshipsConstrollerTest {

    FriendRequestController frc;
    Friendship testfriendship;
    FriendRequestService frs = mock(FriendRequestService.class);
    AccountService as = mock(AccountService.class);
    SessionService ss = mock(SessionService.class);
    Friendship test3 = mock(Friendship.class);

    @Before
    public void configure()
    {
        frc = new FriendRequestController();
        testfriendship = new Friendship("from", "to");
        frc.setFrs(frs);
        frc.setAs(as);
        frc.setSs(ss);
        when(ss.sessionExists("from")).thenReturn(true);
        HashMap<String,Session> test = new HashMap<>();
        test.put("from",new Session("from", LocalDateTime.now()));
        when(ss.getAllSessions()).thenReturn(test);
    }
    @Test
    public void testSendRequestFailed() throws SQLException {
        when(as.userExists("from")).thenReturn(false);
        assertFalse(frc.sendRequest("from","to"));
        when(as.userExists("from")).thenReturn(true);
        when(as.userExists("to")).thenReturn(false);
        assertFalse(frc.sendRequest("from","to"));
        when(as.userExists("to")).thenReturn(true);
        when(frs.friendshipExists(Matchers.any(Friendship.class))).thenReturn(true);
        assertFalse(frc.sendRequest("from","to"));
    }

    @Test
    public void testSendRequestOK() throws SQLException
    {
        when(frs.friendshipExists(Matchers.any(Friendship.class))).thenReturn(false);
        when(as.userExists("from")).thenReturn(true);
        when(as.userExists("to")).thenReturn(true);
        when(frs.sendRequest(Matchers.any(Friendship.class))).thenReturn(true);
        assertTrue(frc.sendRequest("from","to"));
    }

    @Test
    public void testAcceptRequestFailed() throws SQLException
    {
        when(as.userExists("from")).thenReturn(false);
        assertFalse(frc.acceptRequest("from","to"));
        when(as.userExists("from")).thenReturn(true);
        when(as.userExists("to")).thenReturn(false);
        assertFalse(frc.acceptRequest("from","to"));
        when(frs.friendshipExists(Matchers.any(Friendship.class))).thenReturn(false);
        when(as.userExists("from")).thenReturn(true);
        when(as.userExists("to")).thenReturn(true);
        when(frs.acceptRequest(Matchers.any(Friendship.class))).thenReturn(false);
        assertFalse(frc.acceptRequest("from","to"));
    }

    @Test
    public void testAcceptRequestOk () throws SQLException {
        when(as.userExists("from")).thenReturn(true);
        when(as.userExists("to")).thenReturn(true);
        when(frs.acceptRequest(Matchers.any(Friendship.class))).thenReturn(true);
        when(frs.friendshipExists(Matchers.any(Friendship.class))).thenReturn(true);
        assertTrue(frc.acceptRequest("from","to"));
    }

    @Test
    public void testGetFriendships() {
        when(frs.getActiveFriendships(Matchers.any(String.class))).thenReturn(new ArrayList<Friendship>());
        assertTrue(frc.getActiveFriendships("user").size() == 0);
    }

    @Test
    public void noSessionFound() throws SQLException
    {
        assertFalse(frc.acceptRequest("to","to"));
        assertFalse(frc.sendRequest("to","from"));
        assertEquals(0,frc.getActiveFriendships("to").size());
        assertEquals(0,frc.getActiveFriendships("to").size());
        assertEquals(0,frc.getInactiveFriendships("to").size());
        assertEquals(frc.getActiveFriendships("to").size(),0);
        assertEquals(frc.getInactiveFriendships("to").size(),0);
        assertEquals(frc.getFriends("to").size(),0);
        assertEquals(frc.getMatchings("to","match").size(),0);

    }

    @Test
    public void testGetInactiveGetActive() {
        ArrayList<Friendship> to = new ArrayList<>();
        to.add(testfriendship);
        when(frs.getActiveFriendships("from")).thenReturn(to);
        when(frs.getInactiveFriendships("from")).thenReturn(to);
        assertEquals(frc.getInactiveFriendships("from"),to);
        assertEquals(frc.getActiveFriendships("from"),to);
    }

    @Test
    public void getFriendsGetMatch()
    {
        ArrayList<String> match = new ArrayList<>();
        match.add("test");
        when(frs.getFriends("from")).thenReturn(match);
        when(frs.getMatchings("match")).thenReturn(match);
        assertEquals(frc.getMatchings("from","match"),match);
        assertEquals(frc.getFriends("from"),match);
    }

    @Test
    public void testDeleteFriendship() {
        when(ss.sessionExists("test")).thenReturn(false);
        Friendship friendship = new Friendship("from", "to");
        assertFalse(frc.deleteFriendship(friendship, "test"));

        when(ss.sessionExists("test")).thenReturn(true);
        when(frs.removeFriendship(friendship)).thenReturn(true);
        assertTrue(frc.deleteFriendship(friendship, "test"));
    }


    @Test
    public void testGetFriendAccount() {
        when(ss.sessionExists("test")).thenReturn(false);
        assertEquals(new Account(), frc.getFriendAccount("test", "friend"));

        when(ss.sessionExists("test")).thenReturn(true);
        assertNull(frc.getFriendAccount("test", "friend"));
    }
}
