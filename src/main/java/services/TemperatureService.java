package services;

import client.ConnectAccount;
import pojos.Account;

import java.util.ArrayList;
import java.util.HashMap;

public class TemperatureService {

    private static HashMap<String, Boolean> doneToday;

    static {
        doneToday = new HashMap<>();
    }

    /**
     * Checks whether a user has already set temperature for today.
     * @param user the user to check about
     * @return true if he set temperature for today, false otherwise
     */
    public static boolean ifSavedToday(String user) {
        return doneToday.containsKey(user);
    }

    /**
     * Adds user who set temperature for today to the list.
     * @param user the user to add
     */
    public static void didToday(String user) {
        doneToday.put(user, true);
        ConnectAccount.setHeating(true);
    }


    /**
     * Initializes the list when starting the application.
     */
    public static void init() {
        AccountService as = new AccountService();
        ArrayList<Account> accounts = as.getAccounts();
        for (Account account : accounts) {
            if (account.hadHeating()) {
                doneToday.put(account.getUsername(), true);
            }
        }
    }
}
