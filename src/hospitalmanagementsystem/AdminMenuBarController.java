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
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Ekuri PC
 */
public class AdminMenuBarController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private JFXButton doctorsBtn;
    @FXML
    private JFXButton receptionistsBtn;
    @FXML
    private JFXButton pharmacistsBtn;
    @FXML
    private JFXButton biologistsBtn;
    @FXML
    private JFXButton adminsBtn;
    @FXML
    private JFXButton othersBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        adminsBtn.setCursor(Cursor.HAND);
        biologistsBtn.setCursor(Cursor.HAND);
        pharmacistsBtn.setCursor(Cursor.HAND);
        receptionistsBtn.setCursor(Cursor.HAND);
        doctorsBtn.setCursor(Cursor.HAND);
        loadUI("doctorTableView");
    }

    @FXML
    private void handleDoctorsBtn(ActionEvent event) {

        loadUI("doctorTableView");
    }

    @FXML
    private void handleReceptionistsBtn(ActionEvent event) {

        loadUI("ReceptionistTableView");
    }

    @FXML
    private void handlePharmacistsBtn(ActionEvent event) {

        loadUI("pharmacistTableView");
    }

    @FXML
    private void handleBiologistBtn(ActionEvent event) {

        loadUI("BiologistTableView");
    }

    @FXML
    private void handleAdminsBtn(ActionEvent event) {

        loadUI("adminTableView");

    }

    @FXML
    private void handleExitBtn(ActionEvent event) {
    }

    public void loadUI(String ui) {
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource(ui + ".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(AdminMenuBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderPane.setCenter(root);

    }
}
