package gui;

import client.ConnectAccount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import pojos.Activity;
import pojos.Category;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ControllerMyLog implements Initializable {

    protected static ArrayList<pojos.Activity> myActivities;
    @FXML
    private BorderPane rootPane;
    @FXML
    private VBox feed;
    @FXML
    private ComboBox filter;
    @FXML
    private Button applyButton;
    @FXML
    private Label noLogsMessage;
    @FXML
    private Button addActivityButton;
    @FXML
    private VBox noActivitiesBox;

    private String option = null;
    
    @FXML
    private Region region;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        noLogsMessage.setManaged(false);
        addActivityButton.setManaged(false);
        noActivitiesBox.setManaged(false);

        option = null;
        applyButton.setText("Apply filter");
        ArrayList<String> options = new ArrayList<>();
        options.add("food");
        options.add("transportation");
        options.add("energy");
        ObservableList<String> filters = FXCollections.observableArrayList();
        filters.setAll(options);
        filter.setItems(filters);
        ObservableList<Activity> activities = getActivity();

        if (activities.size() == 0) {
            noLogsMessage.setManaged(true);
            addActivityButton.setManaged(true);
            noActivitiesBox.setManaged(true);
        } else {
            for (int i = 0; i < activities.size(); i++) {
                Activity activity = activities.get(i);

                feed.getChildren().add(newLog(
                        activity.getCategory(),
                        activity.getDescription(),
                        activity.getDate().toString(),
                        Integer.toString(activity.getPoints())));
            }
        }
    }

    /** Sets the Feed.
     * @throws IOException when there is an error in the action
     */
    public void setFeed() {
        noLogsMessage.setManaged(false);
        addActivityButton.setManaged(false);
        noActivitiesBox.setManaged(false);
        noLogsMessage.setVisible(false);
        addActivityButton.setVisible(false);
        noActivitiesBox.setVisible(false);

        feed.getChildren().clear();
        ObservableList<Activity> activities = getActivity();

        if (activities.size() == 0) {
            noLogsMessage.setManaged(true);
            addActivityButton.setManaged(true);
            noActivitiesBox.setManaged(true);
            noLogsMessage.setVisible(true);
            addActivityButton.setVisible(true);
            noActivitiesBox.setVisible(true);
        } else {
            for (int i = 0; i < activities.size(); i++) {
                Activity activity = activities.get(i);

                feed.getChildren().add(newLog(
                        activity.getCategory(),
                        activity.getDescription(),
                        activity.getDate().toString(),
                        Integer.toString(activity.getPoints())));
            }
        }
    }

    /** Loads the home page.
     * @param actionEvent the event needed to be made to go to the home page
     * @throws IOException when there is an error in the action
     */
    public void loadHome(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Home.fxml"));
        ControllerHome.welcomeMessage(pane);
        rootPane.getChildren().setAll(pane);
    }

    /**
     * Loads the log page.
     * @param actionEvent the event needed to be made to go to refresh the page
     * @throws IOException when there is an error in the action
     */
    public void loadMyLog(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("MyLog.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**
     * Loads the activity page on action.
     * @param actionEvent the action on which the activity page is loaded
     * @throws IOException when there is an error in the action
     */
    public void loadActivities(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane =
                FXMLLoader.load(getClass().getClassLoader().getResource("Activities.fxml"));
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
     * Applies a filter to the activities list.
     * @param actionEvent the action event on which the friends page should be displayed
     * @throws IOException when something with the action event goes wrong
     */
    public void applyFilter(javafx.event.ActionEvent actionEvent) throws IOException {
        if (filter.getValue().equals(option)) {
            option = null;
            applyButton.setText("Apply filter");
        } else {
            option = (String)filter.getValue();
            applyButton.setText("Remove filter");
        }
        setFeed();
    }

    /**
     * Chooses the filter to apply to the activities list.
     * @param actionEvent the action event on which the friends page should be displayed
     * @throws IOException when something with the action event goes wrong
     */
    public void chooseFilter(javafx.event.ActionEvent actionEvent) throws IOException {
        if (filter.getValue().equals(option)) {
            applyButton.setText("Remove filter");
            return;
        }
        applyButton.setText("Apply filter");
    }


    /**
     * Displays the activities of the current user.
     * @return an observable list containing all activities
     */
    public ObservableList<Activity> getActivity() {
        ObservableList<pojos.Activity> activities = FXCollections.observableArrayList();
        for (Activity activity: myActivities) {
            if (option == null || activity.getCategory().toString().equals(option)) {
                activities.add(activity);
            }
        }
        return activities;
    }

    /**
     * Creates a new log on the screen.
     * @param category the category of the newly created log
     * @param activityDescription the description
     * @param date the date when the log ws issued
     * @param points the number of points gained
     * @return an AnchorPane
     */
    public AnchorPane newLog(Category category, String activityDescription,
                             String date, String points) {

        AnchorPane logLayout = new AnchorPane();
        logLayout.setPrefSize(480, 100);

        Rectangle bigRect = new Rectangle(490, 80);
        bigRect.setStyle("-fx-fill: white; -fx-stroke: #9bc782;");
        bigRect.setLayoutX(1.0);
        bigRect.setLayoutY(8.0);

        Rectangle smallRect = new Rectangle(340, 80);
        smallRect.setStyle("-fx-fill: #9bc782;");
        smallRect.setLayoutX(76.0);
        smallRect.setLayoutY(8.0);

        Label logDescr = new Label(activityDescription);
        logDescr.setStyle("-fx-text-fill: white; -fx-text-alignment: center; "
                + "-fx-font-size: 22px; -fx-font-family: 'Arial Rounded MT Bold';");
        logDescr.setLayoutX(90.0);
        logDescr.setLayoutY(18.0);

        Label logDate = new Label("Added on " + date);
        logDate.setStyle("-fx-font-family: 'Arial Rounded MT Bold';");
        logDate.setLayoutX(90.0);
        logDate.setLayoutY(53.0);

        Label logNumPoints = new Label(points);
        logNumPoints.setStyle("-fx-font-size: 24px; -fx-alignment: center; "
                + "-fx-font-family: 'Arial Rounded MT Bold';");
        logNumPoints.setPrefHeight(46.0);
        logNumPoints.setPrefWidth(75.0);
        logNumPoints.setLayoutX(415.0);
        logNumPoints.setLayoutY(12.0);

        Label logPoints = new Label("POINTS");
        logPoints.setStyle("-fx-font-family: 'Arial Rounded MT Bold';");
        logPoints.setLayoutX(425.0);
        logPoints.setLayoutY(54.0);

        String imageUrl = new String();

        if (category == Category.food) {
            imageUrl = "/images/restaurant.png";
        } else if (category == Category.transportation) {
            imageUrl = "/images/car.png";
        } else if (category == Category.energy) {
            imageUrl = "/images/electricity.png";
        }

        Image image = new Image(imageUrl);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        imageView.setLayoutX(18.0);
        imageView.setLayoutY(27.0);

        logLayout.getChildren().addAll(bigRect, smallRect, logDescr,
                logDate, logNumPoints, logPoints, imageView);

        return logLayout;
    }

    /**
     * Logs a user out.
     * @param actionEvent the event on which the user should be logged out
     * @throws IOException when something with the action event goes wrong
     */
    public void logOut(javafx.scene.input.MouseEvent actionEvent) throws IOException {
        ConnectAccount.logOut();
        BorderPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
        rootPane.getChildren().setAll(pane);
    }



}
