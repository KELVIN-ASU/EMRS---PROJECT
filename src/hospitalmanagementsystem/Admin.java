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
public class Admin extends User {
    
    //the default constructor
    public Admin() {
    }

    public Admin(String userFirstName, String userGender, String userDateOfBirth, String userRegistrationDate, String userPhoneNumber, String StringUserName, String StringPassword) {
        super(userFirstName, userGender, userDateOfBirth, userRegistrationDate, userPhoneNumber, StringUserName, StringPassword);
    }

    public Admin(String userFirstName, String userGender, String userDateOfBirth, String userRegistrationDate, String userPhoneNumber, ImageView userProfilepic, String StringUserName, String StringPassword) {
        super(userFirstName, userGender, userDateOfBirth, userRegistrationDate, userPhoneNumber, userProfilepic, StringUserName, StringPassword);
    }

}
