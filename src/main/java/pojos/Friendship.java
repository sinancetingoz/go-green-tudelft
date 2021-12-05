package pojos;

import javafx.scene.control.Button;

public class Friendship {


    private String sender;
    private String receiver;
    private Boolean accepted;
    private Button accept;
    private Button decline;


    public Friendship() {
        this.accepted = false;
    }

    /**
     * Creates a new friendship with given parameters.
     * @param requester the sender of the friend request
     * @param receiver the receiver of the friend request
     */
    public Friendship(String requester, String receiver) {
        this.sender = requester;
        this.receiver = receiver;
        this.accepted = false;

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String requester) {
        this.sender = requester;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Boolean getStatus() {
        return accepted;
    }

    public void setStatus(Boolean status) {
        this.accepted = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Friendship) {
            Friendship that = (Friendship) obj;

            if (this.sender.equals(that.sender)
                    && this.receiver.equals(that.receiver)
                    && this.accepted == that.accepted) {
                return true;
            }
        }

        return false;
    }
}
