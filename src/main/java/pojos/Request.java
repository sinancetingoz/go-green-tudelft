package pojos;

import javafx.scene.control.Button;

public class Request {
    String username;
    Button accept;
    Button reject;

    /**
     * constructor for a request.
     * @param user user getting the request
     * @param accept the button to accept the request
     * @param reject the button to reject the request
     */
    public Request(String user, Button accept, Button reject) {
        this.username = user;
        this.accept = accept;
        accept.setText("Accept");
        this.reject = reject;
        reject.setText("Decline");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Button getAccept() {
        return accept;
    }

    public void setAccept(Button accept) {
        this.accept = accept;
    }

    public Button getReject() {
        return reject;
    }

    public void setReject(Button reject) {
        this.reject = reject;
    }
}
