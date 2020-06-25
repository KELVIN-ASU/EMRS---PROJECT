/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagementsystem;

/**
 *
 * @author Ekuri PC
 */
public class Patient {

    private String ssn;
    private String name;
    private String gender;
    private String dateOfBirth;
    private String address;
    private String height;
    private String weight;
    private String phoneNumber;
    private String bloodGroup;
    private String assignedDoctor;
    private String regDate;

    public Patient(String ssn, String name, String gender, String dateOfBirth, String address, String height, String weight, String phoneNumber, String bloodGroup, String assignedDoctor, String regDate) {
        this.ssn = ssn;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.height = height;
        this.weight = weight;
        this.phoneNumber = phoneNumber;
        this.bloodGroup = bloodGroup;
        this.assignedDoctor = assignedDoctor;
        this.regDate = regDate;
    }

    public Patient(String ssn, String name, String gender, String regDate) {
        this.ssn = ssn;
        this.name = name;
        this.gender = gender;
        this.regDate = regDate;
    }

    public Patient(String ssn, String name, String gender, String height, String weight, String phoneNumber, String bloodGroup, String assignedDoctor) {
        this.ssn = ssn;
        this.name = name;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.phoneNumber = phoneNumber;
        this.bloodGroup = bloodGroup;
        this.assignedDoctor = assignedDoctor;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(String assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

}
