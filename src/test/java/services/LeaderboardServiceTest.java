package services;

import database.LeaderboardDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import pojos.Leaderboard;
import services.LeaderboardService;

import java.sql.SQLException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LeaderboardServiceTest {

    private LeaderboardDao ld = mock(LeaderboardDao.class);
    private LeaderboardService ls = new LeaderboardService();

    @Before
    public void configure()
    {
        ls.setDb(ld);
    }

    @Test
    public void testGetLeaderboard() throws SQLException {

        Leaderboard test_leaderboard = new Leaderboard(new ArrayList<String>(), new ArrayList<Integer>());
        when(ld.getLeaderboard("username")).thenReturn(test_leaderboard);
        assertEquals(ls.getDb(),ld);
        assertTrue(ls.getLeaderboard("username").getUsernames().size() == 0);
        doNothing().when(ld).addPoints(Matchers.anyInt(),Matchers.anyString());
        ls.addPoints(10000,"username");
        assertTrue(ls.getLeaderboard("username").getUsernames().size() == 0);
    }

    @Test
    public void testHandleException() throws SQLException
    {
        when(ld.getLeaderboard("username")).thenThrow(new SQLException());
        assertNull(ls.getLeaderboard("username"));
    }

}
