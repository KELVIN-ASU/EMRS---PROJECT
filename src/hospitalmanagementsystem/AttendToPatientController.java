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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Ekuri PC
 */
public class AttendToPatientController implements Initializable {

    @FXML
    private JFXTextField patientSNNtextField;
    @FXML
    private JFXTextField patientHeightTextField;
    @FXML
    private JFXTextField patientNameTextField;
    @FXML
    private JFXTextField patientWeightTextField;
    @FXML
    private ComboBox<String> patientBloodGroupCmbBox;
    @FXML
    private TextArea patientAddressTextArea;
    @FXML
    private JFXTextField patientPhoneNumberTextField;
    @FXML
    private DatePicker patientDateOfBirth;
    @FXML
    private JFXButton saveBtn;
    @FXML
    private ComboBox<String> patientGenderCmbBox;
    @FXML
    private TextArea biologistNoteTextArea;
    @FXML
    private TextField drugQuantityTextField;
    @FXML
    private ComboBox<String> drugNameComboBox;
    @FXML
    private TextArea doctorReporttextArea;
    @FXML
    private JFXButton clearBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        saveBtn.setCursor(Cursor.HAND);
        clearBtn.setCursor(Cursor.HAND);
    }

    @FXML
    private void handleSaveBtn(ActionEvent event) {
    }

    @FXML
    private void handleClearBtn(ActionEvent event) {
    }

    public void setPatientDetails(String ssn) {
        Connection conn = DBConnection.Connect();
        String sql = "SELECT * FROM patient WHERE patient_ssn  = ?";

//        String sql = "SELECT `patient_ssn`, `patient_name`, `patient_gender`, `patient_DOB`, "
//                + "`patient_Address`, `patient_height`, `patient_weight`, `patient_phone_number`, "
//                + "`patient_blood_group` FROM `patient` WHERE patient_ssn = '"
//                + ssn
//                + "'";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, ssn);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                patientSNNtextField.setText(rs.getString("patient_ssn"));
                patientHeightTextField.setText(rs.getString("patient_height"));
                patientNameTextField.setText(rs.getString("patient_name"));
                patientWeightTextField.setText(rs.getString("patient_weight"));
                patientAddressTextArea.setText(rs.getString("patient_Address"));
                patientPhoneNumberTextField.setText(rs.getString("patient_phone_number"));
                ((TextField) patientDateOfBirth.getEditor()).setText(rs.getString("patient_DOB"));
                //((TextField) patientGenderCmbBox.getEditor()).setText(rs.getString("patient_gender"));
                //((TextField) patientBloodGroupCmbBox.getEditor()).setText(rs.getString("patient_blood_group"));
                patientGenderCmbBox.getSelectionModel().select(rs.getString("patient_gender"));
                patientBloodGroupCmbBox.getSelectionModel().select(rs.getString("patient_blood_group"));
                //patientGenderCmbBox.getEditor().;
                //patientBloodGroupCmbBox;

            }
        } catch (SQLException ex) {
            Logger.getLogger(AttendToPatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
