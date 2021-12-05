package util;

import pojos.Account;
import pojos.Activity;
import pojos.Category;
import pojos.DefaultValue;
import services.AccountService;
import services.ActivityService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class AddSolarPoints {

    public static void main(String[] args) {
        addPoints();
    }

    /**
     * Adds points to account for having solarpanels.
     */
    public static void addPoints() {
        ActivityService acts = new ActivityService();
        AccountService accs = new AccountService();

        ArrayList<Account> all = accs.getAccounts();
        DefaultValue.initPts();

        for (Account acc : all) {
            Activity act = new Activity("Power saved by solar panels",
                    Category.energy,
                    DefaultValue.kwhToPoints(acc.getSavedEnergy()),
                    Date.valueOf(LocalDate.now()),
                    acc.getUsername());

            acts.createActivity(act);
        }
    }
}
