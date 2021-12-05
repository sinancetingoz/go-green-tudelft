package util;

import client.ConnectActivity;
import database.AccountDao;
import org.junit.Before;
import org.junit.Test;
import pojos.Account;
import services.AccountService;

import static org.junit.Assert.*;

public class AddSolarPointsTest {

    AccountService as = new AccountService();
    AccountDao ad = new AccountDao();
    Account account;

    @Before
    public void init() {
        ad.changeDatabase("test");
        as.setDb(ad);
        account = new Account("test", "test", "test", "test", "test");
    }

    @Test
    public void testAddPoints() {
        AddSolarPoints.addPoints();
        as.createAccount(account);
        AddSolarPoints.addPoints();
        as.deleteAccount(account);
    }

}