package gui;

import client.ConnectEmail;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.validator.routines.EmailValidator;
import pojos.Account;
import util.HashPassword;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;


public class ControllerRegister implements Initializable {

    @FXML
    private javafx.scene.control.TextField email;
    @FXML
    private javafx.scene.control.TextField name;
    @FXML
    private javafx.scene.control.TextField surname;
    @FXML
    private javafx.scene.control.TextField username;
    @FXML
    private javafx.scene.control.TextField password;
    @FXML
    private javafx.scene.control.TextField confirmPassword;
    @FXML
    private Label errormessage;
    @FXML
    private AnchorPane accountCreated;
    @FXML
    private TextField recoverEmail;
    @FXML
    private Button recoverButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accountCreated.setVisible(false);
        errormessage.setVisible(false);
    }

    /**
     * Checks whether the password field is valid.
     * @return message to be displayed to the user
     */
    public String checkPassField() {
        String pass = password.getText();
        if (pass.length() <= 6) {
            return "Password too short (7 characters at least)";
        }
        if (!pass.matches("[a-zA-Z0-9]*")) {
            return "Password contains a forbidden character!";
        }
        if (!pass.equals(confirmPassword.getText())) {
            return "The passwords do not match";
        }
        return "OK";
    }

    /**
     * Checks fields.
     * @return alert message
     */
    public String checkFields() {
        EmailValidator ev = EmailValidator.getInstance();
        String mail = email.getText();

        if (mail.length() == 0) {
            return "Email field empty";
        }
        if (!ev.isValid(mail)) {
            return "Email not valid";
        }
        if (name.getText().length() == 0) {
            return "Name field empty";
        }
        if (surname.getText().length() == 0) {
            return "Surname field empty";
        }

        String user = username.getText();
        if (user.length() <= 5) {
            return "Username too short (6 characters at least)";
        }
        if (!user.matches("[a-zA-Z0-9]*")) {
            return "Username contains a forbidden character";
        }
        if (user.charAt(0) >= '0' && user.charAt(0) <= '9') {
            return "Username cannot start with a digit";
        }

        return checkPassField();
    }

    /**
     * Alerts user of username already in use.
     */
    public void alertSameUsername() {
        errormessage.setVisible(true);
        errormessage.setText("Choose another username");
    }

    /**
     * Alerts users with custom alert message.
     */
    public void customAlert(String message) {
        errormessage.setVisible(true);
        errormessage.setText(message);
    }

    /**
     * Creates an account and then closes window when user click button.
     * @param actionEvent the action event
     */
    public void createAccount(javafx.event.ActionEvent actionEvent) 
        throws NoSuchAlgorithmException {

        String pass = HashPassword.hashPass(password.getText());

        Account user = new Account(username.getText(),email.getText(),
                pass, name.getText(),
                surname.getText());

        if (!checkFields().equals("OK")) {
            customAlert(checkFields());
            return;
        }

        if (ConnectEmail.confirmAccount(user)) {
            accountCreated.setVisible(true);

            final Node source = (Node) actionEvent.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();

            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished( event -> stage.close() );
            delay.play();
        } else {
            alertSameUsername();
        }
    }

}
