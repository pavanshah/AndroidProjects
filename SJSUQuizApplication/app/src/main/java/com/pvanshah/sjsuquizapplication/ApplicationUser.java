package com.pvanshah.sjsuquizapplication;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Pavan Shah on 6/22/2017.
 */

@IgnoreExtraProperties
public class ApplicationUser {

    private String firstname;
    private String lastname;
    private String email;
    private String userId;
    private String role;

    public ApplicationUser() {

    }

    public ApplicationUser(String firstname, String lastname,  String email, String userId) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.userId = userId;
        this.role = "Student";
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }
}
