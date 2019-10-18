package com.nano.Bush.model;

import java.util.Optional;

public class User {

    private final String username;
    private final String firstName;
    private final String lastName;
    private String password;
    private String email;
    private Integer companyId;
    private Optional<Integer> userId;


    public User(String username, String firstName, String lastName, String password, String email, Integer companyId, Optional<Integer> userId) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.companyId = companyId;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public String getEmail() {
        return email;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Optional<Integer> getUserId() {
        return userId;
    }
}
