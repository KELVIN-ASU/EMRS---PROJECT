/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Ekuri PC
 */
public class DBConnection {
    
    public static Connection Connect() {
        
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalmanagementsystem?useTimezone=true&serverTimezone=UTC", "root", "");
            System.out.println("Connected");
        } catch (Exception ex) {
             JOptionPane.showMessageDialog(null, ex);
        }
        return conn;

    }
    
}
