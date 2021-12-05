package gui;

import client.ConnectAccount;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import pojos.Account;
import pojos.DefaultValue;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerHome implements Initializable {

    protected static Account myAccount;
    @FXML
    private BorderPane rootPane;

    @FXML
    private ProgressBar progressbar;

    @FXML
    private Label level;

    @FXML
    private Label welcome1;
    
    @FXML
    private Label levelPointsLabel;
    
    @FXML
    private Region region;


    @FXML
    private Label welcome11;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setProgressbar();
    }

    /**
     * Loads the log page.
     * @param actionEvent the event triggered when the log page loads
     * @throws IOException exception
     */
    public void loadMyLog(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("MyLog.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**
     * The message to be displayed on the home screen.
     * @param root the window of the application
     */
    public static void welcomeMessage(Parent root) {
        javafx.scene.control.Label welcome = (Label) root.lookup("#Welcome");
        if (welcome != null) {
            welcome.setText("Welcome " + ConnectAccount.getMyAccount().getFirstName() + "!");
        }
    }


    /** Loads the home page.
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
     * @throws IOException when something with the action event goes wrong
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
     * Sets the progressbar.
     */
    public void setProgressbar() {
        progressbar.setProgress(0);
        int lvl = myAccount.getLevel();
        int accPoints = myAccount.getPoints();
        welcome1.setText("Total CO\u2082 saved: "
                + String.format("%.1f", DefaultValue.converter(accPoints) * 1000) + " kg");

        welcome11.setText("Total points: " + accPoints);
        level.setText("Level " + lvl);
        KeyValue keyValue = new KeyValue(progressbar.progressProperty(),
                ((double) accPoints - (double) 500 * lvl * (lvl - 1))
                        / (double) (1000 * lvl));
        KeyFrame keyFrame = new KeyFrame(new Duration(1000), keyValue);
        levelPointsLabel.setText(
                (myAccount.getPoints() - (myAccount.getLevelMul(lvl) * 1000) )
                        +  " / " + (lvl  * 1000));
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

    }
}
