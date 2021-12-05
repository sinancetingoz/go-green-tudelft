package gui;

import client.ConnectAccount;
import client.ConnectFriends;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import org.controlsfx.control.textfield.TextFields;
import pojos.Account;
import pojos.Friendship;
import pojos.Request;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerFriends implements Initializable {


    @FXML
    private BorderPane rootPane;
    @FXML
    private TextField friendsField;
    @FXML
    private Label errorLabel;
    @FXML
    private VBox myFriendsContainer;
    @FXML
    private VBox friendReqContainer;

    private ArrayList<String> usernames;

    private ObservableList<Request> friendReqs;

    private ObservableList<Account> myFriends;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorLabel.setVisible(false);

        myFriends = getFriends();

        for (int i = 0; i < myFriends.size(); i++) {
            Account friend = myFriends.get(i);

            myFriendsContainer.getChildren().add(newMyFriend(
                    friend.getUsername(),
                    Integer.toString(friend.getPoints())));
        }

        friendReqs = getPendings();

        for (int i = 0; i < friendReqs.size(); i++) {
            Request friendReq = friendReqs.get(i);

            friendReqContainer.getChildren().add(newFriendReq(
                    friendReq));
        }

    }
    
    /**
     * Displays the pending requests of the current user.
     * @return an observable list containing all activities
     */
    public ObservableList<Request> getPendings() {
        ObservableList<Request> requests = FXCollections.observableArrayList();
        ArrayList<String> pendings = ConnectFriends.getPendingFriends();
        for (String user: pendings) {
            Button accept = new Button();
            Button reject = new Button();
            reject.setOnAction(e -> {
                ConnectFriends.removeFriend(new Friendship(user,ConnectAccount.getUsername()));
                removeRequest(user, false);
            });
            accept.setOnAction(e -> {
                ConnectFriends.acceptRequest(user);
                removeRequest(user, true);
            });

            requests.add(new Request(user,accept,reject));
        }
        return requests;
    }

    /**
     * Removes a request from a user.
     * @param user the user to be removed from the pending requests list
     */
    public void removeRequest(String user, boolean accept) {
        for (int i = 0; i < friendReqs.size(); i++) {
            if (friendReqs.get(i).getUsername().equals(user)) {
                friendReqs.remove(i);
                break;
            }
        }

        friendReqContainer.getChildren().clear();

        for (int i = 0; i < friendReqs.size(); i++) {
            Request friendReq = friendReqs.get(i);

            friendReqContainer.getChildren().add(newFriendReq(
                    friendReq));
        }

        if (accept == true) {
            myFriends.add(ConnectAccount.getFriendAccount(user));

            myFriendsContainer.getChildren().clear();

            for (int i = 0; i < myFriends.size(); i++) {
                Account friend = myFriends.get(i);

                myFriendsContainer.getChildren().add(newMyFriend(
                        friend.getUsername(),
                        Integer.toString(friend.getPoints())));
            }
        }

    }

    /**
     * Makes a request to get matching users (user suggestions).
     */
    public void getUsers() {
        errorLabel.setVisible(false);
        if (friendsField.getText().length() == 1) {
            String match = friendsField.getText();
            usernames = ConnectAccount.getMatchingUsers(match);
            TextFields.bindAutoCompletion(friendsField, usernames);
        } else if (friendsField.getText().length() < 1) {
            usernames = new ArrayList<>();
            TextFields.bindAutoCompletion(friendsField, usernames);
        }
    }

    /**
     * Gets all friends of the current user.
     * @return an observable list containing all friends
     */
    public ObservableList<Account> getFriends() {
        ObservableList<Account> friends = FXCollections.observableArrayList();
        ArrayList<Account> allFriends = ConnectFriends.getFriendsForList();

        for (Account friend: allFriends) {
            friends.add(friend);
        }
        return  friends;
    }

    /**
     * Loads the feed.
     *
     * @param actionEvent the event needed to be made to go to the feed
     * @throws IOException when there is an error in the action
     */
    public void loadMyLog(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("MyLog.fxml"));
        rootPane.getChildren().setAll(pane);
    }


    /**
     * Sends a request.
     *
     * @param actionEvent the event needed to send a request
     * @throws IOException when there is an error in the action
     */
    public void sendRequest(javafx.event.ActionEvent actionEvent) {
        if (ConnectFriends.sendRequest(friendsField.getText())) {
            errorLabel.setStyle("-fx-text-fill: green");
            errorLabel.setText("Friend request sent SUCCESS");
        } else {
            errorLabel.setStyle("-fx-text-fill: red");
            errorLabel.setText("Friend request sent FAILED");
            if (usernames.contains(friendsField.getText())) {
                errorLabel.setText("Friend request already sent");
            }
        }
        errorLabel.setVisible(true);
    }

    /**
     * The message to be displayed on the home screen.
     * @param root the window of the application
     */
    public static void welcomeMessage(Parent root) {
        javafx.scene.control.Label welcome = (Label) root.lookup("#Welcome");
        if (welcome != null) {
            welcome.setText("Welcome " + ConnectAccount.getUsername());
        }
    }

    /**
     * Loads the activities window.
     * @param actionEvent the action on which the activity window needs to open
     * @throws IOException when there is a problem in the action event
     */
    public void loadActivities(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane = FXMLLoader.load(
                getClass().getClassLoader().getResource("Activities.fxml"));

        rootPane.getChildren().setAll(pane);
    }

    /**
     * Loads the statistics page.
     * @param actionEvent the action event on which the statistics page should be displayed
     * @throws IOException when something with the action event goes wrong
     */
    public void loadStatistics(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane = FXMLLoader.load(
                getClass().getClassLoader().getResource("Statistics.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**
     * Loads the friends page.
     * @param actionEvent the action event on which the friends page should be displayed
     * @throws IOException when there is an error in the action
     */
    public void loadFriends(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Friends.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**
     * Logs the user out.
     * @throws IOException when something with the event went wrong
     */
    public void logOut() throws IOException {
        ConnectAccount.logOut();
        BorderPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
        rootPane.getChildren().setAll(pane);
    }


    /**
     * Loads the home page.
     *
     * @param actionEvent the event needed to be made to go to the home page
     * @throws IOException when there is an error in the action
     */
    public void loadHome(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Home.fxml"));
        ControllerHome.welcomeMessage(pane);
        rootPane.getChildren().setAll(pane);
    }

    /**
     * Creates a new log on the screen.
     * @param friendUsername the description
     * @param points the number of points gained
     * @return an AnchorPane
     */
    public AnchorPane newMyFriend(String friendUsername, String points) {
        AnchorPane myFriendsLayout = new AnchorPane();
        myFriendsLayout.setPrefSize(375, 86);

        Rectangle bigRect = new Rectangle(350, 70);
        bigRect.setStyle("-fx-fill: white; -fx-stroke: #9bc782;");
        bigRect.setLayoutX(13.0);
        bigRect.setLayoutY(8.0);

        Rectangle smallRect = new Rectangle(90, 70);
        smallRect.setStyle("-fx-fill: #9bc782;");
        smallRect.setLayoutX(273.0);
        smallRect.setLayoutY(8.0);

        Label username = new Label(friendUsername);
        username.setStyle("-fx-font-size: 26px; -fx-font-family: 'Arial Rounded MT Bold';");
        username.setLayoutX(28.0);
        username.setLayoutY(27.0);

        Label userNumPoints = new Label(points);
        userNumPoints.setStyle("-fx-font-size: 24px; -fx-alignment: center; "
                + "-fx-font-family: 'Arial Rounded MT Bold'; -fx-text-fill: white;");
        userNumPoints.setPrefHeight(46.0);
        userNumPoints.setPrefWidth(90.0);
        userNumPoints.setLayoutX(272.0);
        userNumPoints.setLayoutY(9.0);

        Label logPoints = new Label("POINTS");
        logPoints.setStyle("-fx-font-family: 'Arial Rounded MT Bold'; -fx-text-fill: white");
        logPoints.setLayoutX(289.0);
        logPoints.setLayoutY(51.0);

        myFriendsLayout.getChildren().addAll(bigRect, smallRect, username,
                userNumPoints, logPoints);

        return myFriendsLayout;
    }

    /**
     * Creates a new log on the screen.
     * @param friendReq Request object
     * @return an AnchorPane
     */
    public AnchorPane newFriendReq(Request friendReq) {
        AnchorPane friendReqLayout = new AnchorPane();
        friendReqLayout.setPrefSize(375, 86);

        Rectangle bigRect = new Rectangle(350, 70);
        bigRect.setStyle("-fx-fill: white; -fx-stroke: #9bc782;");
        bigRect.setLayoutX(13.0);
        bigRect.setLayoutY(8.0);

        final String buttonStyle = "-fx-background-color: #9bc782; -fx-background-radius: 0;";
        final String buttonHover = "-fx-background-color: #80a469; -fx-background-radius: 0;";

        Image check = new Image("/Images/check.png");
        ImageView checkView = new ImageView(check);
        checkView.setFitHeight(30);
        checkView.setFitWidth(30);
        checkView.preserveRatioProperty();

        Button acceptRequest = friendReq.getAccept();
        acceptRequest.setGraphic(checkView);
        acceptRequest.setText("");
        acceptRequest.setStyle(buttonStyle);
        acceptRequest.setOnMouseEntered(e -> acceptRequest.setStyle(buttonHover));
        acceptRequest.setOnMouseExited(e -> acceptRequest.setStyle(buttonStyle));
        acceptRequest.setPrefWidth(70);
        acceptRequest.setPrefHeight(70);
        acceptRequest.setLayoutX(219.0);
        acceptRequest.setLayoutY(8.0);

        Image bin = new Image("/Images/bin.png");
        ImageView binView = new ImageView(bin);
        binView.setFitHeight(40);
        binView.setFitWidth(40);
        binView.preserveRatioProperty();

        Button rejectReq = friendReq.getReject();
        rejectReq.setGraphic(binView);
        rejectReq.setText("");
        rejectReq.setStyle(buttonStyle);
        rejectReq.setOnMouseEntered(e -> rejectReq.setStyle(buttonHover));
        rejectReq.setOnMouseExited(e -> rejectReq.setStyle(buttonStyle));
        rejectReq.setPrefWidth(70);
        rejectReq.setPrefHeight(70);
        rejectReq.setLayoutX(293.0);
        rejectReq.setLayoutY(8.0);

        Label username = new Label(friendReq.getUsername());
        username.setStyle("-fx-font-size: 26px; -fx-font-family: 'Arial Rounded MT Bold';");
        username.setLayoutX(28.0);
        username.setLayoutY(27.0);

        friendReqLayout.getChildren().addAll(bigRect, rejectReq,
                acceptRequest, username);

        return friendReqLayout;
    }
}
