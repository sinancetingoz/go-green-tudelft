package pojos;

import java.time.LocalDateTime;

public class Session {

    private String username;
    private LocalDateTime time;

    /** Creating a new sessionID.
     * @param username the user for which the sessionID is created
     * @param now the time of the creation of the sessionID
     */
    public Session(String username, LocalDateTime now) {

        this.time = now;
        this.username = username;

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Session) {
            Session that = (Session) obj;

            if (this.username.equals(that.username)
                    && this.time.equals(that.time)) {
                return true;
            }
        }

        return false;
    }
}
