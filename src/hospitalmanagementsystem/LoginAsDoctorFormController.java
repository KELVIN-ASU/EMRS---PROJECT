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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ekuri PC
 */
public class LoginAsDoctorFormController implements Initializable {

    @FXML
    private JFXTextField doctorUserNameField;
    @FXML
    private JFXPasswordField doctorPasswordField;
    @FXML
    private JFXButton homeBtn;
    @FXML
    private JFXButton loginBtn;
    @FXML
    private ProgressBar progressBar;

    Task<Boolean> task;
    @FXML
    private Label counterLabel;

    Doctor doctor;
    int doctorId;
    String doctorSpecialty, doctorAddress, doctorName, doctorGender, doctorDOB, doctorRegDate, doctorPhone, loginName, password;

    /**
     * Initializes the controller class.
     */
//    private static LoginAsDoctorFormController instance;
//
//    public static LoginAsDoctorFormController getInstance() {
//        return instance;
//    }
//
//    public LoginAsDoctorFormController() {
//        instance = this;
//    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        homeBtn.setCursor(Cursor.HAND);
        loginBtn.setCursor(Cursor.HAND);

    }

    @FXML
    private void handleHomeBtn(ActionEvent event) {
       

    }

    @FXML
    private void handleLoginBtn(ActionEvent event) {

        doctor = new Doctor(0, "", "", "", "",
                "", "", "", "", "");

//        task = new Task() {
//            @Override
//            protected Object call() throws Exception {
//
//                for (int i = 0; i < 100; i++) {
//                    Thread.sleep(1);
//                    updateMessage(String.valueOf(i));
//                    updateProgress(i, 100);
//
//                }
//
//                return true;
//
//            }
//
//            @Override
//            protected void cancelled() {
//                super.cancelled(); //To change body of generated methods, choose Tools | Templates.
//                System.out.println("cancelled");
//            }
//
//        };
//        task.setOnSucceeded((WorkerStateEvent evt) -> {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorDashBoard.fxml"));
        //instantiate the DOctorDashBoardController
        //DoctorDashBoardController dashController = new DoctorDashBoardController();

        try {
            Connection connection = DBConnection.Connect();

            String sql = "SELECT `doctor_id`, `doctor_name`, `doctor_address`, "
                    + "`doctor_phone_number`, `doctor_gender`, `doctor_pic`, `doctor_DOB`, "
                    + "`specialty`, `doctor_reg_date`,`doctor_login`, `doctor_password` FROM `doctor`"
                    + "WHERE doctor_login = '" + doctorUserNameField.getText() + "'AND doctor_password = '" + doctorPasswordField.getText() + "'";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            if (rs.next()) {
                doctorName = rs.getString("doctor_name");
                doctorId = rs.getInt("doctor_id");
                doctorSpecialty = rs.getString("specialty");
                doctorPhone = rs.getString("doctor_phone_number");
                doctorAddress = rs.getString("doctor_address");
                doctorGender = rs.getString("doctor_gender");
                doctorDOB = rs.getString("doctor_DOB");
                doctorRegDate = rs.getString("doctor_reg_date");
                loginName = rs.getString("doctor_login");
                password = rs.getString("doctor_password");

                System.out.println("ID:" + doctorId + "\n" + "NAME:" + doctorName + "\nSPECIALTY" + doctorSpecialty + "\nPHONE" + doctorPhone
                        + "\nADDRESS:" + doctorAddress + "\nGENDER:" + doctorGender + "\nDOB:" + doctorDOB + "\nREG DATE:" + doctorRegDate);
                Parent doctorParent = (Parent) loader.load();
//                dashController.getProfile("1", doctorName, doctorSpecialty, loginName);
//                loader.setController(dashController);
                Scene scene = new Scene(doctorParent);
                Stage doctorStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                doctorStage.setScene(scene);
                doctorStage.show();

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("INCORRECT USERNAME OR PASSWORD");
                alert.showAndWait();

            }

            doctor.setDoctorId(doctorId);
            doctor.setDoctorSpecialty(doctorSpecialty);
            doctor.setDoctorAddress(doctorAddress);
            doctor.setStringUserName(doctorName);
            doctor.setUserGender(doctorGender);
            doctor.setUserDateOfBirth(doctorDOB);
            doctor.setUserRegistrationDate(doctorRegDate);
            doctor.setUserPhoneNumber(doctorPhone);
            doctor.setUserFirstName(loginName);
            doctor.setStringPassword(password);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
//        });
//        progressBar.progressProperty().bind(task.progressProperty());
//        counterLabel.textProperty().bind(task.messageProperty());
//        new Thread(task).start();

    }

    public Doctor getDoctor() {
        return doctor;
    }

}
