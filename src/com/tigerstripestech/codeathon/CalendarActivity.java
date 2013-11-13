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
            	View popUpView = getLayoutInflater().inflate(R.layout.popup, null); // inflating popup layout
            	TextView popUpTvDate = (TextView) popUpView.findViewById(R.id.popUpDate);
            	TextView popUpTvCalories = (TextView) popUpView.findViewById(R.id.popUpCalories);
            	
            	//Format the date for display
            	GregorianCalendar date = new GregorianCalendar(year, month, dayOfMonth);
            	format.setCalendar(date);
            	popUpTvDate.setText(format.format(date.getTime()));
            	
            	//Get the date to send as a parameter
            	final int finalDate = (int)date.getTimeInMillis()/1000;
            	
            	popUpTvCalories.setText(getCalories(year, month, dayOfMonth));
            	
                mpopup = new PopupWindow(popUpView, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, true); //Creation of popup
                mpopup.setAnimationStyle(android.R.style.Animation_Dialog);   
                mpopup.showAtLocation(popUpView, Gravity.CENTER, 0, 0);    // Displaying popup
                
                Button btnDetails = (Button)popUpView.findViewById(R.id.btnDetails);
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
                    	
                        mpopup.dismiss();  //dismissing the popup
                    }
                });
                
                Button btnCancel = (Button)popUpView.findViewById(R.id.btnCancel);
                btnCancel.setOnClickListener(new OnClickListener() 
                {                    
                    @Override
                    public void onClick(View v) 
                    {
                        mpopup.dismiss(); //dismissing the popup
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