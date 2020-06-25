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
public class PharmacistTableViewController implements Initializable {

    @FXML
    private TableView<Pharmacist> pharmacistTable;
    @FXML
    private TableColumn<Pharmacist, Integer> pharmacistIDColumn;
    @FXML
    private TableColumn<Pharmacist, String> pharmacistNameColumn;
    @FXML
    private TableColumn<Pharmacist, String> pharmacistGenderColumn;
    @FXML
    private TableColumn<Pharmacist, String> pharmacistAddressColumn;
    @FXML
    private TableColumn<Pharmacist, String> pharmacistPhnNumberColumn;
    @FXML
    private TableColumn<Pharmacist, String> pharmacistDOBColumn;
    @FXML
    private TableColumn<Pharmacist, String> pharmacistRegDateColumn;
    @FXML
    private TableColumn<Pharmacist, String> pharmacistUserNameColumn;
    @FXML
    private TableColumn<Pharmacist, String> pharmacistPasswordColumn;

    /**
     * Initializes the controller class.
     */
    Connection connection = DBConnection.Connect();
    private final ObservableList<Pharmacist> data = FXCollections.observableArrayList();
    @FXML
    private JFXButton deleteRecordsBtn;
    @FXML
    private JFXButton updateRecordsBtn;
    @FXML
    private JFXButton editRecordsBtn;
    @FXML
    private JFXButton updateImageBtn;
    @FXML
    private JFXButton browseImageBtn;
    @FXML
    private ImageView pharmacistImage;
    @FXML
    private JFXTextField pharmacistNameField;
    @FXML
    private JFXTextField pharmacistGenderField;
    @FXML
    private JFXTextField pharmacistAddressField;
    @FXML
    private JFXTextField pharmacistPhoneField;
    @FXML
    private JFXTextField pharmacistPasswordField;
    @FXML
    private JFXTextField pharmacistUserNameField;
    @FXML
    private JFXDatePicker pharmacistDOB;
    @FXML
    private JFXComboBox<String> pharmacistGenderCmbBox;

    File file;
    FileInputStream fis;
    String dob;
    @FXML
    private TextField searchBar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fillTable();
        deleteRecordsBtn.setCursor(Cursor.HAND);
        updateRecordsBtn.setCursor(Cursor.HAND);
        editRecordsBtn.setCursor(Cursor.HAND);
        updateImageBtn.setCursor(Cursor.HAND);
        browseImageBtn.setCursor(Cursor.HAND);
        pharmacistGenderCmbBox.getItems().addAll("MALE", "FEMALE");

    }

    @FXML
    private void handleDeleteRecordsBtn(ActionEvent event) {

        try {
            Alert al = new Alert(Alert.AlertType.CONFIRMATION);
            al.setContentText("ARE YOU SURE YOU WANT TO DELETE "
                    + pharmacistTable.getSelectionModel().getSelectedItem().getUserFirstName()
                    + "'s RECORD?");
            Optional<ButtonType> action = al.showAndWait();

            if (action.get() == ButtonType.OK) {

                try {
                    String sql = "DELETE FROM pharmacist WHERE pharmacist_no= ?";

                    PreparedStatement smt = connection.prepareStatement(sql);
                    smt.setInt(1, pharmacistTable.getSelectionModel().getSelectedItem().getPharmacistID());
                    int i = smt.executeUpdate();
                    if (i != 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText(pharmacistTable.getSelectionModel().getSelectedItem().getUserFirstName()
                                + "'S RECORD SUCCESSFULLY DELETED");
                        alert.showAndWait();
                        fillTable();
                    }

                } catch (SQLException ex) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("RECORD DELETE OPERATION UNSUCCESSFULL");
                    a.showAndWait();
                }

            }

        } catch (NullPointerException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("NO RECORD SELECTED TO DELETE");
            a.showAndWait();

        }

        //end delete
    }

    @FXML
    private void handleUpdateRecordsBtn(ActionEvent event) {
        Pharmacist selected = pharmacistTable.getSelectionModel().getSelectedItem();

        String sql = "UPDATE pharmacist SET pharmacist_name = ?, pharnacist_gender = ?, pharmacist_address = ?,"
                + "pharnacist_phn =?, pharnacist_DOB = ?, pharnacist_login = ?,"
                + "pharmacist_password = ? WHERE pharmacist_no = ?";

//        
//        
//        
//        
//        
//        
        ((TextField) pharmacistDOB.getEditor()).setText(selected.getUserDateOfBirth());

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, pharmacistNameField.getText().toUpperCase());
            pst.setString(2, pharmacistGenderField.getText().toUpperCase());
            pst.setString(3, pharmacistAddressField.getText());
            pst.setString(4, pharmacistPhoneField.getText());
            pst.setString(5, dob);
            pst.setString(6, pharmacistUserNameField.getText());
            pst.setString(7, pharmacistPasswordField.getText());
            pst.setInt(8, selected.getPharmacistID());

            int k = pst.executeUpdate();
            if (k != 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(pharmacistNameField.getText().toUpperCase()
                        + "'S RECORD SUCCESSFULLY UPDATED");
                alert.showAndWait();
                fillTable();
            }

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("NO RECORD SELECTED TO UPDATE");
            alert.showAndWait();
        }

    }

    @FXML
    private void handleEditRecordsBtn(ActionEvent event) {

        Pharmacist selected = pharmacistTable.getSelectionModel().getSelectedItem();

        pharmacistNameField.setText(selected.getUserFirstName());
        pharmacistGenderField.setText(selected.getUserGender());
        pharmacistAddressField.setText(selected.getPharmacistAddress());
        pharmacistPhoneField.setText(selected.getUserPhoneNumber());
        pharmacistPasswordField.setText(selected.getStringPassword());
        pharmacistUserNameField.setText(selected.getStringUserName());
        ((TextField) pharmacistDOB.getEditor()).setText(selected.getUserDateOfBirth());
        getProfilePic();
        dob = selected.getUserDateOfBirth();
    }

    @FXML
    private void handleUpdateImageBtn(ActionEvent event) {
        String sql = "UPDATE pharmacist SET profile_pic = ? WHERE pharmacist_no = ?";
        try {
            fis = new FileInputStream(file);
            PreparedStatement updateStmt = connection.prepareStatement(sql);
            updateStmt.setBinaryStream(1, (InputStream) fis, (int) file.length());
            updateStmt.setInt(2, pharmacistTable.getSelectionModel().getSelectedItem().getPharmacistID());
            int i = updateStmt.executeUpdate();
            if (i != 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(pharmacistTable.getSelectionModel().getSelectedItem().getUserFirstName()
                        + "'S PROFILE PICTURE UPDATED");
                alert.showAndWait();
                fillTable();
            }
        } catch (SQLException ex) {
            ex.getMessage();
        } catch (FileNotFoundException ex) {
            ex.getMessage();
        } catch (NullPointerException npe) {

        }
    }

    @FXML
    private void handleBrowseImageBtn(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(browseImageBtn.getScene().getWindow());
        try {
            fis = new FileInputStream(file);
            Image img = new Image(fis);
            pharmacistImage.setImage(img);
        } catch (FileNotFoundException | NullPointerException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("NO IMAGE SELECTED");
            alert.showAndWait();
        }
    }

    @FXML
    private void passDateOfBirthValueToTextField(ActionEvent event) {
        dob = pharmacistDOB.getValue().toString();

    }

    @FXML
    private void getPharmacistGenderFromCmbBox(ActionEvent event) {
        pharmacistGenderField.setText(pharmacistGenderCmbBox.getValue());
    }

    public void fillTable() {
        data.clear();
        String sql = "SELECT pharmacist_no, pharmacist_name, pharnacist_gender, pharmacist_address, pharnacist_phn,"
                + "pharnacist_DOB, pharmacist_reg_date, pharnacist_login, pharmacist_password FROM pharmacist";
        try {
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                data.addAll(new Pharmacist(
                        rs.getInt("pharmacist_no"),
                        rs.getString("pharmacist_address"),
                        rs.getString("pharmacist_name"),
                        rs.getString("pharnacist_gender"),
                        rs.getString("pharnacist_DOB"),
                        rs.getString("pharmacist_reg_date"),
                        rs.getString("pharnacist_phn"),
                        rs.getString("pharnacist_login"),
                        rs.getString("pharmacist_password")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PharmacistTableViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        pharmacistIDColumn.setCellValueFactory(new PropertyValueFactory<>("pharmacistID"));
        pharmacistNameColumn.setCellValueFactory(new PropertyValueFactory<>("userFirstName"));
        pharmacistGenderColumn.setCellValueFactory(new PropertyValueFactory<>("userGender"));

        pharmacistAddressColumn.setCellValueFactory(new PropertyValueFactory<>("pharmacistAddress"));
        pharmacistPhnNumberColumn.setCellValueFactory(new PropertyValueFactory<>("userPhoneNumber"));
        pharmacistDOBColumn.setCellValueFactory(new PropertyValueFactory<>("userDateOfBirth"));

        pharmacistRegDateColumn.setCellValueFactory(new PropertyValueFactory<>("userRegistrationDate"));
        pharmacistUserNameColumn.setCellValueFactory(new PropertyValueFactory<>("StringUserName"));
        pharmacistPasswordColumn.setCellValueFactory(new PropertyValueFactory<>("StringPassword"));

        pharmacistTable.setItems(data);

        //performing the search 
        FilteredList<Pharmacist> filteredData = new FilteredList<>(data, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(pharmacist -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; //return the empty list
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (pharmacist.getUserFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;// filter matches firstName

                } else if (pharmacist.getUserGender().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (pharmacist.getPharmacistAddress().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });
        SortedList<Pharmacist> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(pharmacistTable.comparatorProperty());
        pharmacistTable.setItems(sortedData);
    }

    private void getProfilePic() {
        try {

            ResultSet rs = null;
            String getImageQuery = "SELECT `profile_pic` FROM `pharmacist` WHERE pharmacist_no = ?";

            PreparedStatement stmt = connection.prepareStatement(getImageQuery);
            stmt.setInt(1, pharmacistTable.getSelectionModel().getSelectedItem().getPharmacistID());
            rs = stmt.executeQuery();
            if (rs.first()) {
                Blob blob = rs.getBlob("profile_pic");
                InputStream inputStream = blob.getBinaryStream();
                Image image = new Image(inputStream);
                pharmacistImage.setImage(image);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoctorTableViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THE SELECTED PHARMACIST DOESN'T HAVE A PROFILE PICTURE");
            alert.showAndWait();

        }

    }

}
