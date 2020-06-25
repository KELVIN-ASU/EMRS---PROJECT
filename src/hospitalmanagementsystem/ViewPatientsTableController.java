/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagementsystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Ekuri PC
 */
public class ViewPatientsTableController implements Initializable {

    @FXML
    private TableView<Patient> patientTable;

    @FXML
    private TableColumn<Patient, String> ssnCol;

    @FXML
    private TableColumn<Patient, String> nameCol;

    @FXML
    private TableColumn<Patient, String> genderCol;

    @FXML
    private TableColumn<Patient, String> dobCol;

    @FXML
    private TableColumn<Patient, String> addressCol;

    @FXML
    private TableColumn<Patient, String> heightCol;

    @FXML
    private TableColumn<Patient, String> weightCol;

    @FXML
    private TableColumn<Patient, String> phoneCol;

    @FXML
    private TableColumn<Patient, String> bloodGroupCol;

    @FXML
    private TableColumn<Patient, String> doctorAssignedCol;

    @FXML
    private TableColumn<Patient, String> regDateCol;
    @FXML
    private TextField searchBar;

    private final ObservableList<Patient> data = FXCollections.observableArrayList();

    Connection connection = DBConnection.Connect();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fillTable();

        //performing the search 
        FilteredList<Patient> filteredData = new FilteredList<>(data, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(patient -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; //return the empty list
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (patient.getSsn().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;// filter matches firstName

                } else if (patient.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (patient.getGender().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (patient.getBloodGroup().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (patient.getAssignedDoctor().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });

        SortedList<Patient> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(patientTable.comparatorProperty());
        patientTable.setItems(sortedData);
    }

    public void fillTable() {
        data.clear();
        try {

            String sql = "SELECT * FROM `patient`";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                data.addAll(new Patient(
                        rs.getString("patient_ssn"),
                        rs.getString("patient_name"),
                        rs.getString("patient_gender"),
                        rs.getString("patient_DOB"),
                        rs.getString("patient_Address"),
                        rs.getString("patient_height"),
                        rs.getString("patient_weight"),
                        rs.getString("patient_phone_number"),
                        rs.getString("patient_blood_group"),
                        rs.getString("patient_doctor"),
                        rs.getString("reg_date")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ViewPatientsTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ssnCol.setCellValueFactory(new PropertyValueFactory<>("ssn"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        heightCol.setCellValueFactory(new PropertyValueFactory<>("height"));
        weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        bloodGroupCol.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));
        doctorAssignedCol.setCellValueFactory(new PropertyValueFactory<>("assignedDoctor"));
        regDateCol.setCellValueFactory(new PropertyValueFactory<>("regDate"));;
        patientTable.setItems(data);

    }

}
