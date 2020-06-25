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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Ekuri PC
 */
public class AdminTableViewController implements Initializable {

    @FXML
    private TableView<Admin> adminTable;
    @FXML
    private TableColumn<Admin, String> adminNameColumn;
    @FXML
    private TableColumn<Admin, String> adminGenderColumn;
    @FXML
    private TableColumn<Admin, String> adminDOBColumn;
    @FXML
    private TableColumn<Admin, String> adminRegDateColumn;
    @FXML
    private TableColumn<Admin, String> adminPhoneNumberColumn;
    @FXML
    private TableColumn<Admin, String> adminUserNameColumn;
    @FXML
    private TableColumn<Admin, String> adminPasswordColumn;
    @FXML
    private TableColumn<Admin, String> adminIconColumn;
    @FXML
    private ImageView sampleView;

    private final ObservableList<Admin> data = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ImageView imgView = new ImageView(new Image(this.getClass().getResourceAsStream("ICONS/admin.png")));
        // TODO

        Connection conn = DBConnection.Connect();
        ResultSet rs = null;
        String sql = "SELECT admin_name, admin_gender, admin_DOB, "
                + "admin_reg_date, admin_phn, admin_login, "
                + "admin_password, profile_pic FROM admin";
        try {
            rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                data.addAll(new Admin(
                        rs.getString("admin_name"),
                        rs.getString("admin_gender"),
                        rs.getString("admin_DOB"),
                        rs.getString("admin_reg_date"),
                        rs.getString("admin_phn"),
                        imgView,
                        rs.getString("admin_login"),
                        rs.getString("admin_password")
                ));
                adminIconColumn.setCellValueFactory(new PropertyValueFactory<>("userProfilepic"));
            }
        } catch (SQLException ex) {

        }
        adminNameColumn.setCellValueFactory(new PropertyValueFactory<>("userFirstName"));
        adminGenderColumn.setCellValueFactory(new PropertyValueFactory<>("userGender"));
        adminDOBColumn.setCellValueFactory(new PropertyValueFactory<>("userDateOfBirth"));
        adminRegDateColumn.setCellValueFactory(new PropertyValueFactory<>("userRegistrationDate"));
        adminPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("userPhoneNumber"));
        adminUserNameColumn.setCellValueFactory(new PropertyValueFactory<>("StringUserName"));
        adminPasswordColumn.setCellValueFactory(new PropertyValueFactory<>("StringPassword"));
        adminIconColumn.setCellValueFactory(new PropertyValueFactory<>("userProfilepic"));
        adminTable.setItems(data);

    }

}
