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
public class BiologistTableViewController implements Initializable {

    @FXML
    private TableView<Biologist> biologistTable;

    @FXML
    private TableColumn<Biologist, String> biologistNameColumn;
    @FXML
    private TableColumn<Biologist, String> biogistGenderColumn;
    @FXML
    private TableColumn<Biologist, String> biologistAddressColumn;
    @FXML
    private TableColumn<Biologist, String> biologistDOBColumn;
    @FXML
    private TableColumn<Biologist, String> biologistRegDateColumn;
    @FXML
    private TableColumn<Biologist, String> biologistPhoneNumColumn;
    @FXML
    private TableColumn<Biologist, String> biologistUserNameColumn;
    @FXML
    private TableColumn<Biologist, String> biologistPasswordColumn;
    @FXML
    private TextField searchBar;
    private final ObservableList<Biologist> data = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    Connection connection = DBConnection.Connect();
    @FXML
    private TableColumn<Biologist, Integer> biologistIDColumn;
    @FXML
    private JFXButton deleteBtn;
    @FXML
    private JFXTextField biologistNameField;
    @FXML
    private JFXTextField biologistLoginName;
    @FXML
    private JFXTextField biologistAddressField;
    @FXML
    private JFXTextField biologistPhnField;
    @FXML
    private JFXTextField biologistGenderField;
    @FXML
    private JFXComboBox<String> genderComboBox;
    @FXML
    private JFXDatePicker biologistDOBPicker;
    @FXML
    private JFXButton editRecordBtn;
    @FXML
    private JFXButton updateRecordsBtn;
    @FXML
    private JFXTextField biologistPasswordField;
    @FXML
    private JFXButton browseImageBtn;
    @FXML
    private JFXButton updateProfilePicBtn;
    @FXML
    private ImageView biologistImage;

    FileInputStream fis;
    File file;
    @FXML
    private JFXTextField dobHolderField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        genderComboBox.getItems().addAll("MALE", "FEMALE");
        fillTable();
        deleteBtn.setCursor(Cursor.HAND);
        updateRecordsBtn.setCursor(Cursor.HAND);
        editRecordBtn.setCursor(Cursor.HAND);
        browseImageBtn.setCursor(Cursor.HAND);
        updateProfilePicBtn.setCursor(Cursor.HAND);

        //performing the search 
        FilteredList<Biologist> filteredData = new FilteredList<>(data, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(biologist -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; //return the empty list
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (biologist.getUserFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;// filter matches firstName

                } else if (biologist.getBiologistAddress().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (biologist.getUserGender().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });

        SortedList<Biologist> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(biologistTable.comparatorProperty());
        biologistTable.setItems(sortedData);
    }

    private void fillTable() {

        data.clear();

        String sql = "SELECT biologist_id, biologist_address, biologist_name, biologist_gender, biologist_DOB,"
                + "biologist_reg_date, biologist_phn, biologist_login, biologist_password FROM biologist";
        try {
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                data.addAll(new Biologist(
                        rs.getString("biologist_address"),
                        rs.getInt("biologist_id"),
                        rs.getString("biologist_name"),
                        rs.getString("biologist_gender"),
                        rs.getString("biologist_DOB"),
                        rs.getString("biologist_reg_date"),
                        rs.getString("biologist_phn"),
                        rs.getString("biologist_login"),
                        rs.getString("biologist_password")));

            }
        } catch (SQLException ex) {
            Logger.getLogger(BiologistTableViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        biologistIDColumn.setCellValueFactory(new PropertyValueFactory<>("biologistID"));
        biologistNameColumn.setCellValueFactory(new PropertyValueFactory<>("userFirstName"));
        biogistGenderColumn.setCellValueFactory(new PropertyValueFactory<>("userGender"));
        biologistAddressColumn.setCellValueFactory(new PropertyValueFactory<>("biologistAddress"));
        biologistDOBColumn.setCellValueFactory(new PropertyValueFactory<>("userDateOfBirth"));
        biologistRegDateColumn.setCellValueFactory(new PropertyValueFactory<>("userRegistrationDate"));
        biologistPhoneNumColumn.setCellValueFactory(new PropertyValueFactory<>("userPhoneNumber"));
        biologistUserNameColumn.setCellValueFactory(new PropertyValueFactory<>("StringUserName"));
        biologistPasswordColumn.setCellValueFactory(new PropertyValueFactory<>("StringPassword"));

        biologistTable.setItems(data);
    }

    @FXML
    private void handleDeleteBtn(ActionEvent event) {
        try {
            Alert al = new Alert(Alert.AlertType.CONFIRMATION);
            al.setContentText("ARE YOU SURE YOU WANT TO DELETE "
                    + biologistTable.getSelectionModel().getSelectedItem().getUserFirstName()
                    + "'s RECORD?");
            Optional<ButtonType> action = al.showAndWait();

            if (action.get() == ButtonType.OK) {

                try {
                    String sql = "DELETE FROM biologist WHERE biologist_id = ?";

                    PreparedStatement smt = connection.prepareStatement(sql);
                    smt.setInt(1, biologistTable.getSelectionModel().getSelectedItem().getBiologistID());
                    int i = smt.executeUpdate();
                    if (i != 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText(biologistTable.getSelectionModel().getSelectedItem().getUserFirstName()
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

    }

    @FXML
    private void handleEditRecord(ActionEvent event) {
        Biologist selected = biologistTable.getSelectionModel().getSelectedItem();
        biologistNameField.setText(selected.getUserFirstName());
        biologistAddressField.setText(selected.getBiologistAddress());
        biologistLoginName.setText(selected.getStringUserName());
        biologistPhnField.setText(selected.getUserPhoneNumber());
        biologistGenderField.setText(selected.getUserGender());
        ((TextField) biologistDOBPicker.getEditor()).setText(selected.getUserDateOfBirth());
        biologistPasswordField.setText(selected.getStringPassword());
        getProfilePic();
        dobHolderField.setText(selected.getUserDateOfBirth());

    }

    @FXML
    private void handleUpdateRecordsBtn(ActionEvent event) {
        LocalDate dateOfBirth = biologistDOBPicker.getValue();
        Biologist selected = biologistTable.getSelectionModel().getSelectedItem();

        String sql = "UPDATE biologist  SET biologist_name = ?, biologist_gender = ?, biologist_address = ?,"
                + "biologist_DOB = ?, biologist_phn = ?, biologist_login = ?,"
                + "biologist_password = ? "
                + "WHERE biologist_id = ?";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, biologistNameField.getText().toUpperCase());
            pst.setString(2, biologistGenderField.getText().toUpperCase());
            pst.setString(3, biologistAddressField.getText());
            pst.setString(4, dobHolderField.getText());
            pst.setString(5, biologistPhnField.getText());
            pst.setString(6, biologistLoginName.getText());
            pst.setString(7, biologistPasswordField.getText());
            pst.setInt(8, selected.getBiologistID());

            int k = pst.executeUpdate();
            if (k != 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(biologistNameField.getText().toUpperCase()
                        + "'S RECORD SUCCESSFULLY UPDATED");
                alert.showAndWait();
                fillTable();
            }

        } catch (SQLException ex) {
            Logger.getLogger(BiologistTableViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void getBiologistGenderFromGenderCmbBox(ActionEvent event) {
        biologistGenderField.setText(genderComboBox.getValue());
    }

    @FXML
    private void handlebrowseImageBtn(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(browseImageBtn.getScene().getWindow());
        try {
            fis = new FileInputStream(file);
            Image img = new Image(fis);
            biologistImage.setImage(img);
        } catch (FileNotFoundException | NullPointerException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("NO IMAGE SELECTED");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleupdateProfilePicBtn(ActionEvent event) {

        String sql = "UPDATE biologist SET profile_pic = ? WHERE biologist_id = ?";
        try {
            fis = new FileInputStream(file);
            PreparedStatement updateStmt = connection.prepareStatement(sql);
            updateStmt.setBinaryStream(1, (InputStream) fis, (int) file.length());
            updateStmt.setInt(2, biologistTable.getSelectionModel().getSelectedItem().getBiologistID());
            int i = updateStmt.executeUpdate();
            if (i != 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(biologistTable.getSelectionModel().getSelectedItem().getUserFirstName()
                        + "'S PROFILE PICTURE UPDATED");
                alert.showAndWait();
                fillTable();
            }
        } catch (SQLException ex) {
            ex.getMessage();
        } catch (FileNotFoundException ex) {
            ex.getMessage();
        }

    }

    public void getProfilePic() {
        try {

            ResultSet rs = null;
            String getImageQuery = "SELECT `profile_pic` FROM `biologist` WHERE biologist_id = ?";

            PreparedStatement stmt = connection.prepareStatement(getImageQuery);
            stmt.setInt(1, biologistTable.getSelectionModel().getSelectedItem().getBiologistID());
            rs = stmt.executeQuery();
            if (rs.first()) {
                Blob blob = rs.getBlob("profile_pic");
                InputStream inputStream = blob.getBinaryStream();
                Image image = new Image(inputStream);
                biologistImage.setImage(image);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoctorTableViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void passDateOfBirthValueToTextField(ActionEvent event) {
         LocalDate dateOfBirth = biologistDOBPicker.getValue();
        dobHolderField.setText(dateOfBirth.toString());
    }

}
