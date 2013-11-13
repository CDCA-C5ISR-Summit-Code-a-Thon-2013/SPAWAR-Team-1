package com.tigerstripestech.codeathon;

import java.util.Calendar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;

public class CalendarActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalendarView myCal = new CalendarView(this);
        myCal.setOnDateChangeListener(new OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                    int dayOfMonth) {
                 Toast.makeText(getApplicationContext(), ""+dayOfMonth, 0).show();// TODO Auto-generated method stub

            }
        });
        setContentView(myCal);
    }
    
    
}