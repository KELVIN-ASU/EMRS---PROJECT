/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagementsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

/**
 * FXML Controller class
 *
 * @author Ekuri PC
 */
public class PatientRegistrationController implements Initializable {

    @FXML
    private JFXTextField patientSSNField;
    @FXML
    private JFXTextField patientNameField;
    @FXML
    private JFXComboBox<String> patientGenderCmbBox;
    @FXML
    private JFXDatePicker patientDOB;
    @FXML
    private JFXTextArea patientAddressArea;
    @FXML
    private JFXComboBox<String> patientBloodGroupCmbBox;
    @FXML
    private JFXTextField patientHeightField;
    @FXML
    private JFXTextField patientPhnField;
    @FXML
    private JFXTextField patientWeightField;
    @FXML
    private JFXComboBox<String> doctorSpecialtyCmbBox;
    @FXML
    private JFXComboBox<String> doctorNameCmbBox;
    @FXML
    public JFXButton saveBtn;
    @FXML
    private JFXButton resetBtn;
    UtilityMethods utilityMethod = new UtilityMethods();
    @FXML
    private Label patientsCountLabel;
    Date date = new Date();
    int numberOfPatients;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        saveBtn.setCursor(Cursor.HAND);
        resetBtn.setCursor(Cursor.HAND);
        patientGenderCmbBox.getItems().addAll("MALE", "FEMALE");
        utilityMethod.fillComboBox(doctorSpecialtyCmbBox, "SELECT  `specialty_name` FROM `specialty`;", "specialty_name");
        patientBloodGroupCmbBox.getItems().addAll("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-");

        //initialize values for BloodGroupComboBox and GenderComboBox
        patientBloodGroupCmbBox.setValue("A+");
        patientGenderCmbBox.setValue("MALE");

        Tooltip heightToolTip = new Tooltip("ENTER PATIENT'S HEIGHT TO 2 decimal place");
        patientHeightField.setTooltip(heightToolTip);

        Tooltip weightToolTip = new Tooltip("ENTER PATIENT'S WEIGHT TO 1 decimal place");
        patientWeightField.setTooltip(weightToolTip);

        Tooltip phnToolTip = new Tooltip("PHONE NUMBER MUST BEGIN WITH A 6 AND MUST CONTAIN EXACTLY 9 DIGITS");
        patientPhnField.setTooltip(phnToolTip);

    }

    @FXML
    private void handleSaveBtn(ActionEvent event) {

        Connection connection = DBConnection.Connect();
        String sql = "INSERT INTO patient(patient_ssn, patient_name, patient_gender, patient_DOB, patient_Address, patient_height, "
                + "patient_weight, patient_phone_number, patient_blood_group, patient_doctor, reg_date, patient_status) VALUES"
                + " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = null;
        LocalDate dob = patientDOB.getValue();
        if (validateFields()) {
            if (validPatientHeightAndWieight()) {

                if (isInt(patientSSNField, "PLEASE, ASSIGN A UNIQUE SOCIAL SECURITY NUMBER TO THE PATIENT") && isPhoneNumber()) {
                    try {
                        //DIVING THE PHONE NUMBER INTO 3 SUBSTRINGS
                        String subPhn1 = patientPhnField.getText().substring(0, 3);
                        String subPhn2 = patientPhnField.getText().substring(3, 6);
                        String subPhn3 = patientPhnField.getText().substring(6, 9);
                        String phoneNumber = subPhn1 + "-" + subPhn2 + "-" + subPhn3;

                        pst = connection.prepareStatement(sql);
                        pst.setString(1, patientSSNField.getText());
                        pst.setString(2, patientNameField.getText().toUpperCase());
                        pst.setString(3, patientGenderCmbBox.getValue());
                        pst.setString(4, dob.toString());
                        pst.setString(5, patientAddressArea.getText());
                        pst.setString(6, patientHeightField.getText().concat("m"));
                        pst.setString(7, patientWeightField.getText().concat("Kg"));
                        pst.setString(8, phoneNumber);
                        pst.setString(9, patientBloodGroupCmbBox.getValue());
                        pst.setString(10, doctorNameCmbBox.getValue());
                        pst.setString(11, date.toString());
                        pst.setString(12, "waitingDoctor");
                        int i = pst.executeUpdate();
                        if (i != 0) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("PATIENT REGISTRATION COMPLETED\nPATIENT CAN NOW SEE DR."
                                    + doctorNameCmbBox.getValue().toUpperCase()
                                    + "");
                            alert.showAndWait();
                            getNumberOfPatientsInQueue();
                        }
                    } catch (SQLException ex) {
                        ex.getMessage();
                    } catch (StringIndexOutOfBoundsException ex) {
                        ex.getMessage();

                    }
                }

            }

        }
    }

    @FXML
    private void handleResetBtn(ActionEvent event) {

        patientDOB.getEditor().clear();
        patientSSNField.clear();
        patientNameField.clear();
        patientGenderCmbBox.getEditor().clear();
        patientAddressArea.clear();
        patientHeightField.clear();
        patientWeightField.clear();
        patientPhnField.clear();
        patientBloodGroupCmbBox.getEditor().clear();
        doctorNameCmbBox.getEditor().clear();
    }

    @FXML
    private void fillDoctorNameComboBox(ActionEvent event) {
        String sql = "SELECT doctor_name FROM `doctor` WHERE specialty = "
                + "'"
                + doctorSpecialtyCmbBox.getValue()
                + "'"
                + ";";
        doctorNameCmbBox.getEditor().clear();
        utilityMethod.fillComboBox(doctorNameCmbBox, sql, "doctor_name");

    }

    @FXML
    private void countPatientsInSelectedDoctorQueue(ActionEvent event) {
        getNumberOfPatientsInQueue();

    }

    public boolean isInt(JFXTextField f, String message) {
        try {
            Integer.parseInt(f.getText());
            return true;

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("INPUT ERROR");
            alert.setContentText(message);
            alert.showAndWait();
            f.requestFocus();
            return false;
        }
    }

    public boolean validateFields() {
        if (patientSSNField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("PATIENT INFORMATION VALIDATION ERROR");
            alert.setContentText("PLEASE, ASSIGN A UNIQUE SOCIAL SECURITY NUMBER TO PATIENT");
            alert.showAndWait();
            patientSSNField.requestFocus();
            return false;
        }
        if (patientDOB.getEditor().getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("PATIENT INFORMATION VALIDATION ERROR");
            alert.setContentText("PATIENT DATE OF BIRTH IS REQUIRED");
            alert.showAndWait();
            patientDOB.getEditor().requestFocus();
            return false;
        }
        if (patientPhnField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("PATIENT INFORMATION VALIDATION ERROR");
            alert.setContentText("PATIENT PHONE NUMBER CANNOT BE EMPTY");
            alert.showAndWait();
            patientPhnField.requestFocus();
            return false;
        }
        if (patientNameField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("PATIENT INFORMATION VALIDATION ERROR");
            alert.setContentText("PATIENT NAME CANNOT BE EMPTY");
            alert.showAndWait();
            patientNameField.requestFocus();
            return false;
        }
        if (patientAddressArea.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("PATIENT INFORMATION VALIDATION ERROR");
            alert.setContentText("PATIENT ADDRESS IS REQUIRED");
            alert.showAndWait();
            patientAddressArea.requestFocus();
            return false;
        }
        if (patientHeightField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("PATIENT INFORMATION VALIDATION ERROR");
            alert.setContentText("PATIENT HEIGHT IS REQUIRED");
            alert.showAndWait();
            patientHeightField.requestFocus();
            return false;
        }
        if (patientWeightField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("PATIENT INFORMATION VALIDATION ERROR");
            alert.setContentText("PATIENT WEIGHT IS REQUIRED");
            alert.showAndWait();
            patientWeightField.requestFocus();
            return false;
        }
        return true;
    }

    public void getNumberOfPatientsInQueue() {

        patientsCountLabel.setText("");
        ResultSet rs = null;
        try {
            Connection conn = DBConnection.Connect();
            String sql = "SELECT COUNT(*) FROM patient WHERE patient_doctor = "
                    + "'"
                    + doctorNameCmbBox.getValue()
                    + "'"
                    + "AND "
                    + "patient_status = "
                    + "'waitingDoctor';";
            PreparedStatement countStmt = conn.prepareStatement(sql);

            rs = conn.createStatement().executeQuery(sql);
            rs = countStmt.executeQuery();

            if (rs.next()) {
                String patientsCount = rs.getString(1);
                patientsCountLabel.setText(patientsCount);
                changePatientsCountLableColor();

            }
        } catch (SQLException ex) {
            Logger.getLogger(PatientRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void changePatientsCountLableColor() {
        numberOfPatients = Integer.parseInt(patientsCountLabel.getText());
        if (numberOfPatients > 10) {
            patientsCountLabel.setStyle("-fx-background-color: red;");

        }

    }

    public boolean validPatientHeightAndWieight() {
        if ((patientHeightField.getText().matches("\\d{1}[.]\\d{2}")) && (patientWeightField.getText().matches("\\d{2}[.]\\d{1}"))) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("PATIENT HEIGHT OR WEIGHT VALIDATION ERROR");
            alert.setContentText("PATIENT HEIGHT OR WEIGHT IS NOT IN THE RIGHT FORMAT\n"
                    + "EXAMPLE OF WEIGHT IN RIGHT FORMAT 24.7 (IN Kg)\n"
                    + "EXAMPLE OF HEIGHT IN RIGHT FORMAT 1.78 (IN METERS)");
            alert.showAndWait();
            return false;
        }

    }

    public boolean isPhoneNumber() {
        String phone = patientPhnField.getText();
        if ((phone.matches("\\d{1}\\d{1}\\d{1}\\d{1}\\d{1}\\d{1}\\d{1}\\d{1}\\d{1}")) && (phone.startsWith("6"))) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("PATIENT PHONE NUMBER VALIDATION ERROR");
            alert.setContentText("PATIENT PHONE NUMBER IS NOT IN THE RIGHT FORMAT\n"
                    + "PHONE NUMBER MUST CONTAIN NINE DIGITS, BEGINNING WITH A 6");
            alert.showAndWait();
            return false;
        }
    }
}
