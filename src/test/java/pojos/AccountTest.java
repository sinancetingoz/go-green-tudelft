package pojos;

import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;

public class AccountTest {

    Account account;

    @Before
    public void intiliaze() {
        account = new Account("user", "user@mail.com", "123", "User", "User");
    }

    @Test
    public void testConstructor() {
        assertEquals(account.getUsername(), "user");
    }

    @Test
    public void testGetUsername() {
        assertEquals(account.getUsername(), "user");
    }

    @Test
    public void testSetUsername() {
        account.setUsername("user1");
        assertEquals(account.getUsername(), "user1");
        account.setUsername("user");
    }

    @Test
    public void testGetMail() {
        assertEquals(account.getMail(), "user@mail.com");
    }

    @Test
    public void testSetMail() {
        account.setMail("user1@mail.com");
        assertEquals(account.getMail(), "user1@mail.com");
        account.setMail("user@mail.com");
    }

    @Test
    public void testGetPassword() {
        assertEquals(account.getPassword(), "123");
    }

    @Test
    public void testSetPassword() {
        account.setPassword("234");
        assertEquals(account.getPassword(), "234");
        account.setPassword("123");
    }

    @Test
    public void testGetName() {
        assertEquals(account.getFirstName(), "User");
    }

    @Test
    public void testSetName() {
        account.setFirstName("User1");
        assertEquals(account.getFirstName(), "User1");
        account.setFirstName("User");
    }

    @Test
    public void testGetLastName() {
        assertEquals(account.getLastName(), "User");
    }

    @Test
    public void testSetLastName() {
        account.setLastName("User1");
        assertEquals(account.getLastName(), "User1");
        account.setLastName("User");
    }

    @Test
    public void testEqualsTrue() {
        Account account1 = new Account("user", "user@mail.com", "123", "User", "User");
        assertEquals(account, account1);
    }

    @Test
    public void testEqualsFalse1() {
        int var = 42;
        assertNotEquals(account, var);
    }

    @Test
    public void testEqualsFalse2() {
        Account account1 = new Account("user1", "user@mail.com", "123", "User", "User");
        assertNotEquals(account, account1);
    }

    @Test
    public void testEqualsFalse3() {
        Account account1 = new Account("user", "user1@mail.com", "123", "User", "User");
        assertNotEquals(account, account1);
    }

    @Test
    public void testEqualsFalse4() {
        Account account1 = new Account("user", "user@mail.com", "1234", "User", "User");
        assertNotEquals(account, account1);
    }

    @Test
    public void testEqualsFalse5() {
        Account account1 = new Account("user", "user@mail.com", "123", "User1", "User");
        assertNotEquals(account, account1);
    }

    @Test
    public void testEqualsFalse6() {
        Account account1 = new Account("user", "user@mail.com", "123", "User", "User1");
        assertNotEquals(account, account1);
    }

    @Test
    public void testEqualsFalse7() {
        Account account1 = new Account("user", "user@mail.com", "123", "User", "User");
        account1.setSavedEnergy(305);
        assertNotEquals(account, account1);
    }

    @Test
    public void testGetLevel() {
        assertEquals(1, account.getLevel());
        account.setPoints(2000);
        assertEquals(2, account.getLevel());
    }

    @Test
    public void testGetLevelMul() {
        assertEquals(0, account.getLevelMul(0));
        assertEquals(1, account.getLevelMul(2));
    }
}
