/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagementsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Ekuri PC
 */
public class ManageDoctorSpecialtyController implements Initializable {

    final ObservableList options = FXCollections.observableArrayList();
    @FXML
    private JFXTextField newSpecialtyTextField;
    @FXML
    private JFXButton saveBtn;
    Connection conn = DBConnection.Connect();
    @FXML
    private ListView<String> list = new ListView<String>(options);
    @FXML
    private ImageView deleteImg;
    @FXML
    private Label listItemHolderLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        options.clear();
        saveBtn.setCursor(Cursor.HAND);
        deleteImg.setCursor(Cursor.HAND);
        fillList();
    }

    @FXML
    private void handleSaveBtn(ActionEvent event) {
        String sql = "INSERT INTO specialty (specialty_name) VALUES (?)";
        if (newSpecialtyTextField.getText().length() < 3) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("SPECIALTY FIELD CANNOT BE NULL\nOR THE ENTERED TEXT IS NOT A VALID SPECIALTY");
            alert.showAndWait();

        } else {

            try {
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, newSpecialtyTextField.getText().toUpperCase());

                int i = pst.executeUpdate();

                if (i != 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("NEW SPECIALTY FOR DOCTORS SUCCESSFULLY ADDED");
                    alert.showAndWait();
                    newSpecialtyTextField.clear();

                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("COULDN'T ADD NEW SPECIALTY FOR DOCTORS");
                    alert.showAndWait();
                    newSpecialtyTextField.clear();

                }
            } catch (SQLException ex) {
                Logger.getLogger(ManageDoctorSpecialtyController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void fillList() {
        options.clear();
        try {
            String sql = "SELECT specialty_name FROM specialty";
            ResultSet rs = null;
            PreparedStatement stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                options.addAll(rs.getString("specialty_name"));

            }
            list.getItems().addAll(options);
        } catch (SQLException ex) {
            Logger.getLogger(ManageDoctorSpecialtyController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void getListItemToDelete(MouseEvent event) {
        listItemHolderLabel.setText(list.getSelectionModel().getSelectedItem());

    }

    @FXML
    private void deleteItemFromListView(MouseEvent event) {
        Alert al = new Alert(Alert.AlertType.CONFIRMATION);
        al.setContentText("ARE YOU SURE YOU WANT TO DELETE THIS SPECIALTY OPTION");
        Optional<ButtonType> action = al.showAndWait();

        if (action.get() == ButtonType.OK) {

            String sql = "DELETE FROM `specialty` WHERE specialty_name = ?";
            try {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, listItemHolderLabel.getText());

                int i = stmt.executeUpdate();
                if (i != 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("LIST ITEM DELETED FROM DATABASE");
                    alert.showAndWait();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManageDoctorSpecialtyController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

//    public void refreshList() {
//        
//        try {
//            String sql = "SELECT specialty_name FROM specialty";
//            ResultSet rs = null;
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            rs = stmt.executeQuery();
//            while (rs.next()) {
//                options.addAll(rs.getString("specialty_name"));
//
//            }
//            list.getItems().addAll(options);
//        } catch (SQLException ex) {
//            Logger.getLogger(ManageDoctorSpecialtyController.class.getName()).log(Level.SEVERE, null, ex);
//        }
}
