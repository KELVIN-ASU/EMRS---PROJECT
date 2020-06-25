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
public class Biologist extends User {

    public Biologist(String biologistAddress, int biologistID) {
        this.biologistAddress = biologistAddress;
        this.biologistID = biologistID;
    }

    public Biologist(String biologistAddress, int biologistID, String userFirstName, String userGender, String userDateOfBirth, String userRegistrationDate, String userPhoneNumber, String StringUserName, String StringPassword) {
        super(userFirstName, userGender, userDateOfBirth, userRegistrationDate, userPhoneNumber, StringUserName, StringPassword);
        this.biologistAddress = biologistAddress;
        this.biologistID = biologistID;
    }

    public Biologist(String biologistAddress, int biologistID, String userFirstName, String userGender, String userDateOfBirth, String userRegistrationDate, String userPhoneNumber, ImageView userProfilepic, String StringUserName, String StringPassword) {
        super(userFirstName, userGender, userDateOfBirth, userRegistrationDate, userPhoneNumber, userProfilepic, StringUserName, StringPassword);
        this.biologistAddress = biologistAddress;
        this.biologistID = biologistID;
    }
    
    

    private String biologistAddress;
    private int biologistID;

    public String getBiologistAddress() {
        return biologistAddress;
    }

    public void setBiologistAddress(String biologistAddress) {
        this.biologistAddress = biologistAddress;
    }

    public int getBiologistID() {
        return biologistID;
    }

    public void setBiologistID(int biologistID) {
        this.biologistID = biologistID;
    }

   
}
