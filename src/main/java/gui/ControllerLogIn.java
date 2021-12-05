package gui;

import client.ConnectAccount;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import util.HashPassword;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.ResourceBundle;

public class ControllerLogIn implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private BorderPane rootPane;

    @FXML
    private Label loginerror;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginerror.setVisible(false);

    }

    /**
     * Loads the home page when the user is logged in.
     * @param actionEvent the action event on which the user should be logged in
     * @throws IOException when something with the action event goes wrong
     */
    public void loadHome(ActionEvent actionEvent) throws NoSuchAlgorithmException, IOException {

        System.out.println(username.getText());

        String pass = HashPassword.hashPass(password.getText());

        if (ConnectAccount.serverLogin(username.getText(), pass)) {
            ControllerMyLog.myActivities = ConnectAccount.getActivities();
            Collections.reverse(ControllerMyLog.myActivities);
            ControllerHome.myAccount = ConnectAccount.getAccount();
            BorderPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Home.fxml"));
            ControllerHome.welcomeMessage(pane);
            rootPane.getChildren().setAll(pane);
        } else {
            loginerror.setVisible(true);
        }

    }

    /**
     * Loads the register page.
     * @param actionEvent the action event on which the user should be registered
     * @throws IOException when something with the action event goes wrong
     */
    public void loadRegister(ActionEvent actionEvent) throws IOException {
        Parent register = FXMLLoader.load(getClass().getClassLoader().getResource("Register.fxml"));
        Stage registerStage = new Stage();
        registerStage.setTitle("GO GREEN");
        registerStage.setScene(new Scene(register));
        registerStage.show();
    }


    /**
     * Loads the register page.
     * @param actionEvent the action event on which the user should be registered
     * @throws IOException when something with the action event goes wrong
     */
    public void forgotPassword(javafx.scene.input.MouseEvent actionEvent) throws IOException {
        Parent register = FXMLLoader.load(getClass().getClassLoader().getResource("Recover.fxml"));
        Stage registerStage = new Stage();
        registerStage.setTitle("GO GREEN");
        registerStage.setScene(new Scene(register));
        registerStage.show();
    }
}
