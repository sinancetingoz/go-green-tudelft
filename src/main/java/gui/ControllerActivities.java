package gui;

import client.ConnectAccount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerActivities implements Initializable {



    ObservableList<String> transportationActivitieList = FXCollections.observableArrayList("Bike");

    @FXML
    private BorderPane rootPane;
    @FXML
    private AnchorPane activityPane;
    @FXML
    private ImageView foodIcon;
    @FXML
    private ImageView transportIcon;
    @FXML
    private ImageView energyIcon;
    
    @FXML
    private Region region;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        foodIcon.setOpacity(1);
        transportIcon.setOpacity(0.4);
        energyIcon.setOpacity(0.4);
        try {
            loadFoodActivity();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the home page.
     * @param actionEvent the action event on which the home page should be loaded
     * @throws IOException when the event isn't properly handled
     */
    public void loadHome(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Home.fxml"));
        ControllerHome.welcomeMessage(pane);
        rootPane.getChildren().setAll(pane);
    }


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
     * @param actionEvent the action event on which the statistics page should be opened
     * @throws IOException when something with the action event goes wrong
     */
    public void loadStatistics(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane = FXMLLoader.load(
                getClass().getClassLoader().getResource("Statistics.fxml"));
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
     * Loads the friends page.
     * @param actionEvent the action event on which the friends page should be displayed
     * @throws IOException when something in the action went wrong.
     */
    public void loadFriends(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Friends.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    /**
     * Loads the food activity selection screen.
     * @throws IOException when something in the action went wrong
     */
    public void loadFoodActivity() throws IOException {
        foodIcon.setOpacity(1);
        transportIcon.setOpacity(0.4);
        energyIcon.setOpacity(0.4);

        AnchorPane foodPane = FXMLLoader.load(getClass().getClassLoader().getResource("Food.fxml"));
        activityPane.getChildren().setAll(foodPane);
    }
    
    /**
     * Loads the transport activity selection screen.
     * @param mouseEvent mouseEvent that triggers event.
     * @throws IOException when something in the action went wrong
     */
    public void loadTransportActivity(MouseEvent mouseEvent) throws IOException {
        foodIcon.setOpacity(0.4);
        transportIcon.setOpacity(1);
        energyIcon.setOpacity(0.4);
        AnchorPane transportPane = 
            FXMLLoader.load(getClass().getClassLoader().getResource("Transport.fxml"));
        activityPane.getChildren().setAll(transportPane);
    }
    
    /**
     * Loads the energy activity selection screen.
     * @param mouseEvent mouseEvent that triggers event
     * @throws IOException when something in the action went wrong
     */
    public void loadEnergyActivity(MouseEvent mouseEvent) throws IOException {
        foodIcon.setOpacity(0.4);
        transportIcon.setOpacity(0.4);
        energyIcon.setOpacity(1);
        AnchorPane energyPane = 
            FXMLLoader.load(getClass().getClassLoader().getResource("Energy.fxml"));
        activityPane.getChildren().setAll(energyPane);
    }
}
