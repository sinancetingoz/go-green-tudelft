package server;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import pojos.Leaderboard;
import services.LeaderboardService;

import java.sql.SQLException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LeaderboardControllerTest {

    private LeaderboardController lc = new LeaderboardController();
    private LeaderboardService ls = mock(LeaderboardService.class);

    @Before
    public void configure()
    {
        lc.setLs(ls);
    }

    @Test
    public void testGetAdd() throws SQLException {
        Leaderboard testleaderboard = new Leaderboard(new ArrayList<String>(), new ArrayList<Integer>());
        when(ls.getLeaderboard(Matchers.anyString())).thenReturn(testleaderboard);
        assertEquals(testleaderboard,lc.getLeaderboard("username"));
        doNothing().when(ls).addPoints(anyInt(),Matchers.anyString());
        assertTrue(lc.addPoints("username"));

    }

}
