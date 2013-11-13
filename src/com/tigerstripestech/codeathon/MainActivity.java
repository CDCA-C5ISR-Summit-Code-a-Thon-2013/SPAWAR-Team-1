package com.tigerstripestech.codeathon;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

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
	
	public void onClickRecordMeal(View v) {
		Intent intent = new Intent(this, RecordMeal.class);
		startActivity(intent);
	}
	
	
	public void onClickCalendar(View v) {

		Intent intent = new Intent(this, CalendarActivity.class);

		startActivity(intent);
	}

	

	public void onClickProfile(View v) {
		Intent intent = new Intent(this, Profile.class);
		startActivity(intent);
	}
	
	public void onClickTest(View v) {

		Intent intent = new Intent(this, IntakeDetails.class);
		//Intent confirmRun = new Intent(getActivity(), RunInfo.class);
        
        Bundle extras = new Bundle();
        Calendar cal = Calendar.getInstance();
        int day = (int) (cal.getTimeInMillis() / 1000);
        extras.putInt("date", day);

        intent.putExtras(extras);
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
