package com.tigerstripestech.codeathon.objects;

import com.tigerstripestech.codeathon.db.MealDb;

public class Food {

	private String name;
	private int type;
	private int calories;
	private int _id;
	
	// initialization of default values
	public Food() {
		setName("");
		setType(1);
		setCalories(0);
		set_id(1);
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
	
	public static String getTypeString(int type) {
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
	
	public static int getTypeInt(String type) {
		if(type.equalsIgnoreCase("quantity")) return MealDb.TYPE_QUANTITY;
		if(type.equalsIgnoreCase("cup")) return MealDb.TYPE_CUP;
		if(type.equalsIgnoreCase("oz")) return MealDb.TYPE_OZ;
		return 0;
	}
	
	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}
}
