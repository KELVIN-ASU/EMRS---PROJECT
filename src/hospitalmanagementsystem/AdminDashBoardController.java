/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagementsystem;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
public class AdminDashBoardController implements Initializable {

    @FXML
    private Label weekDayLabel;
    @FXML
    private Label monthDayLabel;
    @FXML
    private Label monthYearLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private JFXButton addUsersBtn;
    @FXML
    private JFXButton viewUserBtn;
    @FXML
    private ImageView powerImage;
    @FXML
    private ImageView aboutImage;
    @FXML
    private ImageView lockImage;
    @FXML
    private JFXButton searchAndReportBtn;

    String extractedLoginName;
    @FXML
    private ImageView adminProfilePicture;
    @FXML
    private Label adminIDLable;
    @FXML
    private Label adminNameLable;
    @FXML
    private Label adminLoginLable;

    Connection connection = DBConnection.Connect();

    String extraxtedLoginName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        lockImage.setCursor(Cursor.HAND);
        searchAndReportBtn.setCursor(Cursor.HAND);
        aboutImage.setCursor(Cursor.HAND);
        powerImage.setCursor(Cursor.HAND);
        viewUserBtn.setCursor(Cursor.HAND);
        // Instantiate a Date object
        addUsersBtn.setCursor(Cursor.HAND);
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
    private void handleAddUsersBtn(ActionEvent event) {
        Parent root;
        Stage stage;
        if (event.getSource() == addUsersBtn) {
            try {
                stage = new Stage();
                root = FXMLLoader.load(getClass().getResource("manageUsers.fxml"));
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(addUsersBtn.getScene().getWindow());
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
    private void handleViewUsersBtn(ActionEvent event) {

        Parent root;
        Stage stage;
        if (event.getSource() == viewUserBtn) {
            try {
                stage = new Stage();
                root = FXMLLoader.load(getClass().getResource("adminMenuBar.fxml"));
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(viewUserBtn.getScene().getWindow());
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
    private void handlePowerImage(MouseEvent event) {
        Alert al = new Alert(Alert.AlertType.CONFIRMATION);
        al.setContentText("THIS WILL CLOSE THE APPLICATION\nPROCEED?");
        Optional<ButtonType> action = al.showAndWait();

        if (action.get() == ButtonType.OK) {
            System.exit(1);
        }

    }

    @FXML
    private void howAboutImage(MouseEvent event) {
    }

    @FXML
    private void handleLockScreenImage(MouseEvent event) {
        Alert al = new Alert(Alert.AlertType.CONFIRMATION);
        al.setContentText("SURE YOU WANT TO LOGOUT?");
        Optional<ButtonType> action = al.showAndWait();

        if (action.get() == ButtonType.OK) {
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("loginAsAdminForm.fxml"));
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
    private void handleSearchAndReportBtn(ActionEvent event) {
    }

}
