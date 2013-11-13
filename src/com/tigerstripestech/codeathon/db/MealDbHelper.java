package com.tigerstripestech.codeathon.db;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tigerstripestech.codeathon.R;
import com.tigerstripestech.codeathon.objects.Food;

public class MealDbHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "MealDb.db";
	private static final int DATABASE_VERSION = 1;
	private Resources res;

	public MealDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		res = context.getResources();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		MealDb.onCreate(db);
		populateTestData(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		MealDb.onUpgrade(db, oldVersion, newVersion);
	}

	private void populateTestData(SQLiteDatabase db) {
		Log.d(MealDb.LOG_TAG, "Populating Data");
		
		ContentValues testData;
		String[] food_names = res.getStringArray(R.array.food_name_array);
		String[] food_type = res.getStringArray(R.array.food_type_array);
		String[] food_calorie = res.getStringArray(R.array.food_calorie_array);
		String[] intake_time = res.getStringArray(R.array.intake_time_array);
		String[] intake_food = res.getStringArray(R.array.intake_food_array);
		String[] intake_quantity = res.getStringArray(R.array.intake_quantity_array);
		
		for(int i = 0; i < food_names.length; i++) {
			testData = new ContentValues();
			testData.put(MealDb.KEY_FOOD_NAME, food_names[i]);
			testData.put(MealDb.KEY_FOOD_COUNT, food_type[i]);
			testData.put(MealDb.KEY_FOOD_CALORIE, food_calorie[i]);
			db.insert(MealDb.DB_FOOD, null, testData);
		}
		
		for(int i = 0; i < intake_time.length; i++) {
			testData = new ContentValues();
			testData.put(MealDb.KEY_INTAKE_DATE, Integer.parseInt(intake_time[i]));
			testData.put(MealDb.KEY_INTAKE_FOOD, Integer.parseInt(intake_food[i]));
			testData.put(MealDb.KEY_INTAKE_COUNT, Integer.parseInt(intake_quantity[i]));
			db.insert(MealDb.DB_INTAKE, null, testData);
		}
	}
	
	// returns a list of names from the people table
	public ArrayList<Food> getAllFood() {
		ArrayList<Food> foodArray = new ArrayList<Food>();

		// Select All Query
		String selectQuery = "SELECT  * FROM " + MealDb.DB_FOOD;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list

		if (cursor.moveToFirst()) {
			do {
				// cursor has values in the format = _id, pid, name, updated
				Food food = new Food();
				int id = cursor.getInt(cursor.getColumnIndex(MealDb.KEY_ID));
				String name = cursor.getString(cursor.getColumnIndex(MealDb.KEY_FOOD_NAME));
				int type = cursor.getInt(cursor.getColumnIndex(MealDb.KEY_FOOD_COUNT));
				int calories = cursor.getInt(cursor.getColumnIndex(MealDb.KEY_FOOD_CALORIE));
				food.set_id(id);
				food.setName(name);
				food.setType(type);
				food.setCalories(calories);
				foodArray.add(food);

			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();

		// returning people
		return foodArray;

	}
	
	public Cursor getIntakeRows(int timestamp) {
		
		int day = (timestamp / 86400) * 86400;
		int nextDay = ((timestamp / 86400) + 1) * 86400;
		String selectQuery = "SELECT _id, ((" + MealDb.KEY_INTAKE_DATE + " / 3600) * 3600) hr,"
				+ "sum(" + MealDb.KEY_INTAKE_COUNT + ") cnt, (sum(calories_per) * sum(" 
				+ MealDb.KEY_INTAKE_COUNT +  ")) total FROM (SELECT " + MealDb.DB_INTAKE + ".*, "
			    + MealDb.DB_FOOD + "." + MealDb.KEY_FOOD_CALORIE + " FROM " + MealDb.DB_INTAKE + " LEFT JOIN "
			    + MealDb.DB_FOOD + " ON " + MealDb.DB_INTAKE + "." + MealDb.KEY_INTAKE_FOOD + "="
			    + MealDb.DB_FOOD + "." + MealDb.KEY_ID + ") tbl WHERE hr > " + day + " AND hr < " + nextDay 
			    + " GROUP BY hr ORDER BY hr;";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		
		return cursor;
		
	}
	
	public Food getFoodFromString(String foodName) {
		Food food = new Food();

		// Select All Query
		String selectQuery = "SELECT * FROM " + MealDb.DB_FOOD
				+ " WHERE " + MealDb.KEY_FOOD_NAME + "=\"" + foodName + "\"";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list

		if (cursor.moveToFirst()) {
			do {
				// cursor has values in the format = _id, pid, name, updated
				String name = cursor.getString(cursor.getColumnIndex(MealDb.KEY_FOOD_NAME));
				int foodId = cursor.getInt(cursor.getColumnIndex(MealDb.KEY_ID));
				int type = cursor.getInt(cursor.getColumnIndex(MealDb.KEY_FOOD_COUNT));
				int calories = cursor.getInt(cursor.getColumnIndex(MealDb.KEY_FOOD_CALORIE));
				food.setName(name);
				food.setType(type);
				food.set_id(foodId);
				food.setCalories(calories);

			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();

		// returning people
		return food;
	}
	
	public int getCaloriesFromDay(int timestamp) {

		int day = (timestamp / 86400) * 86400;
		int nextDay = ((timestamp / 86400) + 1) * 86400;
		int calories = 0;
		
		// Select All Query
		String selectQuery = "SELECT " +  MealDb.DB_FOOD + "." + MealDb.KEY_FOOD_CALORIE + ","
				+ MealDb.DB_INTAKE + "." + MealDb.KEY_INTAKE_COUNT
				+ " FROM " + MealDb.DB_INTAKE
				+ " LEFT JOIN " + MealDb.DB_FOOD + " ON " + MealDb.DB_INTAKE + "." + MealDb.KEY_INTAKE_FOOD + "="
				+ MealDb.DB_FOOD + "." + MealDb.KEY_ID
			    + " WHERE " + MealDb.KEY_INTAKE_DATE + ">" + day + " AND " + MealDb.KEY_INTAKE_DATE
			    + " < " + nextDay;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list

		if (cursor.moveToFirst()) {
			do {

				int cals = cursor.getInt(cursor.getColumnIndex(MealDb.KEY_FOOD_CALORIE));
				int quantity = cursor.getInt(cursor.getColumnIndex(MealDb.KEY_INTAKE_COUNT));
				calories += cals * quantity;

			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();
		
		return calories;
	}
	
	public ArrayList<HashMap<String, String>> getFoodFromDay(int timestamp) {
		ArrayList<HashMap<String, String>> values = new ArrayList<HashMap<String, String>>();
		int day = (timestamp / 86400) * 86400;
		int nextDay = ((timestamp / 86400) + 1) * 86400;
		
		// Select All Query
		String selectQuery = "SELECT " +  MealDb.DB_FOOD + "." + MealDb.KEY_FOOD_CALORIE + " as calorie,"  
				+ MealDb.DB_FOOD + "." + MealDb.KEY_FOOD_NAME + " as name,"
				+ MealDb.DB_INTAKE + "." + MealDb.KEY_INTAKE_COUNT + " as count,"
				+ MealDb.DB_INTAKE + "." + MealDb.KEY_INTAKE_DATE + " as date"
				+ " FROM " + MealDb.DB_INTAKE
				+ " LEFT JOIN " + MealDb.DB_FOOD + " ON " + MealDb.DB_INTAKE + "." + MealDb.KEY_INTAKE_FOOD + "="
				+ MealDb.DB_FOOD + "." + MealDb.KEY_ID
			    + " WHERE " + MealDb.KEY_INTAKE_DATE + ">" + day + " AND " + MealDb.KEY_INTAKE_DATE
			    + " < " + nextDay;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list

		if (cursor.moveToFirst()) {
			do {
				HashMap<String, String> newValue = new HashMap<String,String>();
				int calories = cursor.getInt(cursor.getColumnIndex("calorie"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				int count = cursor.getInt(cursor.getColumnIndex("count"));
				int date = cursor.getInt(cursor.getColumnIndex("date"));
				newValue.put("calorie", Integer.toString(calories));
				newValue.put("date", Integer.toString(date));
				newValue.put("name", name);
				newValue.put("count", Integer.toString(count));
				values.add(newValue);
			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();
		
		return values;
	}
	
	public void saveNewIntake(int timestamp, String foodName, int quantity) {
		ContentValues testData = new ContentValues();
		Food food = getFoodFromString(foodName);
		testData.put(MealDb.KEY_INTAKE_DATE, timestamp);
		testData.put(MealDb.KEY_INTAKE_FOOD, food.get_id());
		testData.put(MealDb.KEY_INTAKE_COUNT, quantity);
		
		SQLiteDatabase db = this.getReadableDatabase();
		db.insert(MealDb.DB_INTAKE, null, testData);
	}
	
	public void saveNewFood(String foodName, String foodType, int calories) {
		ContentValues testData = new ContentValues();
		
		testData.put(MealDb.KEY_FOOD_NAME, foodName);
		testData.put(MealDb.KEY_FOOD_COUNT, Food.getTypeInt(foodType));
		testData.put(MealDb.KEY_FOOD_CALORIE, calories);
		
		SQLiteDatabase db = this.getReadableDatabase();
		db.insert(MealDb.DB_INTAKE, null, testData);
	}
}
