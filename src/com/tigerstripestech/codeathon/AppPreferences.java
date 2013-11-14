package com.tigerstripestech.codeathon;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class AppPreferences {
    public static final String KEY_PREFS_HEIGHT = "height";
    public static final String KEY_PREFS_WEIGHT = "weight";
    public static final String KEY_PREFS_GOAL = "caloricGoal";
    private static final String APP_SHARED_PREFS = AppPreferences.class.getSimpleName(); //  Name of the file -.xml
    private SharedPreferences _sharedPrefs;
    private Editor _prefsEditor;

    public AppPreferences(Context context) {
        this._sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this._prefsEditor = _sharedPrefs.edit();
    }

    public String getHeight() {
    	return _sharedPrefs.getString(KEY_PREFS_HEIGHT, "");
    }
    
    public String getWeight() {
    	return _sharedPrefs.getString(KEY_PREFS_WEIGHT, "");
    }
    
    public String getCaloricGoal() {
    	return _sharedPrefs.getString(KEY_PREFS_GOAL, "2000");
    }

    public void saveHeight(String text) {
        _prefsEditor.putString(KEY_PREFS_HEIGHT, text);
        _prefsEditor.commit();
    }
    
    public void saveWeight(String text) {
        _prefsEditor.putString(KEY_PREFS_WEIGHT, text);
        _prefsEditor.commit();
    }

	public void saveGoal(String caloricGoal) {
		_prefsEditor.putString(KEY_PREFS_GOAL, caloricGoal);
        _prefsEditor.commit();
		
	}
}