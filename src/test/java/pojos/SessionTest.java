package pojos;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SessionTest {

    Session session;
    LocalDateTime date;

    @Before
    public void initialize() {
        date = LocalDateTime.now();
        session = new Session("user1", date);
    }

    @Test
    public void testConstructor() {
        assertEquals(session.getUsername(), "user1");
    }

    @Test
    public void testGetUsername() {
        assertEquals(session.getUsername(), "user1");
    }

    @Test
    public void testGetTime() {
        assertEquals(session.getTime(), date);
    }

    @Test
    public void testSetUsername() {
        session.setUsername("user2");
        assertEquals(session.getUsername(), "user2");
        session.setUsername("user1");
    }

    @Test
    public void testSetTime() {
        LocalDateTime time = LocalDateTime.MAX;
        session.setTime(time);
        assertEquals(session.getTime(), time);
        session.setTime(date);
    }

    @Test
    public void testEqualsFalse1() {
        int var = 42;
        assertNotEquals(session, var);
    }

    @Test
    public void testEqualsFalse2() {
        Session session1 = new Session("user2", date);
        assertNotEquals(session, session1);
    }

    @Test
    public void testEqualsFalse3() {
        LocalDateTime time = LocalDateTime.MAX;
        Session session1 = new Session("user1", time);
        assertNotEquals(session, session1);
    }

    @Test
    public void testEqualsTrue() {
        Session session1 = new Session("user1", date);
        assertEquals(session, session1);
    }
}
