package services;

import database.AccountDao;
import org.junit.Test;
import pojos.Account;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TemperatureServiceTest {

    @Test
    public void testInit() {
        Account account = new Account("test", "test", "test"    , "test", "test");
        //AccountDao ad = new AccountDao();
        //ad.changeDatabase("test");
        AccountService as = mock(AccountService.class);
        //as.setDb(ad);
        when(as.getAccounts()).thenReturn(new ArrayList<>());
        TemperatureService.init();

        ArrayList<Account> exp = new ArrayList<>();
        exp.add(account);
        when(as.getAccounts()).thenReturn(exp);
        TemperatureService.init();

        exp.clear();
        account.setHasHeating(true);
        exp.add(account);
        TemperatureService.init();
    }

}