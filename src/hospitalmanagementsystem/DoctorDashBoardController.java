/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagementsystem;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Ekuri PC
 */
public class DoctorDashBoardController implements Initializable {

    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label specialtyLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private ImageView doctorProfilePic;
    @FXML
    private ImageView powerImage;
    @FXML
    private ImageView aboutImage;
    @FXML
    private ImageView logoutBtn;
    @FXML
    private TableView<Patient> queuedPatientsTable;
    @FXML
    private TableColumn<Patient, String> queuedPatientSSN;
    @FXML
    private TableColumn<Patient, String> queuedPatientName;
    @FXML
    private TableColumn<Patient, String> queuedPatientGender;
    @FXML
    private TableColumn<Patient, String> queuedPatientRegDate;

    ObservableList<Patient> queuedPatientsList = FXCollections.observableArrayList();
    ObservableList<Patient> labPatientsList = FXCollections.observableArrayList();

    String name;
    String labPatient;
    @FXML
    private JFXButton refreshTablesBtn;
    @FXML
    private JFXButton attendToBtn;
    @FXML
    private TableView<Patient> labPatientsTable;
    @FXML
    private TableColumn<Patient, String> labPatientSSN;
    @FXML
    private TableColumn<Patient, String> labPatientName;
    @FXML
    private TableColumn<Patient, String> labPatientGender;
    @FXML
    private TableColumn<Patient, String> labPatientRegDate;
    @FXML
    private Label time;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        powerImage.setCursor(Cursor.HAND);
        aboutImage.setCursor(Cursor.HAND);
        logoutBtn.setCursor(Cursor.HAND);
        refreshTablesBtn.setCursor(Cursor.HAND);
        attendToBtn.setCursor(Cursor.HAND);

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            time.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());

        }), new KeyFrame(Duration.seconds(1))
        );

        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void loadDoctorInfo(String id, String doctorName, String doctorSpecialty, String doctorPhoneNumber) {
        idLabel.setText("ID: " + id);
        nameLabel.setText(doctorName);
        specialtyLabel.setText("SPECIALTY:" + doctorSpecialty);
        phoneLabel.setText("NUMBER:" + doctorPhoneNumber);

    }

    public void getDoctorProfilePic(Image image) {
        doctorProfilePic.setImage(image);
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
                Parent parent = FXMLLoader.load(getClass().getResource("loginAsDoctor.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void fillPatientsInDoctorsQueue(String criteria) {
        name = criteria;
        queuedPatientsList.clear();
        Connection connection = DBConnection.Connect();
        String sql = "SELECT `patient_ssn`, `patient_name`, `patient_gender`, `reg_date`, "
                + "`patient_status` FROM `patient`"
                + "WHERE `patient_status` = '"
                + "waitingDoctor"
                + "'AND `patient_doctor` = '"
                + name + "'";
        //"WHERE doctor_login = '" + doctorLoginNameField.getText() + "'AND doctor_password = '" + doctorPasswordField.getText() + "'";

        try {
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                queuedPatientsList.add(new Patient(
                        rs.getString("patient_ssn"),
                        rs.getString("patient_name"),
                        rs.getString("patient_gender"),
                        rs.getString("reg_date")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DoctorDashBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        queuedPatientSSN.setCellValueFactory(new PropertyValueFactory<>("ssn"));
        queuedPatientName.setCellValueFactory(new PropertyValueFactory<>("name"));;
        queuedPatientGender.setCellValueFactory(new PropertyValueFactory<>("gender"));;
        queuedPatientRegDate.setCellValueFactory(new PropertyValueFactory<>("regDate"));

        queuedPatientsTable.setItems(queuedPatientsList);

    }

    public void fillPatientsSentToLabTable(String criteria) {
        labPatient = criteria;
        labPatientsList.clear();
        Connection connection = DBConnection.Connect();
        String sql = "SELECT `patient_ssn`, `patient_name`, `patient_gender`, `reg_date`, "
                + "`patient_status` FROM `patient`"
                + "WHERE `patient_status` = '"
                + "lab"
                + "'AND `patient_doctor` = '"
                + labPatient + "'";
        //"WHERE doctor_login = '" + doctorLoginNameField.getText() + "'AND doctor_password = '" + doctorPasswordField.getText() + "'";

        try {
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                labPatientsList.add(new Patient(
                        rs.getString("patient_ssn"),
                        rs.getString("patient_name"),
                        rs.getString("patient_gender"),
                        rs.getString("reg_date")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DoctorDashBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        labPatientSSN.setCellValueFactory(new PropertyValueFactory<>("ssn"));
        labPatientName.setCellValueFactory(new PropertyValueFactory<>("name"));;
        labPatientGender.setCellValueFactory(new PropertyValueFactory<>("gender"));;
        labPatientRegDate.setCellValueFactory(new PropertyValueFactory<>("regDate"));

        labPatientsTable.setItems(labPatientsList);

    }

    @FXML
    private void handleRefreshTablesBtn(ActionEvent event) {
        fillPatientsInDoctorsQueue(nameLabel.getText());
        fillPatientsSentToLabTable(nameLabel.getText());
    }

    @FXML
    private void handleAttendToBtn(ActionEvent event) {
        Patient patient = queuedPatientsTable.getSelectionModel().getSelectedItem();

        Stage stage;
        if (event.getSource() == attendToBtn) {
            try {
                stage = new Stage();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("attendToPatient.fxml"));
                Parent root = loader.load();
                AttendToPatientController patientController = loader.getController();
                patientController.setPatientDetails(patient.getSsn());

                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(attendToBtn.getScene().getWindow());
                stage.setResizable(false);
                stage.getIcons().add(new Image(HospitalManagementSystem.class.getResourceAsStream("ICONS/hospital.png")));
                stage.showAndWait();
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("ERROR LOADING PATIENT'S TREATMENT SCENE MODULE.\nCONTACT SYSTEM ADMIN");
                alert.showAndWait();
            }catch(NullPointerException nullException){
                 Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("NO PATIENT SELECTED TO ATTEND TO..");
                alert.showAndWait();
            }
        }

    }
}
