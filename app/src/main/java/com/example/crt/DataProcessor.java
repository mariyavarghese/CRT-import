package com.example.crt;

import android.content.Context;
import android.content.SharedPreferences;

public class DataProcessor {

    private static Context context;
    //private static SharedPreferences sharedPref;
    public final static String PREFS_NAME = "ge_prefs";
    //private static SharedPreferences.Editor editor;

    public DataProcessor(Context context) {
        this.context = context;
    }


    public static void setLogStatus(String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getLogStatus(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        // return prefs.getString("TOKEN", "DNF");
        return prefs.getString("LOG_STATUS", null);
    }


    public static void setUserId(String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getUserId(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        // return prefs.getString("TOKEN", "DNF");
        return prefs.getString(key, null);
    }



    public static void setMobile(String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static String getMobile(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        // return prefs.getString("TOKEN", "DNF");
        return prefs.getString(key, null);
    }






    public static void setIdType(String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public static String getIdType(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        // return prefs.getString("TOKEN", "DNF");
        return prefs.getString(key, null);
    }


    public static void clear() {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        editor.commit();
    }

    public void setEventType(String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }



    public static String getEventType(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        // return prefs.getString("TOKEN", "DNF");
        return prefs.getString(key, null);
    }


    public static String getCompanyId(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        // return prefs.getString("TOKEN", "DNF");
        return prefs.getString(key, null);
    }
    public void setUserName(String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static String getUserName(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        // return prefs.getString("TOKEN", "DNF");
        return prefs.getString(key, null);
    }

    public void setUserPassword(String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static String getUserPassword(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        // return prefs.getString("TOKEN", "DNF");
        return prefs.getString(key, null);
    }



    public void setUserEmail(String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static String getUserEmail(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        // return prefs.getString("TOKEN", "DNF");
        return prefs.getString(key, null);
    }
    public void setUserStatus(String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static String getUserStatus(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        // return prefs.getString("TOKEN", "DNF");
        return prefs.getString(key, null);
    }

    public void setCompanyidno(String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static String getCompanyidno(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        // return prefs.getString("TOKEN", "DNF");
        return prefs.getString(key, null);
    }



}
