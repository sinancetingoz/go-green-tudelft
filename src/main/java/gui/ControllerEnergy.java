package gui;

import static client.ConnectAccount.hours;
import static client.ConnectAccount.temperature;

import client.ConnectAccount;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pojos.Activity;
import pojos.Category;
import pojos.DefaultValue;
import services.TemperatureService;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ControllerEnergy implements Initializable {

    ObservableList<String> energyActivitieList =
            FXCollections.observableArrayList(ActivityDb.Energy.descriptions);

    @FXML
    private ComboBox energyBox;
    @FXML
    private Label pointsText;
    @FXML
    private TextField energyAmount;
    @FXML
    private Label amountLabel;
    @FXML
    private Label forLabel;
    @FXML
    private TextField hoursAmount;
    @FXML
    private Label hoursLabel;
    @FXML
    private Label atLabel;
    @FXML
    private Label addSuccess;
    @FXML
    private  Label addFail;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        energyBox.setItems(energyActivitieList);
        energyAmount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                char[] array = newValue.toCharArray();
                for (char search : array) {
                    if (search < '0' || search > '9') {
                        energyAmount.setText(oldValue);
                    }

                }
                if (energyAmount.getText().equals("")) {
                    pointsText.setText("POINTS 0");
                } else {
                    if (energyBox.getValue().equals("Power saved by solar panels")) {
                        pointsText.setText("POINTS " + DefaultValue.kwhToPoints(
                                Integer.parseInt(energyAmount.getText())));
                    } else {
                        if (energyAmount.getText().equals("") || hoursAmount.getText().equals("")) {
                            pointsText.setText("POINTS 0");
                        } else {
                            pointsText.setText("POINTS " + DefaultValue.degreesToPoints(
                                    Double.parseDouble(energyAmount.getText()),
                                    Integer.parseInt(hoursAmount.getText())));
                        }

                    }
                }
            }
        });

        hoursAmount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    hoursAmount.setText(oldValue);
                }
                if (energyAmount.getText().equals("") || hoursAmount.getText().equals("")) {
                    pointsText.setText("POINTS 0");
                } else {
                    pointsText.setText("POINTS " + DefaultValue.degreesToPoints(
                            Double.parseDouble(energyAmount.getText()),
                            Integer.parseInt(hoursAmount.getText())));
                }
            }
        });

        addSuccess.setVisible(false);
        addFail.setVisible(false);
        energyAmount.setVisible(false);
        amountLabel.setVisible(false);
        forLabel.setVisible(false);
        hoursAmount.setVisible(false);
        hoursLabel.setVisible(false);
        atLabel.setVisible(false);
    }


    /**
     * Gets an activity and calculates its points.
     *
     *
     */
    public void inputActivity() {

        for (int i = 0; i < energyActivitieList.size(); i++) {
            if (ActivityDb.Energy.descriptions.get(i).equals(energyBox.getValue())) {
                if (energyBox.getValue().equals("Power saved by solar panels")) {
                    amountLabel.setText("kWh");
                    hoursLabel.setVisible(false);
                    hoursAmount.setVisible(false);
                    atLabel.setVisible(false);
                    forLabel.setVisible(false);
                    energyAmount.setText(String.valueOf(ConnectAccount.getMyAccount()
                            .getSavedEnergy()));
                    energyAmount.setVisible(true);
                    amountLabel.setVisible(true);
                    energyAmount.setLayoutX(284.0);
                    energyAmount.setLayoutY(170.0);
                    amountLabel.setLayoutX(383.0);
                    amountLabel.setLayoutY(178.0);
                    if (energyAmount.getText().equals("")) {
                        pointsText.setText("POINTS 0");
                    } else {
                        pointsText.setText("POINTS " + DefaultValue.kwhToPoints(
                                Integer.parseInt(energyAmount.getText())));
                    }
                } else {
                    amountLabel.setText("\u00B0C");
                    energyAmount.setVisible(true);
                    energyAmount.setText(String.valueOf(temperature));
                    hoursAmount.setText(String.valueOf(hours));
                    amountLabel.setVisible(true);
                    forLabel.setVisible(true);
                    hoursAmount.setVisible(true);
                    hoursLabel.setVisible(true);
                    atLabel.setVisible(true);
                    energyAmount.setLayoutX(307.0);
                    energyAmount.setLayoutY(228.0);
                    amountLabel.setLayoutX(406.0);
                    amountLabel.setLayoutY(236.0);
                    if (energyAmount.getText().equals("") || hoursAmount.getText().equals("")) {
                        pointsText.setText("POINTS 0");
                    } else {
                        pointsText.setText("POINTS " + DefaultValue.degreesToPoints(
                                Double.parseDouble(energyAmount.getText()),
                                Integer.parseInt(hoursAmount.getText())));
                    }
                }
            }
            /*if (energyBox.getValue().equals("Power saved by solar panels")) {
                amountLabel.setText("Energy saved (in kWh)");
            }*/
        }
    }



    /**
     * Adds an activity to the current user.
     *
     * @param actionEvent the action event on which a new activity should be added
     */
    public void addActivity(javafx.event.ActionEvent actionEvent) {
        if (energyAmount.getText() == null || energyAmount.getText().length() == 0) {
            return;
        }
        if (energyBox.getValue().equals("Lowering home temperature")
                && TemperatureService.ifSavedToday(ConnectAccount.getUsername())) {
            System.out.println("here\n");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Already put temperature for today");
            alert.setContentText("You cannot set your home temperature more than once a day!");
            alert.showAndWait();
            return;
        }

        String actDesc = null;
        int points = 0;

        for (int i = 0; i < ActivityDb.Energy.descriptions.size(); i++) {
            if (ActivityDb.Energy.descriptions.get(i).equals(energyBox.getValue())) {
                actDesc = ActivityDb.Energy.descriptions.get(i);
                if (actDesc.equals("Power saved by solar panels")) {
                    points = DefaultValue.kwhToPoints(Integer.parseInt(energyAmount.getText()));
                } else {
                    points = DefaultValue.degreesToPoints(
                            Double.parseDouble(energyAmount.getText()),
                            Integer.parseInt(hoursAmount.getText()));
                    TemperatureService.didToday(ConnectAccount.getUsername());
                }
            }
        }

        Activity activity = new Activity(actDesc, Category.energy, points,
                Date.valueOf(LocalDate.now()), ConnectAccount.getUsername());

        if (actDesc.equals("Power saved by solar panels")) {
            ConnectAccount.getMyAccount().setSavedEnergy(Integer.parseInt(energyAmount.getText()));
            ConnectAccount.setEnergy(Integer.parseInt(energyAmount.getText()));
            addSuccess.setText("Energy changed");
            addSuccess.setVisible(true);
            ControllerFood.fade(addSuccess);
        } else if (ConnectAccount.addActivity(activity)) {
            ControllerHome.myAccount.setPoints(ControllerHome.myAccount.getPoints()
                    + activity.getPoints());
            ControllerMyLog.myActivities.add(0,activity);
            temperature = Integer.valueOf(energyAmount.getText());
            hours = Integer.valueOf(hoursAmount.getText());
            addSuccess.setText("Temperature set");
            addSuccess.setVisible(true);
            ControllerFood.fade(addSuccess);
        } else {
            addFail.setVisible(true);
            ControllerFood.fade(addFail);
        }
    }
}
