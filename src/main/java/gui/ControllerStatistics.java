package gui;

import client.ConnectAccount;
import client.ConnectFriends;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import pojos.Activity;
import pojos.DefaultValue;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ResourceBundle;

public class ControllerStatistics implements Initializable {

    private static Hashtable<String,Integer> usersOnGraph;

    private static int ADDED = 1;
    @FXML
    private BorderPane rootPane;
    @FXML
    private LineChart<String, Number> linechart;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Button addButton;
    
    @FXML
    private Region region;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // X-axis: dates array
        usersOnGraph = new Hashtable<>();
        ADDED = 1;
        //we load all the friends' activities
        ConnectFriends.getFriendActivities(ConnectFriends.getFriends());
        ArrayList<Activity> myActivities =
                ConnectFriends.getUsersActivities().get(ConnectAccount.getUsername());

        LocalDate date = LocalDate.now();
        String[] dates = new String[7];
        double[] userCo2 = new double[7];

        for (int i = 0; i <= 6; i++) {
            dates[i] = date.minusDays(6 - i).toString();
        }

        for (Activity search : myActivities) {
            System.out.println(search.getDate().toLocalDate().toString());
            for (int i = 0; i <= 6; i++) {
                if (search.getDate().toLocalDate().toString().equals(dates[i])) {
                    userCo2[i] += DefaultValue.converter(search.getPoints()) * 1000;
                }
            }
        }

        // User's own series
        XYChart.Series userSeries = new XYChart.Series();
        userSeries.setName("You");

        for (int i = 0; i <= 6; i++) {
            userSeries.getData().add(new XYChart.Data(dates[i], userCo2[i]));
        }

        // Friend's series
        ArrayList<String> users = ConnectFriends.getFriends();
        users.remove(ConnectAccount.getUsername());
        comboBox.getItems().addAll(users);
        addButton.setOnAction(e -> addFriend(comboBox, dates));

        // Plot series of points
        linechart.getData().add(userSeries);
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
     * Loads the my log page on action.
     *
     * @param actionEvent the action on which the my log page should be loaded
     * @throws IOException when something with the action event goes wrong
     */
    public void loadMyLog(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("MyLog.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**
     * Loads the activity page on action.
     *
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
     * @param actionEvent the action event on which the statistics page should be opened
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
     * Logs a user out.
     * @param actionEvent the event on which the user should be logged out
     * @throws IOException when something with the action event goes wrong
     */
    public void logOut(javafx.scene.input.MouseEvent actionEvent) throws IOException {
        ConnectAccount.logOut();
        BorderPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    /**
     * Get choice from choice box.
     * @param comboBox The choicebox
     * @param dates The dates
     */
    public void addFriend(ComboBox<String> comboBox, String[] dates) {

        String friend = comboBox.getValue();
        if (ControllerStatistics.usersOnGraph.containsKey(friend)) {
            int counter = 0;
            for (XYChart.Series<String,Number> search : linechart.getData()) {
                if (search.getName().equals(friend)) {
                    linechart.getData().remove(counter);
                    break;
                }
                counter++;
            }
            ControllerStatistics.usersOnGraph.remove(friend);
            addButton.setText("Add to chart");
            return;
        }
        usersOnGraph.put(friend,ADDED);
        XYChart.Series friendSeries = new XYChart.Series();
        friendSeries.setName(friend);

        double[] friendCo2 = new double[7];
        ArrayList<Activity> friendActivities = ConnectFriends.getUsersActivities().get(friend);

        for (Activity search : friendActivities) {
            for (int i = 0; i <= 6; i++) {
                if (search.getDate().toLocalDate().toString().equals(dates[i])) {
                    friendCo2[i] += DefaultValue.converter(search.getPoints() * 1000);
                }
            }
        }

        for (int i = 0; i <= 6; i++) {
            friendSeries.getData().add(new XYChart.Data(dates[i], friendCo2[i]));
        }

        linechart.getData().add(friendSeries);
        addButton.setText("Remove from chart");
    }

    /**
     * Changes the text of the choicebox.
     * @param actionEvent the action which triggers the event
     * @throws IOException when there is an error in the action
     */
    public void changeText(javafx.event.ActionEvent actionEvent) throws IOException {
        if (usersOnGraph.containsKey(comboBox.getValue())) {
            addButton.setText("Remove from chart");
        } else {
            addButton.setText("Add to chart");
        }
    }
}

