package pojos;

import org.junit.Test;
import pojos.Activity;

import static org.junit.Assert.*;

import pojos.Category;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.Before;

public class ActivityTest {
    Activity activity;

    @Before
    public void initialize() {
        activity = new Activity();
    }

    @Test
    public void TestDescription() {
        assertEquals(activity.getDescription(), "meal");
    }

    @Test
    public void TestCatagory() {
        assertEquals(activity.getCategory(), null);
    }

    @Test
    public void TestPoints() {
        assertEquals(activity.getPoints(), 10);
    }

    @Test
    public void TestDate() {
        activity.getDate().equals(Date.valueOf(LocalDate.now()));
    }

    @Test
    public void TestUsername() {
        assertEquals(activity.getUsername(), "test");
    }

    @Test
    public void TestSetDescription() {
        activity.setDescription("Cycling");
        assertEquals(activity.getDescription(), "Cycling");
    }

    @Test
    public void TestSetCatagory() {
        activity.setCategory(Category.food);
        assertEquals(activity.getCategory(), Category.food);
    }

    @Test
    public void TestSetPoints() {
        activity.setPoints(30);
        assertEquals(activity.getPoints(), 30);
    }

    @Test
    public void TestSetDate() {
        Date x = new Date(0);
        activity.setDate(x);
        ;
        assertEquals(activity.getDate(), x);
    }

    @Test
    public void TestSetUsername() {
        activity.setUsername("Meepo");
        assertEquals(activity.getUsername(), "Meepo");
    }

    @Test
    public void TestEqualsTrue() {
        Activity activity2 = new Activity();
        assertEquals(activity, activity2);
    }

    @Test
    public void TestEqualsFalse() {
        Date x = new Date(0);
        Activity activity2 = new Activity("Cycling", null, 40, x, "Meepo");
        assertFalse(activity.equals(activity2));
    }

    @Test
    public void testEqualsFalse() {
        int var = 42;
        assertNotEquals(activity, var);
    }

    @Test
    public void testEqualsTrue1() {
        assertEquals(activity, activity);
    }

    @Test
    public void testEqualsFalse1() {
        Date date = Date.valueOf(LocalDate.now());
        activity = new Activity("Vegetarian meal", Category.food, 100, date, "Test");
        Activity activity1 = new Activity("Biological meal", Category.food, 100, date, "Test");
        assertNotEquals(activity1, activity);
    }

    @Test
    public void testEqualsFalse2() {
        Date date = Date.valueOf(LocalDate.now());
        activity = new Activity("Vegetarian meal", Category.food, 100, date, "Test");
        Activity activity1 = new Activity("Vegetarian meal", Category.energy, 100, date, "Test");
        assertNotEquals(activity1, activity);
    }

    @Test
    public void testEqualsFalse3() {
        Date date = Date.valueOf(LocalDate.now());
        activity = new Activity("Vegetarian meal", Category.food, 100, date, "Test");
        Activity activity1 = new Activity("Vegetarian meal", Category.food, 128, date, "Test");
        assertNotEquals(activity1, activity);
    }

    @Test
    public void testEqualsFalse4() {
        Date date = Date.valueOf(LocalDate.now());
        activity = new Activity("Vegetarian meal", Category.food, 100, date, "Test");
        Activity activity1 = new Activity("Vegetarian meal", Category.food, 100, new Date(0), "Test");
        assertNotEquals(activity1, activity);
    }

    @Test
    public void testEqualsFalse5() {
        Date date = Date.valueOf(LocalDate.now());
        activity = new Activity("Vegetarian meal", Category.food, 100, date, "Test");
        Activity activity1 = new Activity("Vegetarian meal", Category.food, 100, date, "User");
        assertNotEquals(activity1, activity);
    }
}
