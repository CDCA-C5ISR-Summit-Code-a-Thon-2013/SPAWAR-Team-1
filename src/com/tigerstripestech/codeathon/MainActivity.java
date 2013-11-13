package com.tigerstripestech.codeathon;

import com.tigerstripestech.codeathon.RecordMeal;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void onClickRecordMeal(View v) {
		Intent intent = new Intent(this, RecordMeal.class);
		startActivity(intent);
	}
	/*
	public void onClickCalendar(View v) {
		Intent intent = new Intent(this, Calendar.class);
		startActivity(intent);
	}
	
	public void onClickProfile(View v) {
		Intent intent = new Intent(this, Profile.class);
		startActivity(intent);
	}
	*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
