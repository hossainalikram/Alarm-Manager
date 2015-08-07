package com.example.alarmmanager;

import java.util.Calendar;
import java.util.concurrent.Callable;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;

import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class MessageActivity extends Activity {

	TimePicker timePicker;
	DatePicker datePicker;
	DatabaseReminder databaseReminder;

	EditText et_to, et_body;
	static final int PICK_CONTACT = 1;

	String tonumberString;
	String tonamestring;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);

		et_to = (EditText) findViewById(R.id.et_phone_number);
		et_body = (EditText) findViewById(R.id.et_message_body);
		timePicker = (TimePicker) findViewById(R.id.timePicker1_message);
		datePicker = (DatePicker) findViewById(R.id.datePicker1_message);
		databaseReminder = new DatabaseReminder(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message, menu);
		return true;
	}

	public void contact(View v) {

		callcontact();
	}

	private void callcontact() {

		Intent intent = new Intent(Intent.ACTION_PICK,
				ContactsContract.Contacts.CONTENT_URI);

		startActivityForResult(intent, PICK_CONTACT);

	}

	protected void onActivityResult(int reqCode, int resultCode, Intent data) {

		Log.d("MY Tag : ", "num: " + tonumberString + "name : " + tonamestring);

		super.onActivityResult(reqCode, resultCode, data);

		switch (reqCode) {
		case (PICK_CONTACT):
			if (resultCode == Activity.RESULT_OK) {
				Uri contactData = data.getData();
				Cursor c = managedQuery(contactData, null, null, null, null);
				if (c.moveToFirst()) {
					String id = c
							.getString(c
									.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

					String hasPhone = c
							.getString(c
									.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

					if (hasPhone.equalsIgnoreCase("1")) {
						Cursor phones = getContentResolver()
								.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
										null,
										ContactsContract.CommonDataKinds.Phone.CONTACT_ID
												+ " = " + id, null, null);
						phones.moveToFirst();
						String phn_no = phones.getString(phones
								.getColumnIndex("data1"));
						String name = c.getString(c
								.getColumnIndex(StructuredPostal.DISPLAY_NAME));
						tonamestring = name;
						tonumberString = phn_no;

						// Toast.makeText(this, "contact info : "+
						// tonumberString+"\n"+tonamestring,
						// Toast.LENGTH_LONG).show();

					}
				}
			}
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		if(item.getItemId()==android.R.id.home){
			 NavUtils.navigateUpFromSameTask(this);        
			 return true;
			
		}

		if (item.getItemId() == R.id.save_message) {

			Log.e("my log : ", "in save message clvkred");

			String to, body;
			body = et_body.getText().toString();
			to = et_to.getText().toString();

			if (tonumberString != null) {
				to = tonumberString;
				et_to.setText(tonumberString + "/n" + tonamestring);
			}

			int day, month, year, hour, minute;
			day = datePicker.getDayOfMonth();
			month = datePicker.getMonth();
			year = datePicker.getYear();

			String dateString = day + "/" + (month + 1) + "/" + year + "";

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

			
			Events events;
			
			if (tonumberString != null) {
				events = new Events(1, 101, tinmili, "Message To \n   "
						+ tonamestring, body, timeString, dateString, null, to);
			} else {
				events = new Events(1, 101, tinmili, "Message To \n   " + to,
						body, timeString, dateString, null, to);
			}

			long inserted = databaseReminder.insertEvent(events);

			if (inserted > 0) {
				Log.e("MY Tag", "Inserted :  " + events.toString());
			}

			Log.e("MY Tag", "Save key pressed ");

			Intent k = new Intent(MessageActivity.this, MainActivity.class);
			startActivity(k);

		}
		return super.onOptionsItemSelected(item);
	}

}
