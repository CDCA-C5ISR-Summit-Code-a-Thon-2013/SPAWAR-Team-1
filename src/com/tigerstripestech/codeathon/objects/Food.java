package com.tigerstripestech.codeathon.objects;

import com.tigerstripestech.codeathon.db.MealDb;

public class Food {

	private String name;
	private int type;
	private int calories;
	
	// initialization of default values
	public Food() {
		setName("");
		setType(1);
		setCalories(0);
	}
	
	public Food(String name, int type, int calories) {
		this.name = name;
		this.type = type;
		this.calories = calories;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public String getTypeString(int type) {
		switch(type) {
		case MealDb.TYPE_QUANTITY: return "quantity"; 
		case MealDb.TYPE_CUP: return "cup";
		case MealDb.TYPE_OZ: return "oz";
		default: return "not found";
		}
	}

	public String getTypeString() {
		return getTypeString(this.type);
	}
	
	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}
}
