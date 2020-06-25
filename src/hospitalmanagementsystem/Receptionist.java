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
public class Receptionist extends User {

    private String receptionistAddress;
    public int receptionistID;

    public Receptionist(String receptionistAddress, int receptionistID) {
        this.receptionistAddress = receptionistAddress;
        this.receptionistID = receptionistID;
    }

    public Receptionist(String receptionistAddress, int receptionistID, String userFirstName, String userGender, String userDateOfBirth, String userRegistrationDate, String userPhoneNumber, String StringUserName, String StringPassword) {
        super(userFirstName, userGender, userDateOfBirth, userRegistrationDate, userPhoneNumber, StringUserName, StringPassword);
        this.receptionistAddress = receptionistAddress;
        this.receptionistID = receptionistID;
    }

    public Receptionist(String receptionistAddress, int receptionistID, String userFirstName, String userGender, String userDateOfBirth, String userRegistrationDate, String userPhoneNumber, ImageView userProfilepic, String StringUserName, String StringPassword) {
        super(userFirstName, userGender, userDateOfBirth, userRegistrationDate, userPhoneNumber, userProfilepic, StringUserName, StringPassword);
        this.receptionistAddress = receptionistAddress;
        this.receptionistID = receptionistID;
    }
    
    

    public String getReceptionistAddress() {
        return receptionistAddress;
    }

    public void setReceptionistAddress(String receptionistAddress) {
        this.receptionistAddress = receptionistAddress;
    }

    public int getReceptionistID() {
        return receptionistID;
    }

    public void setReceptionistID(int receptionistID) {
        this.receptionistID = receptionistID;
    }

    

   

   
    

}
