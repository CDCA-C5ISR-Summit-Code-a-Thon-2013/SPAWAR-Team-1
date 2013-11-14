package com.tigerstripestech.codeathon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

import com.tigerstripestech.codeathon.db.MealDbHelper;

public class IntakeDetails extends Activity {
	private MealDbHelper dbHelper;
	private TableLayout tl;
	private Button btnReturn;
	

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intake_details);

		btnReturn = (Button) findViewById(R.id.btnReviewReturn);
		
		btnReturn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		dbHelper = App.getDbHelper();
		int day, end;

		
		if (this.getIntent().getExtras() != null) {
			Bundle bundle = this.getIntent().getExtras();
			day = bundle.getInt("date");
			end = bundle.getInt("end");

		} else {
			throw new NullPointerException("argument is missing");
		}

		//ArrayList<HashMap<String, String>> foodArray = dbHelper.getFoodFromDay(day);
		ArrayList<HashMap<String, String>> foodArray = dbHelper.getFoodFromTime(day, end);
		tl = (TableLayout) findViewById(R.id.detailsTable);

		TextView title = (TextView) findViewById(R.id.details_date);
		TextView titleTime = (TextView) findViewById(R.id.details_time);
		Calendar cal = Calendar.getInstance();
		int ESTOffset = 3600 * 5;
		Long milli = (long) (day + ESTOffset) * 1000;
		cal.setTimeInMillis(milli);
		Calendar endCal = Calendar.getInstance();
		Long endMilli = (long) (end + ESTOffset) * 1000;
		endCal.setTimeInMillis(endMilli);

		SimpleDateFormat format = new SimpleDateFormat("EEE d MMM yyyy");
		SimpleDateFormat formatTime = new SimpleDateFormat("h a");
		String dateString = format.format(cal.getTime());
		String timeString = formatTime.format(cal.getTime()) + " - " + formatTime.format(endCal.getTime());
		title.setText(dateString);
		titleTime.setText(timeString);

		int calories = 0;
		int intake = 0;
		for (int i = 0; i < foodArray.size(); i++) {
			Map<String, String> value = foodArray.get(i);
			Log.d("Codeathon Food Msg", value.get("name"));

			LayoutInflater inflater = getLayoutInflater();
			TableRow tr = (TableRow) inflater.inflate(R.layout.table_row, tl, false);

			// Add First Column
			TextView detailTime = (TextView) tr.findViewById(R.id.detailRowTime);
			cal = Calendar.getInstance();
			milli = (long) (Long.parseLong(value.get("date")) + ESTOffset) * 1000;
			cal.setTimeInMillis(milli);
			format = new SimpleDateFormat("hh:mm a");
			timeString = format.format(cal.getTime());

			detailTime.setText(timeString);

			// Add Second Column
			TextView detailName = (TextView) tr.findViewById(R.id.detailRowIntake);
			detailName.setText(value.get("name"));
			
			// Add Third Column
			TextView detailCount = (TextView) tr.findViewById(R.id.detailRowCount);
			int count = Integer.parseInt(value.get("count"));
			detailCount.setText(value.get("count"));
			intake+=1;

			// Add Fourth Column
			TextView detailCalorie = (TextView) tr.findViewById(R.id.detailRowCalorie);
			int calorie = Integer.parseInt(value.get("calorie"));
			detailCalorie.setText(Integer.toString(calorie * count));
			calories+=calorie * count;

			/* Add row to TableLayout. */
			tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
		
		TextView calorieCount = (TextView) findViewById(R.id.detailsCalories);
		TextView intakeCount = (TextView) findViewById(R.id.detailsIntakeCount);
		calorieCount.setText(Integer.toString(calories));
		intakeCount.setText(Integer.toString(intake));
	}
}
