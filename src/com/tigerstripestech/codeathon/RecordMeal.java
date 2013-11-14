package com.tigerstripestech.codeathon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tigerstripestech.codeathon.db.MealDbHelper;
import com.tigerstripestech.codeathon.objects.Food;

public class RecordMeal extends Activity {
	
	Spinner spinMeal, spinType;
	String curMeal, curType;
	Button btnTime, btnDate;
	EditText calories, foodName;
	MealDbHelper dbHelper = App.getDbHelper();
	//private AppPreferences _appPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_meal);
		// Show the Up button in the action bar.
		setupActionBar();
		
		spinMeal = (Spinner) findViewById(R.id.spinMeal);
		spinType = (Spinner) findViewById(R.id.spinType);
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
		
		
		meals.add("-- Select Food --");
		
		for(int i=0;i<allFood.size();i++){
			meals.add(allFood.get(i).getName());
		}
		
		// Add a final option to have a custom food type created
		meals.add("Other");
		
		ArrayAdapter<String> mealAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, meals);
		
		spinMeal.setAdapter(mealAdapter);
		
		spinMeal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				curMeal = (String) parent.getItemAtPosition(pos).toString();
				if(curMeal.equalsIgnoreCase("Other")){
					// TODO: Pop up dialog
				}
				else{
					Food f = dbHelper.getFoodFromString(curMeal);
					String fType = f.getTypeString();
					TextView quantity = (TextView) findViewById(R.id.selectQuantityTextView);
					quantity.setText("Quantity (in " + fType + "):");
				}
				
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		
	}
	/*
	private void populateOtherSpinner(){
		//ArrayList<String> meals = dbHelper.getAllFood();
		ArrayList<String> allTypes = dbHelper.getAllTypes();
		ArrayList<String> types = new ArrayList<String>();
		
		for(int i=0;i<allTypes.size();i++){
			types.add(allTypes.get(i));
		}
		
		ArrayAdapter<String> typesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, types);
		
		spinType.setAdapter(typesAdapter);
		
		spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				tmp = (String) parent.getItemAtPosition(pos).toString();
				Log.d("codeathon", "Tmp is: " + tmp);
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		
	}
	*/
	
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public void onClickSaveMeal(View v) {
		if(curMeal.equalsIgnoreCase("-- Select Food --")){
			String title, message;
			title = "Food Bytes Error";
			message = "Please select a food";
			AlertDialog.Builder builder = confirmDialog(title, message);
			builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				}
				});
			builder.setCancelable(false);
			AlertDialog dialog = builder.create();
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
		}
		else if(curMeal.equalsIgnoreCase("Other")){
			//populateOtherSpinner();
			View otherView;
			
			LayoutInflater inflater = this.getLayoutInflater();	
			otherView = inflater.inflate(R.layout.other_dialog, null);
			foodName = (EditText) otherView.findViewById(R.id.foodNameEditText);
			calories = (EditText) otherView.findViewById(R.id.caloriesEditText);
			final Spinner sp = (Spinner) otherView.findViewById(R.id.spinType);
			sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
					curType = (String) parent.getItemAtPosition(pos).toString();
				}
				@Override
				public void onNothingSelected(AdapterView<?> parent) {
				}
			});
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setView(otherView);
			builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dbHelper.saveNewFood(foodName.getText().toString(), curType, Integer.parseInt(calories.getText().toString()));
					populateSpinner();
				}
				});
			builder.setNegativeButton(android.R.string.cancel, null);
			builder.setCancelable(false);
			AlertDialog dialog = builder.create();
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
			
			Button b = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
			if(b!=null)
				b.setBackground(getResources().getDrawable(R.drawable.app_bg));
			b = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
			if(b!=null)
				b.setBackground(getResources().getDrawable(R.drawable.app_bg));
		}
		else{
		
			// Calculate the timestamp of the recorded intake
			Button timeView = (Button) findViewById(R.id.btnTime);
			Button dateView = (Button) findViewById(R.id.btnDate);
			EditText quantityView = (EditText) findViewById(R.id.editQuantity);
			int quantity = Integer.parseInt(quantityView.getText().toString());
			String time = timeView.getText().toString();
			String date = dateView.getText().toString();
			int timeStamp = getTimeStamp(time,date);
			
			// TODO: Add logic to save the current values before resetting the activity
			dbHelper.saveNewIntake(timeStamp, curMeal, quantity);
			Log.d("Codeathon", "Saved Intake with timestamp=" + timeStamp + " foodName=" + curMeal + " quantity=" + quantity);
			Toast.makeText(getApplicationContext(), "Saved Intake", Toast.LENGTH_LONG).show();
			finish();
		}
	}
	
	public int getTimeStamp(String time, String date){
		int ts = 0;
		Date myDate = new Date();
		Calendar c = Calendar.getInstance();
		
		// January is month 0 in java date class. add 1 to c.get month
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String input = date + " " + time;
		try {
			myDate = dateFormater.parse(input);
			ts = (int) (myDate.getTime() / 1000);
			//stringTime = String.valueOf(myTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ts;
	}
	
	public void onClickAddNew(View v) {
		//TODO: Add logic to save the current values before resetting the activity
		Button timeView = (Button) findViewById(R.id.btnTime);
		Button dateView = (Button) findViewById(R.id.btnDate);
		EditText quantityView = (EditText) findViewById(R.id.editQuantity);
		int quantity = Integer.parseInt(quantityView.getText().toString());
		String time = timeView.getText().toString();
		String date = dateView.getText().toString();
		int timeStamp = getTimeStamp(time,date);
		
		// TODO: Add logic to save the current values before resetting the activity
		dbHelper.saveNewIntake(timeStamp, curMeal, quantity);
		Log.d("Codeathon", "Saved Intake with timestamp=" + timeStamp + " foodName=" + curMeal + " quantity=" + quantity);
		Toast.makeText(getApplicationContext(), "Saved Intake", Toast.LENGTH_LONG).show();
		
		Intent intent = new Intent(this, RecordMeal.class);
		startActivity(intent);
		finish();
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
	
	private AlertDialog.Builder confirmDialog(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setIcon(android.R.drawable.ic_dialog_alert);

		builder.setCancelable(false);
		return builder;
		
	}

}
