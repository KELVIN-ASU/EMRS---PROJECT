/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagementsystem;

import javafx.scene.image.ImageView;

/**
 *
 * @author Ekuri PC
 */
public class Pharmacist extends User {

    private int pharmacistID;
    private String pharmacistAddress;

    public Pharmacist(int pharmacistID, String pharmacistAddress, String userFirstName, String userGender, String userDateOfBirth, String userRegistrationDate, String userPhoneNumber, String StringUserName, String StringPassword) {
        super(userFirstName, userGender, userDateOfBirth, userRegistrationDate, userPhoneNumber, StringUserName, StringPassword);
        this.pharmacistID = pharmacistID;
        this.pharmacistAddress = pharmacistAddress;
    }

    public Pharmacist(int pharmacistID, String pharmacistAddress, String userFirstName, String userGender, String userDateOfBirth, String userRegistrationDate, String userPhoneNumber, ImageView userProfilepic, String StringUserName, String StringPassword) {
        super(userFirstName, userGender, userDateOfBirth, userRegistrationDate, userPhoneNumber, userProfilepic, StringUserName, StringPassword);
        this.pharmacistID = pharmacistID;
        this.pharmacistAddress = pharmacistAddress;
    }

    public int getPharmacistID() {
        return pharmacistID;
    }

    public void setPharmacistID(int pharmacistID) {
        this.pharmacistID = pharmacistID;
    }

    public String getPharmacistAddress() {
        return pharmacistAddress;
    }

    public void setPharmacistAddress(String pharmacistAddress) {
        this.pharmacistAddress = pharmacistAddress;
    }

}
