package com.tigerstripestech.codeathon.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MealDb {

	public static final String KEY_ID = "_id";
	
	public static final String DB_FOOD = "Food";
	public static final String KEY_FOOD_NAME = "food_name";
	public static final String KEY_FOOD_COUNT = "quantity_type";
	public static final String KEY_FOOD_CALORIE = "calories_per";
	
	public static final int TYPE_QUANTITY = 1;
	public static final int TYPE_CUP = 2;
	public static final int TYPE_OZ = 3;
	
	public static final String DB_INTAKE = "Intake";
	public static final String KEY_INTAKE_DATE = "date";
	public static final String KEY_INTAKE_FOOD = "food_id";
	public static final String KEY_INTAKE_COUNT = "food_quantity";
	
	public static final String LOG_TAG = "Codeathon DB Mesg";


	public static void onCreate(SQLiteDatabase db) {
		createFoodTable(db);
		createIntakeTable(db);

	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion,
			int newVersion) {
		Log.d(LOG_TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + DB_FOOD);
		db.execSQL("DROP TABLE IF EXISTS " + DB_INTAKE);
		onCreate(db);
	}
	
	private static void createFoodTable(SQLiteDatabase db) {
		String createTable = "CREATE TABLE IF NOT EXISTS "
				+ DB_FOOD + " (" + KEY_ID + " INTEGER PRIMARY KEY, "
				+ KEY_FOOD_NAME + " TEXT , "
				+ KEY_FOOD_COUNT + " INTEGER , "
				+ KEY_FOOD_CALORIE + " INTEGER DEFAULT 0)";
		Log.d(LOG_TAG, createTable);
		db.execSQL(createTable);
	}

	private static void createIntakeTable(SQLiteDatabase db) {
		String createTable = "CREATE TABLE IF NOT EXISTS "
				+ DB_INTAKE + " (" + KEY_ID + " INTEGER PRIMARY KEY, "
				+ KEY_INTAKE_DATE+ " INTEGER , "
				+ KEY_INTAKE_FOOD + " INTEGER , "
				+ KEY_INTAKE_COUNT + " INTEGER)";
		Log.d(LOG_TAG, createTable);
		db.execSQL(createTable);
	}

}
