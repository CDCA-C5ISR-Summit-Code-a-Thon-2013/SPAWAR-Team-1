package com.tigerstripestech.codeathon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

import com.tigerstripestech.codeathon.db.MealDbHelper;

public class IntakeDetails extends Activity {
	private MealDbHelper dbHelper;
	private TableLayout tl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intake_details);

		dbHelper = App.getDbHelper();

		int tmpTime = (int) (Calendar.getInstance().getTimeInMillis() / 1000);
		ArrayList<HashMap<String, String>> foodArray = dbHelper.getFoodFromDay(tmpTime);
		tl = (TableLayout) findViewById(R.id.detailsTable);

		TextView title = (TextView) findViewById(R.id.details_date);
		Calendar cal = Calendar.getInstance();
		Long milli = (long) (tmpTime * 1000);
		cal.setTimeInMillis(milli);
		SimpleDateFormat format = new SimpleDateFormat("EEE d MMM yyyy");
		String timeString = format.format(cal.getTime());
		title.setText(timeString);

		for (int i = 0; i < foodArray.size(); i++) {
			Map<String, String> value = foodArray.get(i);
			Log.d("Codeathon Food Msg", value.get("name"));

			LayoutInflater inflater = getLayoutInflater();
			TableRow tr = (TableRow) inflater.inflate(R.layout.table_row, tl, false);

			// Add First Column
			TextView detailTime = (TextView) tr.findViewById(R.id.detailRowTime);
			cal = Calendar.getInstance();
			milli = (long) (Long.parseLong(value.get("date"))) * 1000;
			cal.setTimeInMillis(milli);
			format = new SimpleDateFormat("h a");
			timeString = format.format(cal.getTime());

			detailTime.setText(timeString);

			// Add Second Column
			TextView detailName = (TextView) tr.findViewById(R.id.detailRowIntake);
			detailName.setText(value.get("name"));

			// Add Third Column
			TextView detailCalorie = (TextView) tr.findViewById(R.id.detailRowCalorie);
			detailCalorie.setText(value.get("calorie"));

			/* Add row to TableLayout. */
			tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
	}
}
