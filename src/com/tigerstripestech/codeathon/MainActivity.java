package com.tigerstripestech.codeathon;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.tigerstripestech.codeathon.db.MealDbHelper;
import com.tigerstripestech.codeathon.objects.Food;

public class MainActivity extends Activity {

	private MealDbHelper dbHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dbHelper = App.getDbHelper();
		ArrayList<Food> foodArray = dbHelper.getAllFood();
		
		for(int i = 0; i < foodArray.size(); i++) {
			Food food = foodArray.get(i);
			Log.d("Codeathon Food Msg", food.getName());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
