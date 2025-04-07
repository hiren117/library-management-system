package com.webapp.lms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // Giving a meaningful table name
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // Ensure unique usernames
    private String userName;

    @Column(nullable = false)
    private String password; // Later, we will encrypt this

    private String fullName;

    @Column(unique = true) // Ensuring unique mobile numbers
    private Long mobileNumber;

    @Column(unique = true) // Ensuring unique email IDs
    private String emailId;

    // Default constructor (needed by JPA)
    public User() {}

    // Parameterized constructor
    public User(String userName, String password, String fullName, Long mobileNumber, String emailId) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", mobileNumber=" + mobileNumber +
                ", emailId='" + emailId + '\'' +
                '}';
    }
}
