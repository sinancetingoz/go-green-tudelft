package gui;

import client.ConnectAccount;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.Duration;
import pojos.Activity;
import pojos.Category;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ControllerFood implements Initializable {

    ObservableList<String> foodActivitieList =
            FXCollections.observableArrayList(ActivityDb.Food.descriptions);

    @FXML
    private ComboBox foodBox;

    @FXML
    private Label pointsText;

    @FXML
    private Label addSuccess;

    @FXML
    private  Label addFail;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        foodBox.setItems(foodActivitieList);

        addSuccess.setVisible(false);
        addFail.setVisible(false);
    }


    /** Gets an activity and calculates its points.
     *
     * @param actionEvent the action event on which points should be calculated
     */
    public void inputActivity(javafx.event.ActionEvent actionEvent) {

        for (int i = 0; i < ActivityDb.Food.descriptions.size(); i++) {
            if (ActivityDb.Food.descriptions.get(i).equals(foodBox.getValue())) {
                pointsText.setText("POINTS " + ActivityDb.Food.points.get(i));
            }
        }
    }

    /**
     * Adds an activity to the current user.
     * @param actionEvent the action event on which a new activity should be added
     */
    public void addActivity(javafx.event.ActionEvent actionEvent) {
        String actDesc = null;
        int points = 0;

        for (int i = 0; i < ActivityDb.Food.descriptions.size(); i++) {
            if (ActivityDb.Food.descriptions.get(i).equals(foodBox.getValue())) {
                actDesc = ActivityDb.Food.descriptions.get(i);
                points = ActivityDb.Food.points.get(i);
            }
        }

        Activity activity = new Activity(actDesc, Category.food, points,
                Date.valueOf(LocalDate.now()), ConnectAccount.getUsername());

        if (ConnectAccount.addActivity(activity)) {
            ControllerMyLog.myActivities.add(0,activity);
            ControllerHome.myAccount.setPoints(ControllerHome.myAccount.getPoints()
                    + activity.getPoints());
            addSuccess.setVisible(true);
            ControllerFood.fade(addSuccess);
        } else {
            addFail.setVisible(true);
            ControllerFood.fade(addFail);
        }
    }

    /**
     * Used for making a fade-effect of a label.
     * @param label the label to be faded
     */
    public static void fade(Label label) {
        FadeTransition fade = new FadeTransition(Duration.millis(3000));
        fade.setNode(label);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setCycleCount(1);
        fade.setAutoReverse(false);
        fade.playFromStart();
    }
}
