/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagementsystem;

import java.sql.Blob;
import javafx.scene.image.ImageView;

/**
 *
 * @author Ekuri PC
 */
public class Doctor extends User {

    private int doctorId;
    String doctorSpecialty;
    private String doctorAddress;

    public Doctor(String doctorSpecialty, String doctorAddress) {
        this.doctorSpecialty = doctorSpecialty;
        this.doctorAddress = doctorAddress;
    }

    public Doctor(int doctorId, String doctorSpecialty, String doctorAddress, String userFirstName, String userGender, String userDateOfBirth, String userRegistrationDate, String userPhoneNumber, String StringUserName, String StringPassword) {
        super(userFirstName, userGender, userDateOfBirth, userRegistrationDate, userPhoneNumber, StringUserName, StringPassword);
        this.doctorSpecialty = doctorSpecialty;
        this.doctorAddress = doctorAddress;
        this.doctorId = doctorId;
    }
    
   

    public Doctor(String doctorSpecialty, String doctorAddress, String userFirstName, String userGender, String userDateOfBirth, String userRegistrationDate, String userPhoneNumber, ImageView userProfilepic, String StringUserName, String StringPassword) {
        super(userFirstName, userGender, userDateOfBirth, userRegistrationDate, userPhoneNumber, userProfilepic, StringUserName, StringPassword);
        this.doctorSpecialty = doctorSpecialty;
        this.doctorAddress = doctorAddress;
    }

    public String getDoctorSpecialty() {
        return doctorSpecialty;
    }

    public void setDoctorSpecialty(String doctorSpecialty) {
        this.doctorSpecialty = doctorSpecialty;
    }

    public String getDoctorAddress() {
        return doctorAddress;
    }

    public void setDoctorAddress(String doctorAddress) {
        this.doctorAddress = doctorAddress;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

}
