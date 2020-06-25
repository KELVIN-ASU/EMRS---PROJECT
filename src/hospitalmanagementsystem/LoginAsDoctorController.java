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
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ekuri PC
 */
public class LoginAsDoctorController implements Initializable {
    
    @FXML
    private JFXTextField doctorLoginNameField;
    @FXML
    private JFXPasswordField doctorPasswordField;
    @FXML
    private JFXButton loginBtn;
    @FXML
    private JFXButton homeBtn;
    
    Doctor doctor;
    
    String doctorId, doctorSpecialty, doctorAddress, doctorName, doctorGender, doctorDOB, doctorRegDate, doctorPhone, loginName, password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loginBtn.setCursor(Cursor.HAND);
        homeBtn.setCursor(Cursor.HAND);
    }
    
    @FXML
    private void handleHomeBtn(ActionEvent event) {

        // task.cancel();
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
        doctor = new Doctor(0, "", "", "", "",
                "", "", "", "", "");
        try {
            //PERFORM CONNECTIVITY TO DATABASE
            Connection connection = DBConnection.Connect();
            
            String sql = "SELECT `doctor_id`, `doctor_name`, `doctor_address`, "
                    + "`doctor_phone_number`, `doctor_gender`, `doctor_pic`, `doctor_DOB`, "
                    + "`specialty`, `doctor_reg_date`,`doctor_login`, `doctor_password` FROM `doctor`"
                    + "WHERE doctor_login = '" + doctorLoginNameField.getText() + "'AND doctor_password = '" + doctorPasswordField.getText() + "'";
            
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            
            if (rs.next()) {
                doctorName = rs.getString("doctor_name");
                doctorId = rs.getString("doctor_id");
                doctorSpecialty = rs.getString("specialty");
                doctorPhone = rs.getString("doctor_phone_number");
                doctorAddress = rs.getString("doctor_address");
                doctorGender = rs.getString("doctor_gender");
                doctorDOB = rs.getString("doctor_DOB");
                doctorRegDate = rs.getString("doctor_reg_date");
                loginName = rs.getString("doctor_login");
                password = rs.getString("doctor_password");
                //GET DOCTOR PROFILE PICTURE AS BLOB
                Blob blob = rs.getBlob("doctor_pic");
                InputStream inputStream = blob.getBinaryStream();
                Image image = new Image(inputStream);
                
                System.out.println("ID:" + doctorId + "\n" + "NAME:" + doctorName + "\nSPECIALTY" + doctorSpecialty + "\nPHONE" + doctorPhone
                        + "\nADDRESS:" + doctorAddress + "\nGENDER:" + doctorGender + "\nDOB:" + doctorDOB + "\nREG DATE:" + doctorRegDate);
                //Load second scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("doctorDashBoard.fxml"));
                
                Parent root = loader.load();
                //Get controller of scene2
                DoctorDashBoardController doctorController = loader.getController();
                doctorController.loadDoctorInfo(doctorId, doctorName, doctorSpecialty, doctorPhone);
                
                doctorController.getDoctorProfilePic(image);
                doctorController.fillPatientsInDoctorsQueue(doctorName);
                doctorController.fillPatientsSentToLabTable(doctorName);

                //Show scene 2 in new window            
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                //stage.setTitle("DOCTOR'S DASHBOARD");
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("INCORRECT USERNAME OR PASSWORD");
                alert.showAndWait();
                
            }
            
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
    
}
