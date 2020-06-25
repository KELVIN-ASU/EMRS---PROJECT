/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagementsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Ekuri PC
 */
public class DoctorTableViewController implements Initializable {

    @FXML
    private TableView<Doctor> doctorTable;
    @FXML
    private TableColumn<Doctor, String> doctorIDColumn;
    @FXML
    private TableColumn<Doctor, String> doctorNameColumn;
    @FXML
    private TableColumn<Doctor, String> doctorAddressColumn;
    @FXML
    private TableColumn<Doctor, String> dpctprPhoneColumn;
    @FXML
    private TableColumn<Doctor, String> doctorGenderColumn;
    @FXML
    private TableColumn<Doctor, String> doctorDOBColumn;
    @FXML
    private TableColumn<Doctor, String> doctorSpecialtyColumn;
    @FXML
    private TableColumn<Doctor, String> doctorRegDateColumn;
    @FXML
    private TableColumn<Doctor, String> doctorUserNameColumn;
    @FXML
    private TableColumn<Doctor, String> doctorPasswordColumn;
    @FXML
    private TextField searchBar;

    private final ObservableList<Doctor> data = FXCollections.observableArrayList();
    @FXML
    private JFXTextField doctorPhoneField;
    @FXML
    private JFXTextField doctorNameField;
    @FXML
    private JFXTextField doctorUserNameField;
    @FXML
    private JFXTextField doctorAddressField;
    @FXML
    private JFXComboBox<?> doctorSpecialtyCmbBox;
    @FXML
    private JFXTextField doctorPasswordField;
    @FXML
    private JFXComboBox<String> doctorSexCmbBox;
    @FXML
    private JFXDatePicker doctorDOBField;
    @FXML
    private ImageView doctorImage;
    @FXML
    private JFXButton browseBtn;
    @FXML
    private JFXButton editBtn;

    FileInputStream fis;
    File file;
    @FXML
    private JFXTextField doctorGenderField;
    @FXML
    private JFXTextField doctorSpecialtyField;
    @FXML
    private JFXButton updateBtn;

    Connection conn = DBConnection.Connect();
    ResultSet rs = null;
    @FXML
    private JFXButton deleteDoctorBtn;
    @FXML
    private JFXButton updateProfilePicBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fillGenderComboBox();
        updateProfilePicBtn.setCursor(Cursor.HAND);
        deleteDoctorBtn.setCursor(Cursor.HAND);
        updateBtn.setCursor(Cursor.HAND);
        editBtn.setCursor(Cursor.HAND);
        doctorSexCmbBox.getItems().addAll("MALE", "FEMALE");
        fillTable();

        //performing the search 
        FilteredList<Doctor> filteredData = new FilteredList<>(data, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(doctor -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; //return the empty list
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (doctor.getUserFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;// filter matches firstName

                } else if (doctor.getDoctorSpecialty().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (doctor.getUserGender().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (doctor.getDoctorAddress().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });

        SortedList<Doctor> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(doctorTable.comparatorProperty());
        doctorTable.setItems(sortedData);

    }

    @FXML
    private void handleEditBtn(ActionEvent event) {
        try {
            Doctor selected = doctorTable.getSelectionModel().getSelectedItem();
            doctorNameField.setText(selected.getUserFirstName());
            doctorAddressField.setText(selected.getDoctorAddress());
            doctorPhoneField.setText(selected.getUserPhoneNumber());
            doctorUserNameField.setText(selected.getStringUserName());
            doctorPasswordField.setText(selected.getStringPassword());
            doctorSpecialtyField.setText(selected.getDoctorSpecialty());
            doctorGenderField.setText(selected.getUserGender());
            ((TextField) doctorDOBField.getEditor()).setText(selected.getUserDateOfBirth());
            getProfilePic();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("NO RECORD SELECTED\nSELECT RECORD TO EDIT FROM TABLE");
            alert.showAndWait();
        }

    }

    @FXML
    private void handleBrowseBtn(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(browseBtn.getScene().getWindow());
        try {
            fis = new FileInputStream(file);
            Image img = new Image(fis);
            doctorImage.setImage(img);
        } catch (FileNotFoundException | NullPointerException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("NO IMAGE SELECTED");
            alert.showAndWait();
        }

    }

    @FXML
    private void EditDoctorSpecialtyBySelectingFromCmb(ActionEvent event) {

        doctorSpecialtyField.setText(doctorSpecialtyCmbBox.getValue().toString());
    }

    @FXML
    private void getDoctorGender(ActionEvent event) {
        doctorGenderField.setText(doctorSexCmbBox.getValue().toString());
    }

    public void fillGenderComboBox() {
        UtilityMethods utilMethod = new UtilityMethods();
        String query = "SELECT * FROM `specialty`";
        utilMethod.fillComboBox(doctorSpecialtyCmbBox, query, "specialty_name");

    }

    public void getProfilePic() {
        try {
            //Connection connection = DBConnection.Connect();
            ResultSet rs = null;
            String getImageQuery = "SELECT `doctor_pic` FROM `doctor` WHERE doctor_id = ?";
            // + doctorNameField.getText() 

            PreparedStatement stmt = conn.prepareStatement(getImageQuery);
            stmt.setInt(1, doctorTable.getSelectionModel().getSelectedItem().getDoctorId());
            rs = stmt.executeQuery();
            if (rs.first()) {
                Blob blob = rs.getBlob("doctor_pic");
                InputStream inputStream = blob.getBinaryStream();
                Image image = new Image(inputStream);
                doctorImage.setImage(image);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoctorTableViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void fillTable() {
        data.clear();
        String sql = "SELECT  doctor_id, doctor_name, doctor_address, doctor_phone_number, doctor_gender, doctor_DOB, specialty,"
                + "doctor_reg_date, doctor_login, doctor_password, doctor_pic FROM doctor";

        try {
            rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                data.addAll(new Doctor(
                        rs.getInt("doctor_id"),
                        rs.getString("specialty"),
                        rs.getString("doctor_address"),
                        rs.getString("doctor_name"),
                        rs.getString("doctor_gender"),
                        rs.getString("doctor_DOB"),
                        rs.getString("doctor_reg_date"),
                        rs.getString("doctor_phone_number"),
                        rs.getString("doctor_login"),
                        rs.getString("doctor_password")
                ));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        doctorIDColumn.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
        doctorNameColumn.setCellValueFactory(new PropertyValueFactory<>("userFirstName"));
        doctorGenderColumn.setCellValueFactory(new PropertyValueFactory<>("userGender"));
        doctorDOBColumn.setCellValueFactory(new PropertyValueFactory<>("userDateOfBirth"));
        doctorRegDateColumn.setCellValueFactory(new PropertyValueFactory<>("userRegistrationDate"));
        dpctprPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("userPhoneNumber"));
        doctorUserNameColumn.setCellValueFactory(new PropertyValueFactory<>("StringUserName"));
        doctorPasswordColumn.setCellValueFactory(new PropertyValueFactory<>("StringPassword"));
        doctorAddressColumn.setCellValueFactory(new PropertyValueFactory<>("doctorAddress"));
        doctorSpecialtyColumn.setCellValueFactory(new PropertyValueFactory<>("doctorSpecialty"));

        doctorTable.setItems(data);

    }

    @FXML
    private void handleUpdateBtn(ActionEvent event) {
        LocalDate dateOfBirth = doctorDOBField.getValue();
        Doctor selected = doctorTable.getSelectionModel().getSelectedItem();
        try {

            String sql = "UPDATE doctor SET doctor_name = ?, doctor_address = ?,"
                    + "doctor_phone_number = ?, doctor_gender = ?, specialty = ?, doctor_login = ?, doctor_password = ? "
                    + "WHERE doctor_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, doctorNameField.getText().toUpperCase());
            pst.setString(2, doctorAddressField.getText());
            pst.setString(3, doctorPhoneField.getText());
            pst.setString(4, doctorGenderField.getText());
            //pst.setBinaryStream(5, (InputStream) fis, (int) file.length());
            //pst.setString(6, dateOfBirth.toString());
            pst.setString(5, doctorSpecialtyField.getText().toUpperCase());
            pst.setString(6, doctorUserNameField.getText());
            pst.setString(7, doctorPasswordField.getText());
            pst.setInt(8, selected.getDoctorId());
            int k = pst.executeUpdate();
            if (k != 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("DOCTOR "
                        + doctorNameField.getText().toUpperCase()
                        + "'s RECORD SUCCESSFULLY UPDATED");
                alert.showAndWait();
                fillTable();
            }
        } catch (NullPointerException npe) {
            npe.getMessage();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    @FXML
    private void handleDeleteDoctorBtn(ActionEvent event) {
        Alert al = new Alert(Alert.AlertType.CONFIRMATION);
        al.setContentText("ARE YOU SURE YOU WANT TO DELETE THE SELECTED RECORD?");
        Optional<ButtonType> action = al.showAndWait();

        if (action.get() == ButtonType.OK) {

            try {
                PreparedStatement deleteStmt;
                String sql = "DELETE FROM doctor where doctor_id = ?";
                deleteStmt = conn.prepareCall(sql);
                deleteStmt.setInt(1, doctorTable.getSelectionModel().getSelectedItem().getDoctorId());
                int i = deleteStmt.executeUpdate();
                if (i != 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("DOCTOR "
                            + doctorTable.getSelectionModel().getSelectedItem().getUserFirstName()
                            + "'S RECORD DELETED FROM DATABASE");
                    alert.showAndWait();
                    fillTable();
                }
            } catch (NullPointerException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("NO RECORED SELECTED TO DELETE\nENSURE A RECORD TO DELETE IS SELECTED FROM TABLE BY CLICKING ON THE DESIRED ROW");
                alert.showAndWait();
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("RECORD DELETE HAS FATAL ERROR");
                alert.showAndWait();

            }

        }

    }

    @FXML
    private void handleUpdateProfilePictureBtn(ActionEvent event) {

        String sql = "UPDATE doctor SET doctor_pic = ? WHERE doctor_id = ?";
        try {
            fis = new FileInputStream(file);
            PreparedStatement updateStmt = conn.prepareStatement(sql);
            updateStmt.setBinaryStream(1, (InputStream) fis, (int) file.length());
            updateStmt.setInt(2, doctorTable.getSelectionModel().getSelectedItem().getDoctorId());
            int i = updateStmt.executeUpdate();
            if (i != 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("DOCTOR "
                        + doctorTable.getSelectionModel().getSelectedItem().getUserFirstName()
                        + "'s PROFILE PICTURE SUCCESSFULLY UPDATED");
                alert.showAndWait();
                fillTable();
            }
        } catch (SQLException ex) {
            ex.getMessage();
        } catch (FileNotFoundException ex) {
            ex.getMessage();
        }

    }

}
