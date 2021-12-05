package pojos;

import java.io.File;

public class Account {
    private String username;
    private String mail;
    private String password;
    private String firstname;
    private String lastname;
    private int points;
    private int savedEnergy;
    private boolean hasHeating;
    private File picture;

    /**
     * Creates an empty account.
     */
    public Account() {
        this.username = "";
        this.mail = "";
        this.password = "";
        this.firstname = "";
        this.lastname = "";
        this.points = 0;
        this.savedEnergy = 0;
        this.hasHeating = false;
        this.picture = null;
    }




    /**
     * Making a new user.
     *
     * @param username the username of the user
     * @param mail     the email of the user
     * @param password the password of the user
     * @param name     first name of the user
     * @param surname  last name of the user
     */
    public Account(String username, String mail, String password, String name, String surname) {
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.firstname = name;
        this.lastname = surname;
        this.points = 0;
        this.savedEnergy = 0;
        this.hasHeating = false;
        this.picture = new File("ivaylo.jpg");
    }


    public File getPicture() { 
        return picture; 
    }

    public void setPicture(File picture) {
        this.picture = picture;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String name) {
        this.firstname = name;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String surname) {
        this.lastname = surname;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getSavedEnergy() {
        return savedEnergy;
    }

    public void setSavedEnergy(int savedEnergy) {
        this.savedEnergy = savedEnergy;
    }

    public boolean hadHeating() {
        return hasHeating;
    }

    public void setHasHeating(boolean hasHeating) {
        this.hasHeating = hasHeating;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Account) {
            Account that = (Account) obj;

            if (this.username.equals(that.username)
                    && this.mail.equals(that.mail)
                    && this.password.equals(that.password)
                    && this.firstname.equals(that.firstname)
                    && this.lastname.equals(that.lastname)
                    && this.savedEnergy == that.savedEnergy) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the level of the account according to his/her points.
     * @return the level of the user
     */
    public int getLevel() {
        int lvl = 1;
        int fac = 2;
        int points = 1000;

        while (this.points >= points) {
            ++ lvl;
            points += 1000 * fac;
            ++ fac;
        }

        return lvl;
    }

    /**
     * Converts the level of a user into a number used to display the points on their home page.
     * @param lvl the current level to convert.
     * @return Num to reset the num points to zero without reducing actual points of user.
     */
    public int getLevelMul(int lvl) {
        int ret = 0;
        for (int i = 1; i < lvl; i++) {
            ret = ret + i;
        }
        return ret;
    }
}
