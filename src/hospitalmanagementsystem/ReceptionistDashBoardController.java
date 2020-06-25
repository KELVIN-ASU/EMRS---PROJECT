/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagementsystem;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ekuri PC
 */
public class ReceptionistDashBoardController implements Initializable {

    @FXML
    private ImageView powerImage;
    @FXML
    private ImageView aboutImage;
    @FXML
    private ImageView logoutImage;
    @FXML
    private Label weekDayLabel;
    @FXML
    private Label monthDayLabel;
    @FXML
    private Label monthYearLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private JFXButton registerPatientBtn;
    @FXML
    private JFXButton leavePatientBtn;
    @FXML
    private JFXButton searchPatientBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        logoutImage.setCursor(Cursor.HAND);
        aboutImage.setCursor(Cursor.HAND);
        powerImage.setCursor(Cursor.HAND);
        searchPatientBtn.setCursor(Cursor.HAND);
        leavePatientBtn.setCursor(Cursor.HAND);
        registerPatientBtn.setCursor(Cursor.HAND);

        //INSTANTIATING A DATE OBJECT
        Date date = new Date();
        weekDayLabel.setText(date.toString().substring(0, 4));
        monthDayLabel.setText(date.toString().substring(8, 10));
        String monthValue = date.toString().substring(3, 8);
        String yearValue = date.toString().substring(24);
        String monthYearLabelValue = monthValue + "," + yearValue;
        monthYearLabel.setText(monthYearLabelValue);
        timeLabel.setText(date.toString().substring(10, 19));

    }

    @FXML
    private void handlePowerImage(MouseEvent event) {
        Alert al = new Alert(Alert.AlertType.CONFIRMATION);
        al.setContentText("THIS WILL CLOSE THE APPLICATION\nPROCEED?");
        Optional<ButtonType> action = al.showAndWait();

        if (action.get() == ButtonType.OK) {
            System.exit(1);
        }

    }

    @FXML
    private void handleAboutImage(MouseEvent event) {
    }

    @FXML
    private void handleLogoutImage(MouseEvent event) {
        Alert al = new Alert(Alert.AlertType.CONFIRMATION);
        al.setContentText("SURE YOU WANT TO LOGOUT?");
        Optional<ButtonType> action = al.showAndWait();

        if (action.get() == ButtonType.OK) {
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("loginAsReceptionistForm.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void handleRegisterPatientBtn(ActionEvent event) {
        Parent root;
        Stage stage;
        if (event.getSource() == registerPatientBtn) {
            try {
                stage = new Stage();
                root = FXMLLoader.load(getClass().getResource("patientRegistration.fxml"));
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(registerPatientBtn.getScene().getWindow());
                stage.setResizable(false);
                stage.showAndWait();
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("ERROR LOADING MANAGEUSERS MODULE.\nMODULE HAS AN IMPLEMENTATION ERROR");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleLeavePatientBtn(ActionEvent event) {
    }

    @FXML
    private void handleSearchPatientBtn(ActionEvent event) {

        Parent root;
        Stage stage;
        if (event.getSource() == searchPatientBtn) {
            try {
                stage = new Stage();
                root = FXMLLoader.load(getClass().getResource("viewPatientsTable.fxml"));
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(searchPatientBtn.getScene().getWindow());
                stage.setResizable(false);
                stage.showAndWait();
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("ERROR LOADING MANAGEUSERS MODULE.\nMODULE HAS AN IMPLEMENTATION ERROR");
                alert.showAndWait();
            }
        }

    }

}
