package com.example.alarmmanager;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class DetailsActivity extends Activity {

	TextView tv_title, tv_details, tv_date, tv_time;
	String title,details,time,date;
	int id2,trigger2;

	DatabaseReminder db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_details = (TextView) findViewById(R.id.tv_details);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_date = (TextView) findViewById(R.id.tv_date);
		
		title=getIntent().getStringExtra("title");
		details=getIntent().getStringExtra("details");
		time=getIntent().getStringExtra("time");
		date=getIntent().getStringExtra("date");
		String idsString=getIntent().getStringExtra("id");
		String TriggerString=getIntent().getStringExtra("trigger");
		
		//id2=Integer.parseInt(idsString);
		//trigger2=Integer.parseInt(TriggerString);
		
		set_details_weathertext( title, details,
				date, time);
		
		
	}

	public void set_details_weathertext(String title, String details,
			String date, String time) {

		tv_title.setText(title);
		tv_details.setText(details);
		tv_time.setText(time);
		tv_date.setText(date);
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}

}
