package com.pvanshah.sjsuquizapplication.student.util;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.pvanshah.sjsuquizapplication.student.base.App;

/**
 * @author Avinash
 */
public class Preferences {
    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL="user_email";
    private static Preferences pref;
    private SharedPreferences prefObj;
    private SharedPreferences prefObjforCount;

    private Editor prefsEditor;

    private Preferences(SharedPreferences prefObj) {
        //default constructor needed
        this.prefObj = prefObj;
        this.prefObjforCount = prefObj;
        prefsEditor = prefObj.edit();

    }

    public SharedPreferences getSharedPref() {
        if (prefObjforCount == null) {
            prefObjforCount = PreferenceManager.getDefaultSharedPreferences(App
                    .get());
        }
        return prefObjforCount;
    }


    /**
     * @return
     */
    public static synchronized Preferences getIns() {
        if (pref == null) {
            pref = new Preferences(PreferenceManager.getDefaultSharedPreferences(App.get()));
        }
        return pref;
    }

    public static void resetPrefs() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(App.get());
        Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    public void storeString(String key, String value) {
        prefsEditor.putString(key, value).commit();
    }

    public void storeBool(String key, boolean value) {
        prefsEditor.putBoolean(key, value).commit();
    }

    public boolean getBool(String key) {
        return prefObj.getBoolean(key, false);
    }

    public boolean isFirst(String key) {
        return prefObj.getBoolean(key, true);
    }

    public String getString(String key) {
        return prefObj.getString(key, "");
    }

}