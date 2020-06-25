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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ekuri PC
 */
public class LoginAsAdminFormController implements Initializable {

    @FXML
    private JFXTextField adminUserNameField;
    @FXML
    private JFXPasswordField adminPasswordField;
    @FXML
    private JFXButton homeBtn;
    @FXML
    private JFXButton loginBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        homeBtn.setCursor(Cursor.HAND);
        loginBtn.setCursor(Cursor.HAND);
    }

    @FXML
    private void handleHomeBtn(ActionEvent event) {
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
    private void handleAdminLoginBtn(ActionEvent event) {
        Connection connection = DBConnection.Connect();
        String sql = "SELECT * FROM admin WHERE admin_login = '" + adminUserNameField.getText() + "'AND admin_password = '" + adminPasswordField.getText() + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("adminDashBoard.fxml"));
                    Parent visitorParent = (Parent) loader.load();
                    Scene scene = new Scene(visitorParent);
                    Stage visitorStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    visitorStage.setScene(scene);
                    visitorStage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("INCORRECT USERNAME OR PASSWORD");
                alert.showAndWait();
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginAsAdminFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
