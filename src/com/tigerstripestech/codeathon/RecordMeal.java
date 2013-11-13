package com.tigerstripestech.codeathon;

import java.util.ArrayList;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.tigerstripestech.codeathon.db.MealDbHelper;
import com.tigerstripestech.codeathon.objects.Food;

public class RecordMeal extends Activity {
	
	Spinner spinMeal;
	String curMeal;
	Button btnTime, btnDate;
	MealDbHelper dbHelper = App.getDbHelper();
	//private AppPreferences _appPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_meal);
		// Show the Up button in the action bar.
		setupActionBar();
		
		spinMeal = (Spinner) findViewById(R.id.spinMeal);
		btnTime = (Button) findViewById(R.id.btnTime);
		btnDate = (Button) findViewById(R.id.btnDate);
		
		//_appPrefs = new AppPreferences(getBaseContext());
		
		//Log.d("CODEATHON", _appPrefs.getHeight());
		//Log.d("CODEATHON", _appPrefs.getWeight());
		
		populateSpinner();
	}
	
	private void populateSpinner(){
		
		//ArrayList<String> meals = dbHelper.getAllFood();
		ArrayList<Food> allFood = dbHelper.getAllFood();
		ArrayList<String> meals = new ArrayList<String>();
		
		for(int i=0;i<allFood.size();i++){
			meals.add(allFood.get(i).getName());
		}
		
		ArrayAdapter<String> mealAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, meals);
		
		spinMeal.setAdapter(mealAdapter);
		
		spinMeal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				curMeal = (String) parent.getItemAtPosition(pos).toString();
				Food f = dbHelper.getFoodFromString(curMeal);
				String fType = f.getTypeString();
				TextView quantity = (TextView) findViewById(R.id.selectQuantityTextView);
				quantity.setText("Quantity (in " + fType + "):");
				
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}
	
	public void onClickSaveMeal(View v) {
		//Intent intent = new Intent(this, SaveMeal.class);
		//startActivity(intent);
		
		// TODO: Save meal information logic here
		finish();
	}
	
	public void onClickAddNew(View v) {
		Intent intent = new Intent(this, RecordMeal.class);
		startActivity(intent);
	}
	
	public void showTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getFragmentManager(), "timePicker");
	}
	
	public void onTimeSet(String time) {
		btnTime.setText(time);
	}
	
	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}
	
	public void onDateSet(String date) {
		btnDate.setText(date);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.record_meal, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
