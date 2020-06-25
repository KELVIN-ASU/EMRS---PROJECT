/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagementsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ekuri PC
 */
public class LoginAsReceptionistFormController implements Initializable {

    @FXML
    private JFXTextField receptionistUserNameField;
    @FXML
    private JFXPasswordField receptionistPasswordField;
    @FXML
    private JFXButton homeBtn;
    @FXML
    private JFXButton loginBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        homeBtn.setCursor(Cursor.HAND);
        loginBtn.setCursor(Cursor.HAND);
    }

    @FXML
    private void handleHomeBtn(ActionEvent event) {
        try {
            Parent visitorParent = FXMLLoader.load(getClass().getResource("loginLevel.fxml"));
            Scene scene = new Scene(visitorParent);
            Stage visitorStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            visitorStage.setScene(scene);
            visitorStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleLoginBtn(ActionEvent event) {
        try {
            Parent visitorParent = FXMLLoader.load(getClass().getResource("receptionistDashBoard.fxml"));
            Scene scene = new Scene(visitorParent);
            Stage visitorStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            visitorStage.setScene(scene);
            visitorStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
