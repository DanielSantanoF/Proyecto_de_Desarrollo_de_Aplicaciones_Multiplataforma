package com.dsantano.proyectodam.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private Context ctx;
    private SharedPreferencesManager(Context ctx){}

    private static SharedPreferences getSharedPreferences(){
        return MyApp.getContext().getSharedPreferences(Constants.APP_SETTINGS_FILE, Context.MODE_PRIVATE);
    }

    public static void setStringValue(String dataLabel, String dataValue){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(dataLabel,dataValue);
        editor.commit();
    }

    public static String getStringValue(String dataLabel){
        return getSharedPreferences().getString(dataLabel,null);
    }
}