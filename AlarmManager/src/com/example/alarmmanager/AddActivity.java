package com.example.alarmmanager;

import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

@SuppressLint("NewApi") public class AddActivity extends Activity {

	EditText et_details, et_date, et_timEditText, et_title;
	TimePicker timePicker;
	DatePicker datePicker;
	DatabaseReminder databaseReminder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		
		MainActivity.ok=2;

		et_details = (EditText) findViewById(R.id.et_details_add);
		et_title = (EditText) findViewById(R.id.et_title_add);
		timePicker = (TimePicker) findViewById(R.id.timePicker1);
		datePicker = (DatePicker) findViewById(R.id.datePicker1);
		databaseReminder = new DatabaseReminder(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.add_event) {

			String title, details;
			title = et_title.getText().toString();
			details = et_details.getText().toString();

			int day, month, year, hour, minute;
			day = datePicker.getDayOfMonth();
			month = datePicker.getMonth();
			year = datePicker.getYear();

			String dateString = day + "/" + month + "/" + year + "";

			hour = timePicker.getCurrentHour();
			minute = timePicker.getCurrentMinute();

			String timeString = "";
			Calendar c = Calendar.getInstance();

			c.set(year, month, day, hour, minute);
			long tinmili = c.getTimeInMillis();

			Log.e("MY tag ", "caletnder time in mili : " + tinmili);

			boolean is24hour = timePicker.is24HourView();
			String apm = "";

			if (is24hour) {
				if (hour >= 12) {
					apm = "PM";

				} else {
					apm = "AM";
				}

				timeString = hour + " : " + minute + " " + apm;
			} else {
				timeString = hour + " : " + minute;
			}

			// Events events=new Events(1, tinmili, title, details, timeString,
			// dateString);
			Events events = new Events(1, 100, tinmili, title, details,
					timeString, dateString, null, null);
			long inserted = databaseReminder.insertEvent(events);

			if (inserted > 0) {
				Log.e("MY Tag", "Inserted :  " + events.toString());
			}

			Log.e("MY Tag", "Save key pressed ");

			Intent i = new Intent(AddActivity.this, MainActivity.class);
			this.startActivity(i);

		}

		if (item.getItemId() == R.id.message) {
			
			Intent j = new Intent(AddActivity.this, MessageActivity.class);
			this.startActivity(j);

		}

		

		return super.onOptionsItemSelected(item);
	}
	
	
	
	
	

}
