package com.ahamed.dpichat.Model;

public class ProfileModel {
    private String department;
    private String email;
    private String id;
    private String name;
    private String password;
    private String phone;
    private String registration;
    private String roll;

    public ProfileModel(String department, String email, String id, String name, String password, String phone, String registration, String roll) {
        this.department = department;
        this.email = email;
        this.id = id;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.registration = registration;
        this.roll = roll;
    }

    public ProfileModel() {
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }
}
