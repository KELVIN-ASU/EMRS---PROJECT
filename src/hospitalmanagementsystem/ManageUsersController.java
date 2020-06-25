/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagementsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ekuri PC
 */
public class ManageUsersController implements Initializable {

    @FXML
    private JFXComboBox<String> userTypeComboBox;
    @FXML
    private JFXTextField userNameTextField;
    @FXML
    private JFXRadioButton rdoMale;
    @FXML
    private JFXRadioButton rdoFemale;
    @FXML
    private JFXDatePicker dob;
    @FXML
    private JFXTextField phoneNumberTextField;
    @FXML
    private JFXComboBox<String> specialtyCmbBox;
    @FXML
    private JFXDatePicker registrationDate;
    @FXML
    private JFXTextField loginNameTextField;
    @FXML
    private JFXPasswordField password1Field;
    @FXML
    private JFXButton saveBtn;
    @FXML
    private JFXButton resetBtn;
    @FXML
    private ImageView profilePic;
    @FXML
    private Button browseBtn;
    @FXML
    private Label radioLabel;
    @FXML
    private JFXTextField addressTextField;
    private FileInputStream fis;
    File f;//this the file that will hold the image that will be passed to the input stream

    @FXML
    private JFXPasswordField password2Field;
    @FXML
    private ImageView addSpecialtyImageBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        resetBtn.setCursor(Cursor.HAND);
        browseBtn.setCursor(Cursor.HAND);
        saveBtn.setCursor(Cursor.HAND);
        ToggleGroup genderGroup = new ToggleGroup();
        rdoMale.setToggleGroup(genderGroup);
        rdoFemale.setToggleGroup(genderGroup);
        rdoMale.setSelected(true);
        userTypeComboBox.getItems().addAll("Admin", "Pharmacist", "Doctor", "Biologist", "Receptionist");
        radioLabel.setText(rdoMale.getText());
    }

    //method to save the input form data in database
    //this method is invoked when the save button is pressed
    @FXML
    private void handleSaveBtn(ActionEvent event) {

        LocalDate date_of_birth = dob.getValue(); //get date of birth value from calender and convert it to local date
        LocalDate reg_date = registrationDate.getValue(); //get registration date from calendar and convert it to local date
        Connection connection = DBConnection.Connect();//creates and instance of the DBConnection class and invokes the Connect Method
        String sql;
        PreparedStatement pst;
        //Beginning of if-else block
        if (userTypeComboBox.getValue().equals("Admin")) {
            sql = "INSERT INTO admin (admin_name, admin_gender, admin_DOB, admin_reg_date, admin_phn, profile_pic, admin_login, admin_password)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                fis = new FileInputStream(f);
                pst = connection.prepareStatement(sql);
                pst.setString(1, userNameTextField.getText().toUpperCase());
                pst.setString(2, radioLabel.getText().toUpperCase());
                pst.setString(3, date_of_birth.toString());
                pst.setString(4, reg_date.toString());
                pst.setString(5, phoneNumberTextField.getText());
                pst.setBinaryStream(6, (InputStream) fis, (int) f.length());
                pst.setString(7, loginNameTextField.getText());
                pst.setString(8, password1Field.getText());

                int i = pst.executeUpdate();
                if (i != 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("NEW ADMIN RECORD ADDED TO DATABASE");
                    alert.showAndWait();
                }

            } catch (FileNotFoundException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("FILE UPLOAD ERROR");
                alert.showAndWait();
            } catch (SQLException ex) {
                Logger.getLogger(ManageUsersController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (userTypeComboBox.getValue().equals("Doctor")) {

            sql = "INSERT INTO doctor("
                    + "doctor_name,"
                    + " doctor_address,"
                    + "doctor_phone_number,"
                    + "doctor_gender,"
                    + "doctor_pic,"
                    + "doctor_DOB, "
                    + "specialty, "
                    + "doctor_reg_date, "
                    + "doctor_login, "
                    + "doctor_password)"
                    + "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                fis = new FileInputStream(f);
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, userNameTextField.getText().toUpperCase());
                statement.setString(2, addressTextField.getText());
                statement.setString(3, phoneNumberTextField.getText());
                statement.setString(4, radioLabel.getText().toUpperCase());
                statement.setBinaryStream(5, (InputStream) fis, (int) f.length());
                statement.setString(6, date_of_birth.toString());
                statement.setString(7, specialtyCmbBox.getValue());
                statement.setString(8, reg_date.toString());
                statement.setString(9, loginNameTextField.getText());
                statement.setString(10, password1Field.getText());
                int i = statement.executeUpdate();
                if (i != 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("NEW DOCTOR RECORDED SUCCESSFULLY ADDED TO DATABASE");
                    alert.showAndWait();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManageUsersController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                System.out.println("COULDN'T PASS FILE TO INPUT STREAM");
            }

        } else if (userTypeComboBox.getValue().equals("Receptionist")) {
            sql = "INSERT INTO receptionist (receptionist_name, receptionist_gender, receptionist_address, receptionist_phn, receptionist_reg_date, receptionist_DOB, profile_pic, receptionist_login, receptionist_password)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                fis = new FileInputStream(f);
                pst = connection.prepareStatement(sql);
                pst.setString(1, userNameTextField.getText().toUpperCase());
                pst.setString(2, radioLabel.getText().toUpperCase());
                pst.setString(3, addressTextField.getText());
                pst.setString(4, phoneNumberTextField.getText());
                pst.setString(5, reg_date.toString());
                pst.setString(6, date_of_birth.toString());
                pst.setBinaryStream(7, (InputStream) fis, (int) f.length());
                pst.setString(8, loginNameTextField.getText());
                pst.setString(9, password1Field.getText());

                int i = pst.executeUpdate();
                if (i != 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("NEW RECEPTIONIST RECORD SUCCESSFULLY ADDED TO DATABASE");
                    alert.showAndWait();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManageUsersController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ManageUsersController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (userTypeComboBox.getValue().equals("Pharmacist")) {
            sql = "INSERT INTO pharmacist(pharmacist_name, pharnacist_gender, pharmacist_address, pharnacist_phn, "
                    + "pharnacist_DOB, pharmacist_reg_date, profile_pic, pharnacist_login, pharmacist_password)"
                    + "VALUES (?,?,?,?,?,?,?,?,?)";

            try {
                fis = new FileInputStream(f);
                pst = connection.prepareStatement(sql);

                pst = connection.prepareStatement(sql);
                pst.setString(1, userNameTextField.getText().toUpperCase());
                pst.setString(2, radioLabel.getText().toUpperCase());
                pst.setString(3, addressTextField.getText());
                pst.setString(4, phoneNumberTextField.getText());
                pst.setString(5, date_of_birth.toString());
                pst.setString(6, reg_date.toString());
                pst.setBinaryStream(7, (InputStream) fis, (int) f.length());
                pst.setString(8, loginNameTextField.getText());
                pst.setString(9, password1Field.getText());

                int i = pst.executeUpdate();
                if (i != 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("NEW PHARMACIST RECORD SUCCESSFULLY ADDED TO DATABASE");
                    alert.showAndWait();
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ManageUsersController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ManageUsersController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (userTypeComboBox.getValue().equals("Biologist")) { //insert into biologist table depending on the value of the userType comboBox
            sql = "INSERT INTO biologist (biologist_name, biologist_gender, biologist_address, "
                    + "biologist_DOB, biologist_reg_date, biologist_phn, profile_pic, biologist_login, biologist_password) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                fis = new FileInputStream(f);
                pst = connection.prepareStatement(sql);

                pst = connection.prepareStatement(sql);
                pst.setString(1, userNameTextField.getText().toUpperCase());
                pst.setString(2, radioLabel.getText().toUpperCase());
                pst.setString(3, addressTextField.getText());
                pst.setString(4, date_of_birth.toString());
                pst.setString(5, reg_date.toString());
                pst.setString(6, phoneNumberTextField.getText());
                pst.setBinaryStream(7, (InputStream) fis, (int) f.length());
                pst.setString(8, loginNameTextField.getText());
                pst.setString(9, password1Field.getText());

                int i = pst.executeUpdate();
                if (i != 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("NEW BIOLOGIST RECORD SUCCESSFULLY ADDED TO DATABASE");
                    alert.showAndWait();
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ManageUsersController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ManageUsersController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("INVALID USER TYPE\nCONSULT SYSTEM MANNUAL");
            alert.showAndWait();
        }

    }//end the save button event handler

    //method invoked when the user clicks the Browse Button
    //this will open the windows explorer for the user to select a profile picture to the user account.
    @FXML
    private void handleBrowseBtn(ActionEvent event) {
        openFile();
    }

    public void openFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            // FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg","png","gif","bmp");

            f = fileChooser.showOpenDialog(browseBtn.getScene().getWindow());
            FileInputStream fileInputStream = new FileInputStream(f);
            Image image = new Image(fileInputStream);
            profilePic.setImage(image);
        } catch (Exception ex) {
            System.out.println("NO IMAGE SELECTED");
        }

    }

    @FXML
    private void getDoctorSpecialty(ActionEvent event) {
        UtilityMethods myMethod = new UtilityMethods();
        if (userTypeComboBox.getValue().toString().equalsIgnoreCase("Doctor")) {
            String query = "SELECT * FROM `specialty`";
            myMethod.fillComboBox(specialtyCmbBox, query, "specialty_name");

        } else {
            specialtyCmbBox.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void getMale(ActionEvent event) {
        radioLabel.setText(rdoMale.getText());
        //rdoFemale
    }

    @FXML
    private void getFemale(ActionEvent event) {
        radioLabel.setText(rdoFemale.getText());
    }

    @FXML
    private void handleResetBtn(ActionEvent event) {
        userNameTextField.clear();
        loginNameTextField.clear();
        password1Field.clear();
        registrationDate.getEditor().clear();
        dob.getEditor().clear();
        phoneNumberTextField.clear();
        // Image image = new Image("ICONS/people.png");
        // profilePic.setImage(image);
    }

    private void handleAddSpecialtyBtn(ActionEvent event) {

    }

    private void handleBackBtn(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("adminDashBoard.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleAddSpecialtyImageBtn(MouseEvent event) {
        Parent root;
        Stage stage;
        if (event.getSource() == addSpecialtyImageBtn) {
            try {
                stage = new Stage();
                root = FXMLLoader.load(getClass().getResource("manageDoctorSpecialty.fxml"));
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(addSpecialtyImageBtn.getScene().getWindow());
                stage.setResizable(false);
                stage.showAndWait();
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("ERROR LOADING SPECIALTY MODULE.\nMODULE HAS AN IMPLEMENTATION ERROR");
                alert.showAndWait();
            }
        }
    }

}
