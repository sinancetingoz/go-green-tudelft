package server;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import pojos.Activity;
import pojos.Session;
import services.AccountService;
import services.ActivityService;
import services.SessionService;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pojos.Category.food;

public class ActivityControllerTest {

    private ActivityService as = mock(ActivityService.class);
    private SessionService ss = mock(SessionService.class);
    private SessionController sc = mock(SessionController.class);
    private ActivityController ac = new ActivityController();
    private Activity test_activity;

    @Before
    public void configure() {
        ac.setup(sc,ss,as);
        test_activity = new Activity("description",food,0, Date.valueOf(LocalDate.now()),"test");
    }
    
    @Test
    public void testGetActivityOK() throws SQLException
    {

        ArrayList<Activity> testactivity = new ArrayList<>();
        when(sc.getUser("test")).thenReturn("test");
        when(ss.sessionExists("test")).thenReturn(true);
        when(as.getActivities(Matchers.anyString())).thenReturn(testactivity);
        assertEquals(ac.getActivities("test"), testactivity);
    }

    @Test
    public void testGetActivityFailed() throws SQLException
    {
        when(ss.sessionExists("test")).thenReturn(false);
        assertEquals(0,ac.getActivities("test").size());
    }

    @Test
    public void testAddActivity() throws SQLException
    {
        when(ss.sessionExists("test")).thenReturn(false);
        assertFalse(ac.addActivity(test_activity,"test"));
        when(as.createActivity(test_activity)).thenReturn(true);
        when(ss.sessionExists("test")).thenReturn(true);
        assertTrue(ac.addActivity(test_activity,"test"));
    }


    @Test
    public void testGetActivitiesSince() {
        ArrayList<String> arr = new ArrayList<>();
        when(ss.sessionExists("test")).thenReturn(false);
        assertEquals(new HashMap<>(), ac.getActivitiesSince(arr, "test"));

        when(ss.sessionExists("test")).thenReturn(true);
        assertEquals(new HashMap<>(), ac.getActivitiesSince(arr, "test"));

        arr.add("user");
        ArrayList<Activity> act = new ArrayList<>();
        act.add(test_activity);
        when(as.getActivitiesOfUserSince("user", Date.valueOf(LocalDate.now().minusDays(7)))).thenReturn(act);
        HashMap<String, ArrayList<Activity>> exp = new HashMap<>();
        exp.put("user", act);
        assertEquals(exp, ac.getActivitiesSince(arr, "test"));

    }
}
