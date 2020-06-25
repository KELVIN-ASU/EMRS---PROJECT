/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagementsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author Ekuri PC
 */
public class UtilityMethods {

    public void fillComboBox(JFXComboBox comboBox, String query, String tableColumn) {
        final ObservableList options = FXCollections.observableArrayList();
        Connection conn = DBConnection.Connect();
        ResultSet rs = null;

        conn = DBConnection.Connect();
        try {
            PreparedStatement fillStmt = conn.prepareStatement(query);
            rs = fillStmt.executeQuery();
            while (rs.next()) {
                // comboBox.getItems().addAll(rs.getString("specialty_name"));  
                options.addAll(rs.getString(tableColumn));
            }
            comboBox.getItems().addAll(options);

        } catch (SQLException ex) {
            System.out.println("ComboBox not filled");
        }

    }

   

}
