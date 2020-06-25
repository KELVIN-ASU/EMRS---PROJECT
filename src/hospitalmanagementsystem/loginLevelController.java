/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagementsystem;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ekuri PC
 */
public class loginLevelController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private JFXButton adminBtn;
    @FXML
    private JFXButton receptionistBtn;
    @FXML
    private JFXButton doctorBtn;
    @FXML
    private JFXButton pharmacistBtn;
    @FXML
    private JFXButton biologistBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        doctorBtn.setCursor(Cursor.HAND);
        pharmacistBtn.setCursor(Cursor.HAND);
        biologistBtn.setCursor(Cursor.HAND);
        receptionistBtn.setCursor(Cursor.HAND);
        adminBtn.setCursor(Cursor.HAND);
    }

    @FXML
    private void handleAdminBtn(ActionEvent event) {

        try {
            Parent visitorParent = FXMLLoader.load(getClass().getResource("loginAsAdminForm.fxml"));
            Scene scene = new Scene(visitorParent);
            Stage visitorStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            visitorStage.setScene(scene);
            visitorStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleReceptionistBtn(ActionEvent event) {
        try {
            Parent visitorParent = FXMLLoader.load(getClass().getResource("loginAsReceptionistForm.fxml"));
            Scene scene = new Scene(visitorParent);
            Stage visitorStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            visitorStage.setScene(scene);
            visitorStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleDoctorBtn(ActionEvent event) {
        try {
            Parent visitorParent = FXMLLoader.load(getClass().getResource("loginAsDoctor.fxml"));
            Scene scene = new Scene(visitorParent);
            Stage visitorStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            visitorStage.setScene(scene);
            visitorStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handlePharmacistBtn(ActionEvent event) {
    }

    @FXML
    private void handleBiologistBtn(ActionEvent event) {

    }

}
