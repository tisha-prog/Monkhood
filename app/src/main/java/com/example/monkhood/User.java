package com.example.monkhood;

public class User {
    private String name;
    private String email;
    private String phoneNumber;


    // An empty constructor (required for Firebase)
    public User() {}

    public User(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
