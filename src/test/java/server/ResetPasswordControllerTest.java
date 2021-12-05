package server;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import pojos.Account;
import services.AccountService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResetPasswordControllerTest {

    Account account;
    AccountService accountService;
    ResetPasswordController rpc;

    @Before
    public void init() {
        account = new Account("user", "user", "user", "user", "user");
        accountService = mock(AccountService.class);
        rpc = new ResetPasswordController();
        rpc.setAs(accountService);
    }

    @Test
    public void testRecoverPassword() {
        when(accountService.getEmail("user")).thenReturn(null);
        assertFalse(rpc.recoverPassword("user"));

        when(accountService.getEmail("user")).thenReturn(account);
        assertTrue(rpc.recoverPassword("user"));
    }
}