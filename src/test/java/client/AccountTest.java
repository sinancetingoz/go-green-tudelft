package client;

import org.junit.Before;
import org.junit.Test;
import pojos.Account;

import static org.junit.Assert.*;

public class AccountTest {

    Account account;

    @Before public void initialize() {
        account = new Account("john", "john@mail.com", "alala", "John", "Baker");
    }

    @Test
    public void testConstructor() {
        assertEquals("john", account.getUsername());
    }

    @Test
    public void testUserName() {
        assertEquals("john", account.getUsername());
    }

    @Test
    public void testMail() {
        assertEquals("john@mail.com", account.getMail());
    }

    @Test
    public void testPassword() {
        assertEquals("alala", account.getPassword());
    }

    @Test
    public void testName() {
        assertEquals("John", account.getFirstName());
    }

    @Test
    public void testSurname() {
        assertEquals("Baker", account.getLastName());
    }

    @Test
    public void testSetUserName() {
        account.setUsername("jack");
        assertEquals("jack", account.getUsername());
    }

    @Test
    public void testSetMail() {
        account.setMail("jack@mail.com");
        assertEquals("jack@mail.com", account.getMail());
    }

    @Test
    public void testSetPassword() {
        account.setPassword("baba");
        assertEquals("baba", account.getPassword());
    }

    @Test
    public void testSetName() {
        account.setFirstName("Jack");
        assertEquals("Jack", account.getFirstName());
    }

    @Test
    public void testSetSurname() {
        account.setLastName("Cooker");
        assertEquals("Cooker", account.getLastName());
    }

    @Test
    public void testDefaultConstructor() {
        account = new Account();
        assertNotNull(account);
    }
}