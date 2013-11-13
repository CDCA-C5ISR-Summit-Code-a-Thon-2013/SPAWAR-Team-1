package com.tigerstripestech.codeathon;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.tigerstripestech.codeathon.db.MealDbHelper;

public class App extends Application {

	private static MealDbHelper dbHelper;

	public static MealDbHelper getDbHelper() {
		return dbHelper;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		dbHelper = new MealDbHelper(getApplicationContext());
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.close();
	}

	
}
