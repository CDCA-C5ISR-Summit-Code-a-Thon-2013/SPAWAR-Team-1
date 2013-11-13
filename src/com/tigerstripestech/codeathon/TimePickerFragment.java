package com.tigerstripestech.codeathon;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment implements
		TimePickerDialog.OnTimeSetListener {
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		
		// round scheduled time to nearest quarter hour
		int unroundedMinutes = c.get(Calendar.MINUTE);
		int mod = unroundedMinutes % 15;
		c.add(Calendar.MINUTE, mod < 8 ? -mod : (15-mod));
		
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute,
				DateFormat.is24HourFormat(getActivity()));
	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// Do something with the time chosen by the user
		RecordMeal callingActivity = (RecordMeal) getActivity();
		String time;
		String ampm = "AM";
		if(hourOfDay > 12) {
			hourOfDay -= 12;
			ampm = "PM";
			
		}
		time = String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute) + " " + ampm; 
		callingActivity.onTimeSet(time);
		
	}
}
