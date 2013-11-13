package com.tigerstripestech.codeathon;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RecordMeal extends Activity {
	
	Spinner spinMeal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_meal);
		// Show the Up button in the action bar.
		setupActionBar();
		
		spinMeal = (Spinner) findViewById(R.id.spinMeal);
		
		populateSpinner();
	}
	
	private void populateSpinner(){
		
		//List<String> meals = dbHelper.getAllMeals();
		String[] meals = getResources().getStringArray(R.array.meals);
		ArrayAdapter<String> mealAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, meals);
		
		spinMeal.setAdapter(mealAdapter);
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
