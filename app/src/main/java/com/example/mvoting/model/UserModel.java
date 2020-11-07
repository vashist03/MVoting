package com.example.mvoting.model;

public class UserModel {
    private String id;
    private String fname;
    private String surname;
    private String address;
    private String nic;
    private String pin;
    private String phone;
    private String email;
    private String dob;
    private String vote;
    private boolean voted;
    private String uType;

    public UserModel() {}

    public UserModel(String id, String fname, String surname, String address, String nic, String pin, String phone, String email, String dob, String vote, boolean voted, String uType) {
        this.id = id;
        this.fname = fname;
        this.surname = surname;
        this.address = address;
        this.nic = nic;
        this.pin = pin;
        this.phone = phone;
        this.email = email;
        this.dob = dob;
        this.vote = vote;
        this.voted = voted;
        this.uType = uType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    public String getuType() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = uType;
    }
}
