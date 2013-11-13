package com.tigerstripestech.codeathon;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.tigerstripestech.codeathon.db.MealDbHelper;

public class IntakeSelect extends Activity {
	private SimpleCursorAdapter dataAdapter;
	private ListView listView;
	private static final int REQUEST_CODE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		int day;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intake_select_activity);
		
		listView = (ListView) findViewById(R.id.intakeList);
		
		if (this.getIntent().getExtras() != null) {
			Bundle bundle = this.getIntent().getExtras();
			day = bundle.getInt("date");

		} else {
			throw new NullPointerException("argument is missing");
		}
		generateIntake(day);

	}
	
	@SuppressLint("SimpleDateFormat")
	private void generateIntake(int day){	
		
		MealDbHelper dbHelper = App.getDbHelper();
		String[] columns = new String[] {
				"cnt",
				"hr",
				"total"
		};
		
		int[] to = new int[] {
				R.id.intake_count,
				R.id.intake_date,
				R.id.calorie_count
		};
		
		dataAdapter = new SimpleCursorAdapter(this, R.layout.button_layout, dbHelper.getIntakeRows(day), columns, to, 0);
		SimpleCursorAdapter.ViewBinder binder = new SimpleCursorAdapter.ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
				
				int sTime = cursor.getInt(cursor.getColumnIndexOrThrow("hr"));
				TextView tv = (TextView) view;
				if(tv != null && tv.getId() == R.id.intake_date){
					Calendar cal = Calendar.getInstance();
					Long milli = (long) (sTime) * 1000;
					cal.setTimeInMillis(milli);
					SimpleDateFormat format = new SimpleDateFormat("h a");
					String curDay = format.format(cal.getTime());
					
					cal.setTimeInMillis(milli + (3600 * 1000));
					String nextDay = format.format(cal.getTime());
					tv.setText(curDay + " - " + nextDay);
					return true;
				}
				
				return false;
			}
		};
		
		dataAdapter.setViewBinder(binder);
		listView.setAdapter(dataAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> listView, View view,
					int position, long id) {
				// Get the cursor, positioned to the corresponding row in the result set
				Cursor cursor = (Cursor) listView.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), IntakeDetails.class);
                int sTime = cursor.getInt(cursor.getColumnIndexOrThrow("hr"));
                
                int date = (sTime / 86400) * 86400;
                Bundle extras = new Bundle();
                extras.putInt("date", date);

                intent.putExtras(extras);
                startActivity(intent);
                finish();
				
			}
		});
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		
	}

	

}
