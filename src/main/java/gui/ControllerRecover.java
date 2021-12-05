package gui;

import client.ConnectEmail;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerRecover implements Initializable {
    @FXML
    private TextField recoverEmail;
    @FXML
    private Label emailError;
    @FXML
    private AnchorPane emailSent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emailError.setVisible(false);
        emailSent.setVisible(false);
    }

    /**
     * Sends recover password email to the server.
     * @param actionEvent the event triggered when email recovery is requested
     */
    public void sendEmail(javafx.scene.input.MouseEvent actionEvent) {
        if (recoverEmail.getText() == null || recoverEmail.getText().equals("")) {
            emailError.setText("E-mail field empty");
            emailError.setVisible(true);
            return;
        } else if (!ConnectEmail.recoverPassword(recoverEmail.getText())) {
            emailError.setText("E-mail not valid");
            emailError.setVisible(true);
        } else {
            emailSent.setVisible(true);

            final Node source = (Node) actionEvent.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();

            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished( event -> stage.close() );
            delay.play();
        }
    }
}
