package database;

import database.AccountDao;
import database.ActivityDao;
import org.junit.Before;
import org.junit.Test;
import pojos.Account;
import pojos.Activity;
import pojos.Category;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import static junit.framework.TestCase.assertTrue;

public class ActivityDaoTest {
    ActivityDao ad = new ActivityDao();
    AccountDao add = new AccountDao();
    Account testaccount;
    Activity testactivity;

    @Before
    public void configure()
    {
        add.changeDatabase("test");
        ad.changeDatabase("test");
        testaccount = new Account("test","test","test","test","test");
        testactivity = new Activity("vegetarian", Category.food, 100 , Date.valueOf(LocalDate.now()),"test");
    }

    @Test
    public void testAddGetActivities() throws SQLException
    {
        //ad.getActivities("test");
        assertTrue(ad.getActivities("test").size() == 0);
        add.createAccount(testaccount);
        ad.createActivity(testactivity);
        assertTrue(ad.getActivities("test").size() == 1);
        ad.removeActivity(testactivity);
        assertTrue(ad.getActivities("test").size() == 0);
        add.deleteAccount(testaccount);
    }
}
