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
public class User {

    private String userFirstName;
    private String userGender;
    private String userDateOfBirth;
    private String userRegistrationDate;
    private String userPhoneNumber;
    private ImageView userProfilepic;
    private String StringUserName;
    private String StringPassword;

    public User() {
        
    }

    public User(String userFirstName, String userGender, String userDateOfBirth, String userRegistrationDate, String userPhoneNumber, String StringUserName, String StringPassword) {
        this.userFirstName = userFirstName;
        this.userGender = userGender;
        this.userDateOfBirth = userDateOfBirth;
        this.userRegistrationDate = userRegistrationDate;
        this.userPhoneNumber = userPhoneNumber;
        this.StringUserName = StringUserName;
        this.StringPassword = StringPassword;
    }
    
    

    public User(String userFirstName, String userGender, String userDateOfBirth, String userRegistrationDate, String userPhoneNumber, ImageView userProfilepic, String StringUserName, String StringPassword) {
        this.userFirstName = userFirstName;
        this.userGender = userGender;
        this.userDateOfBirth = userDateOfBirth;
        this.userRegistrationDate = userRegistrationDate;
        this.userPhoneNumber = userPhoneNumber;
        this.userProfilepic =  userProfilepic;
        this.StringUserName = StringUserName;
        this.StringPassword = StringPassword;
    }
    
    
    

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserDateOfBirth() {
        return userDateOfBirth;
    }

    public void setUserDateOfBirth(String userDateOfBirth) {
        this.userDateOfBirth = userDateOfBirth;
    }

    public String getUserRegistrationDate() {
        return userRegistrationDate;
    }

    public void setUserRegistrationDate(String userRegistrationDate) {
        this.userRegistrationDate = userRegistrationDate;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public ImageView getUserProfilepic() {
        return userProfilepic;
    }

    public void setUserProfilepic(ImageView userProfilepic) {
        this.userProfilepic = userProfilepic;
    }

    public String getStringUserName() {
        return StringUserName;
    }

    public void setStringUserName(String StringUserName) {
        this.StringUserName = StringUserName;
    }

    public String getStringPassword() {
        return StringPassword;
    }

    public void setStringPassword(String StringPassword) {
        this.StringPassword = StringPassword;
    }

   

}
