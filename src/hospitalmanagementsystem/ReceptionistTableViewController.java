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
public class ReceptionistTableViewController implements Initializable {

    @FXML
    private TableView<Receptionist> receptionistTable;
    @FXML
    private TableColumn<Receptionist, Integer> receptionIDCol;
    @FXML
    private TableColumn<Receptionist, String> receptionistNameCol;
    @FXML
    private TableColumn<Receptionist, String> receptionistGenderCol;
    @FXML
    private TableColumn<Receptionist, String> receptionistAddressCol;
    @FXML
    private TableColumn<Receptionist, String> receptionistPhoneNumCol;
    @FXML
    private TableColumn<Receptionist, String> receptionistRegDateCol;
    @FXML
    private TableColumn<Receptionist, String> receptionistDOBCol;
    @FXML
    private TableColumn<Receptionist, String> receptionistUserNameCol;
    @FXML
    private TableColumn<Receptionist, String> receptionistPasswordCol;
    @FXML
    private TextField searchBar;
    private final ObservableList<Receptionist> data = FXCollections.observableArrayList();

    File file;
    FileInputStream fis;

    /**
     * Initializes the controller class.
     */
    Connection connection = DBConnection.Connect();
    @FXML
    private JFXButton deleteRecordsBtn;
    @FXML
    private JFXButton browseImageBtn;
    @FXML
    private ImageView receptionistImage;
    @FXML
    private JFXButton updateImageBtn;
    @FXML
    private JFXButton editRecordsBtn;
    @FXML
    private JFXTextField receptionistNameField;
    @FXML
    private JFXTextField receptionistGenderField;
    @FXML
    private JFXTextField receptionistAddressField;
    @FXML
    private JFXTextField receptionistPhoneNumberField;
    @FXML
    private JFXDatePicker receptionistDOB;
    @FXML
    private JFXTextField receptionistLoginNameField;
    @FXML
    private JFXTextField receptionistPasswordField;
    @FXML
    private JFXComboBox<String> receptionistGenderCmbBox;
    @FXML
    private JFXButton updateRecordsBtn;

    String receptionistDOBHolder;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        updateRecordsBtn.setCursor(Cursor.HAND);
        updateImageBtn.setCursor(Cursor.HAND);
        browseImageBtn.setCursor(Cursor.HAND);
        deleteRecordsBtn.setCursor(Cursor.HAND);
        editRecordsBtn.setCursor(Cursor.HAND);
        receptionistGenderCmbBox.getItems().addAll("MALE", "FEMALE");

        fillTable();

        //performing the search 
        FilteredList<Receptionist> filteredData = new FilteredList<>(data, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(receptionist -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; //return the empty list
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (receptionist.getUserFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;// filter matches firstName

                } else if (receptionist.getReceptionistAddress().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (receptionist.getUserGender().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });

        SortedList<Receptionist> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(receptionistTable.comparatorProperty());
        receptionistTable.setItems(sortedData);

    }

    public void fillTable() {
        data.clear();
        String sql = "SELECT * FROM receptionist";
        try {
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                data.addAll(new Receptionist(
                        rs.getString("receptionist_address"),
                        rs.getInt("receptionist_id"),
                        rs.getString("receptionist_name"),
                        rs.getString("receptionist_gender"),
                        rs.getString("receptionist_DOB"),
                        rs.getString("receptionist_reg_date"),
                        rs.getString("receptionist_phn"),
                        rs.getString("receptionist_login"),
                        rs.getString("receptionist_password")
                ));

            }
        } catch (SQLException ex) {
            Logger.getLogger(ReceptionistTableViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        receptionIDCol.setCellValueFactory(new PropertyValueFactory<>("receptionistID"));
        receptionistNameCol.setCellValueFactory(new PropertyValueFactory<>("userFirstName"));
        receptionistGenderCol.setCellValueFactory(new PropertyValueFactory<>("userGender"));
        receptionistAddressCol.setCellValueFactory(new PropertyValueFactory<>("receptionistAddress"));
        receptionistPhoneNumCol.setCellValueFactory(new PropertyValueFactory<>("userPhoneNumber"));
        receptionistRegDateCol.setCellValueFactory(new PropertyValueFactory<>("userRegistrationDate"));
        receptionistDOBCol.setCellValueFactory(new PropertyValueFactory<>("userDateOfBirth"));
        receptionistUserNameCol.setCellValueFactory(new PropertyValueFactory<>("StringUserName"));
        receptionistPasswordCol.setCellValueFactory(new PropertyValueFactory<>("StringPassword"));

        receptionistTable.setItems(data);

    }

    @FXML
    private void handleDeleteRecordsBtn(ActionEvent event) {

        try {
            Alert al = new Alert(Alert.AlertType.CONFIRMATION);
            al.setContentText("ARE YOU SURE YOU WANT TO DELETE "
                    + receptionistTable.getSelectionModel().getSelectedItem().getUserFirstName()
                    + "'s RECORD?");
            Optional<ButtonType> action = al.showAndWait();

            if (action.get() == ButtonType.OK) {

                try {
                    String sql = "DELETE FROM receptionist WHERE receptionist_id = ?";

                    PreparedStatement smt = connection.prepareStatement(sql);
                    smt.setInt(1, receptionistTable.getSelectionModel().getSelectedItem().getReceptionistID());
                    int i = smt.executeUpdate();
                    if (i != 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText(receptionistTable.getSelectionModel().getSelectedItem().getUserFirstName()
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
    private void handleBrowseImageBtn(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(browseImageBtn.getScene().getWindow());
        try {
            fis = new FileInputStream(file);
            Image img = new Image(fis);
            receptionistImage.setImage(img);
        } catch (FileNotFoundException | NullPointerException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("NO IMAGE SELECTED");
            alert.showAndWait();
        }

    }

    @FXML
    private void handleupdateImageBtn(ActionEvent event) {

        String sql = "UPDATE receptionist SET profile_pic = ? WHERE receptionist_id = ?";
        try {
            fis = new FileInputStream(file);
            PreparedStatement updateStmt = connection.prepareStatement(sql);
            updateStmt.setBinaryStream(1, (InputStream) fis, (int) file.length());
            updateStmt.setInt(2, receptionistTable.getSelectionModel().getSelectedItem().getReceptionistID());
            int i = updateStmt.executeUpdate();
            if (i != 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(receptionistTable.getSelectionModel().getSelectedItem().getUserFirstName()
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
    private void handleEditRecordsBtn(ActionEvent event) {
        Receptionist selected = receptionistTable.getSelectionModel().getSelectedItem();
        receptionistNameField.setText(selected.getUserFirstName());
        receptionistGenderField.setText(selected.getUserGender());
        receptionistAddressField.setText(selected.getReceptionistAddress());
        receptionistPhoneNumberField.setText(selected.getUserPhoneNumber());
        ((TextField) receptionistDOB.getEditor()).setText(selected.getUserDateOfBirth());
        receptionistLoginNameField.setText(selected.getStringUserName());
        receptionistPasswordField.setText(selected.getStringPassword());
        getProfilePic();
        receptionistDOBHolder = selected.getUserDateOfBirth();
    }

    @FXML
    private void getReceptionistGenderFromCmbBox(ActionEvent event) {
        receptionistGenderField.setText(receptionistGenderCmbBox.getValue());
    }

    private void getProfilePic() {
        try {

            ResultSet rs = null;
            String getImageQuery = "SELECT `profile_pic` FROM `receptionist` WHERE receptionist_id = ?";

            PreparedStatement stmt = connection.prepareStatement(getImageQuery);
            stmt.setInt(1, receptionistTable.getSelectionModel().getSelectedItem().getReceptionistID());
            rs = stmt.executeQuery();
            if (rs.first()) {
                Blob blob = rs.getBlob("profile_pic");
                InputStream inputStream = blob.getBinaryStream();
                Image image = new Image(inputStream);
                receptionistImage.setImage(image);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoctorTableViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THE SELECTED RECEPTIONIST DOESN'T HAVE A PROFILE PICTURE");
            alert.showAndWait();

        }

    }

    @FXML
    private void handleUpdateRecordsBtn(ActionEvent event) {
        Receptionist selected = receptionistTable.getSelectionModel().getSelectedItem();
        //receptionistDOBHolder = selected.getUserDateOfBirth();

        String sql = "UPDATE receptionist SET receptionist_name = ?, receptionist_gender = ?,"
                + "receptionist_address = ?, receptionist_phn = ?,"
                + "receptionist_DOB = ?, receptionist_login = ?, receptionist_password = ?"
                + "WHERE receptionist_id = ?";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, receptionistNameField.getText().toUpperCase());
            pst.setString(2, receptionistGenderField.getText().toUpperCase());
            pst.setString(3, receptionistAddressField.getText());
            pst.setString(4, receptionistPhoneNumberField.getText());
            pst.setString(5, receptionistDOBHolder);
            pst.setString(6, receptionistLoginNameField.getText());
            pst.setString(7, receptionistPasswordField.getText());
            pst.setInt(8, selected.getReceptionistID());

            int k = pst.executeUpdate();
            if (k != 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(receptionistNameField.getText().toUpperCase()
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
    private void passDateOfBirthValueToTextField(ActionEvent event) {
        LocalDate dateOfBirth = receptionistDOB.getValue();
        receptionistDOBHolder = dateOfBirth.toString();
    }

}
