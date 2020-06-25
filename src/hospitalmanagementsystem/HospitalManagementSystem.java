/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Ekuri PC
 */
public class HospitalManagementSystem extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        //Parent root = FXMLLoader.load(getClass().getResource("viewPatientsTable.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("adminDashBoard.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("loginLevel.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("manageDoctorSpecialty.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("pharmacistTableView.fxml"));
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(HospitalManagementSystem.class.getResourceAsStream("ICONS/hospital.png")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("ELECTRONIC HEALTH INFORMATION SYSTEM: BUEA HOSPITAL");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
