package com.pvanshah.sjsuquizapplication;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pavan Shah on 7/31/2017.
 */

public class StudentDetails {

    private String username;

    private String email;

    public StudentDetails(){

    }

    public StudentDetails(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
