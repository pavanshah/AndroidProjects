package com.cmpe277labs.amipa.facebookclone;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Pavan Shah on 5/20/2017.
 */

public class SessionManager{

    private SharedPreferences prefs;

    public SessionManager(Context cntx) {
        Log.d("Session", "inside constructor");
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setEmail(String email) {
        prefs.edit().putString("email", email).apply();
    }

    public void setPassword(String password) {
        prefs.edit().putString("password", password).apply();
    }

    public String getEmail() {
        String email = prefs.getString("email","");
        return email;
    }

    public String getPassword() {
        String password = prefs.getString("password","");
        return password;
    }
    public void endSession(){
        prefs.edit().clear();
        prefs.edit().commit();
    }

    public void setScreenname(String screenname) {
        prefs.edit().putString("screenname", screenname).apply();
    }

    public void setFirstname(String firstname) {
        prefs.edit().putString("firstname", firstname).apply();
    }

    public void setLastname(String lastname) {
        prefs.edit().putString("lastname", lastname).apply();
    }

    public String getScreenname() {
        String screenname = prefs.getString("screenname","");
        return screenname;
    }

    public String getFirstname() {
        String firstname = prefs.getString("firstname","");
        return firstname;
    }

    public String getLastname() {
        String lastname = prefs.getString("lastname","");
        return lastname;
    }



}