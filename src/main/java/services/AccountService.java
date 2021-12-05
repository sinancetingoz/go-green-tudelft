package services;

import database.AccountDao;
import pojos.Account;
import util.HashPassword;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountService {

    private AccountDao db = new AccountDao();


    /**
     * Checks whether the inputted username and password are valid.
     *
     * @param username the inputted username
     * @param password the inputted password
     * @return true if the log in was successful, false otherwise
     */
    public boolean checkLogin(String username, String password) {
        try {
            if (db.exists(username)) {
                String expPass = db.getAccount(username).getPassword();
                return password.equals(expPass);
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }


    /**
     * Creates an account.
     *
     * @param acc the account to be created
     * @return true if the account was successfully created, false otherwise
     */
    public boolean createAccount(Account acc) {
        try {
            if (db.exists(acc.getUsername())) {
                return false;
            }
            return db.createAccount(acc);
        } catch (SQLException e) {
            return false;
        }
    }


    /**
     * Checks whether a user exists.
     *
     * @param user the user who is checked to exist
     * @return true if the user exists, false otherwise
     */
    public boolean userExists(String user) {
        try {
            return db.exists(user);
        } catch (SQLException e) {
            return false;
        }
    }


    public void setDb(AccountDao db) {
        this.db = db;
    }

    /**
     * Returns the account information of a user.
     * @param account the account we are looking for
     * @return an Account object containing the necessary information
     */
    public Account getAccount(String account) {
        try {
            return db.getAccount(account);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Returns the account information of a user.
     * @param email of the account we are looking for
     * @return an Account object containing the necessary information
     */
    public Account getEmail(String email) {
        try {
            return db.getEmail(email);
        } catch (SQLException e) {
            return null;
        }
    }




    /**
     * Update the points of a user.
     * @param user the user whose points need to be updated
     * @param points the number of points to be added
     * @return true if the process was successful, false otherwise
     */
    public boolean updatePoints(String user, int points) {
        try {
            return db.updatePoints(user, points);
        } catch (SQLException e) {
            return false;
        }
    }


    /**
     * Sets a new password.
     * @param user the user that chenges the password
     * @param password the new password
     * @return weather the password changed successfully or not
     */
    public boolean updatePassword(String user, String password) {
        try {
            password = HashPassword.hashPass(password);
            return db.updatePassword(user,password);
        } catch (SQLException e) {
            return false;
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

    /**
     * Gets an arraylist of all accounts.
     * @return an arraylist of all accounts
     */
    public ArrayList<Account> getAccounts() {
        try {
            return db.getAccounts();
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Deletes specified account.
     * @param account account to be deleted
     */
    public void deleteAccount(Account account) {
        try {
            db.deleteAccount(account);
        } catch (SQLException e) {
            return;
        }
    }

    /**
     * Sets energy of specified account.
     * @param account account to have their energy set
     * @param energy energy value to be set
     * @return true or false depending on whether the method was successful
     */
    public boolean setEnergy(String account, int energy) {
        try {
            return db.setEnergy(account, energy);
        } catch (SQLException e) {
            return false;
        }
    }


    /**
     * Sets the heating status of a user.
     * @param user the user whose status we want to update
     * @param heating the heating status
     * @return true if the update was successful, false otherwise
     */
    public boolean setHeating(String user, boolean heating) {
        try {
            return db.setHeating(user, heating);
        } catch (SQLException e) {
            return false;
        }
    }


    /**
     * Resets the heating status of all users.
     * @return true if the reset was successful, false otherwise
     */
    public boolean resetHeating() {
        try {
            ArrayList<Account> accounts = db.getAccounts();

            for (Account account : accounts) {
                if (!db.setHeating(account.getUsername(), false)) {
                    return false;
                }
            }

            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}


