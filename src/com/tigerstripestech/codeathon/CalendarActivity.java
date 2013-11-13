package com.tigerstripestech.codeathon;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarActivity extends Activity {
	
	private PopupWindow mpopup;
	SimpleDateFormat format = new SimpleDateFormat(
			"EEE, d MMM yyyy");

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        CalendarView myCal = (CalendarView) findViewById(R.id.cal);
        
        myCal.setOnDateChangeListener(new OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                    int dayOfMonth) {
            	
            	TextView focusTvDate = (TextView) findViewById(R.id.focusDate);
            	TextView focusTvCalories = (TextView) findViewById(R.id.focusCalories);
            	
            	//Format the date for display
            	GregorianCalendar date = new GregorianCalendar(year, month, dayOfMonth);
            	format.setCalendar(date);
            	focusTvDate.setText(format.format(date.getTime()));
            	
            	//Get the date to send as a parameter
            	final int finalDate = (int)date.getTimeInMillis()/1000;
            	
            	focusTvCalories.setText(getCalories(year, month, dayOfMonth));
            	
                Button btnDetails = (Button) findViewById(R.id.focusDetailsBtn);
                btnDetails.setOnClickListener(new OnClickListener() 
                {                    
                    @Override
                    public void onClick(View v) 
                    {
                    	System.out.println(finalDate);
                    	
                    	Intent intent = new Intent(CalendarActivity.this, IntakeSelect.class);
                   
                   		Bundle extras = new Bundle();
                   		extras.putInt("date", finalDate);
                   		

                   		intent.putExtras(extras);
                   		startActivity(intent);
                    	
                    }
                });
                
            
                 

            }
        });
        setupActionBar();
    }
    
    /**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	
    private String getCalories(int year, int month,
            int dayOfMonth){
    	return "Custom Text!!";
    }
}