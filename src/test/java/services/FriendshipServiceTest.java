package services;

import database.FriendshipDao;
import org.junit.Before;
import org.junit.Test;
import pojos.Friendship;
import services.FriendRequestService;

import java.sql.SQLException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FriendshipServiceTest {

    private FriendRequestService frs = new FriendRequestService();
    private FriendshipDao test = mock(FriendshipDao.class);
    private Friendship testfriendship = new Friendship("from","to");

    @Before
    public void configure()
    {
        frs.setDb(test);
    }

    @Test
    public void testFriendshipExists() throws SQLException
    {
        when(test.friendshipExists(testfriendship)).thenReturn(true);
        when(test.acceptRequest(testfriendship)).thenReturn(true);
        assertTrue(frs.friendshipExists(testfriendship));
        assertTrue(frs.acceptRequest(testfriendship));
        when(test.friendshipExists(testfriendship)).thenReturn(false);
        when(test.acceptRequest(testfriendship)).thenReturn(false);
        assertFalse(frs.friendshipExists(testfriendship));
        assertFalse(frs.acceptRequest(testfriendship));
    }

    @Test
    public void testFriendshipSend() throws SQLException
    {
        when(test.sendRequest(testfriendship)).thenReturn(true);
        when(test.getFriendships("test")).thenReturn(new ArrayList<Friendship>());
        assertTrue(frs.sendRequest(testfriendship));
        assertEquals(0,frs.getActiveFriendships("test").size());
        assertEquals(0,frs.getInactiveFriendships("test").size());
        ArrayList<Friendship> now = new ArrayList<>();
        now.add(testfriendship);
        when(test.getFriendships("from")).thenReturn(now);
        assertEquals(new ArrayList<>(), frs.getActiveFriendships("from"));
        assertEquals(now, frs.getInactiveFriendships("from"));
        now.remove(testfriendship);
        testfriendship.setStatus(true);
        now.add(testfriendship);
        when(test.getFriendships("from")).thenReturn(now);
        assertEquals(new ArrayList<>(), frs.getInactiveFriendships("from"));
        testfriendship.setStatus(false);
        when(test.sendRequest(testfriendship)).thenReturn(false);
        assertFalse(frs.sendRequest(testfriendship));
    }
    @Test
    public void testHandleException() throws  SQLException
    {
        when(test.sendRequest(testfriendship)).thenThrow(new SQLException());
        when(test.getFriendships("test")).thenThrow(new SQLException());
        when(test.acceptRequest(testfriendship)).thenThrow(new SQLException());
        assertFalse(frs.sendRequest(testfriendship));
        assertEquals(0,frs.getActiveFriendships("test").size());
        assertEquals(0,frs.getInactiveFriendships("test").size());
        assertFalse(frs.acceptRequest(testfriendship));
    }

    @Test
    public void testGetFriends() throws SQLException {
        ArrayList<String> exp = new ArrayList<>();
        when(test.getFriendships("test")).thenReturn(new ArrayList<>());
        assertEquals(new ArrayList<>(), frs.getFriends("test"));
        exp.add("to");
        ArrayList<Friendship> now = new ArrayList<>();
        testfriendship.setStatus(true);
        now.add(testfriendship);
        when(test.getFriendships("from")).thenReturn(now);
        when(test.getFriendships("to")).thenReturn(now);
        assertEquals(exp, frs.getFriends("from"));
        exp.remove("to");
        exp.add("from");
        assertEquals(exp, frs.getFriends("to"));
        testfriendship.setStatus(false);
    }
}
