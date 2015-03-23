package com.example.alarmmanager;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.R.integer;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements
		List_Fragment.Communicator {

	// EditText title, description, time_et;
	DatabaseReminder databaseReminder;
	Context context = this;
	public static int ok;
	long finalTime;
	int requestcode;
	AlarmManager alarmManager;
	public static int flag_alarm;
	

	// Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ActionBar actionBar= getActionBar();
		//actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#9BDDFF")));

		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		databaseReminder = new DatabaseReminder(this);
		databaseReminder.ResetallEvent();
		ArrayList<Events> eventsarraArrayList = databaseReminder.getAllEvent();

		if (MainActivity.flag_alarm==1) {
			
			MediaPlayer mPlayer;
			mPlayer = MediaPlayer.create(context, R.raw.htc);
			mPlayer.start();
		}
		
		if (eventsarraArrayList.size() > 0) {

			Log.e("MYLOG ",
					"data in full arratlist:"
							+ eventsarraArrayList.toString());

			for (int i = 0; i < eventsarraArrayList.size(); i++) {

				Events events = eventsarraArrayList.get(i);

				if (events.getTrigger() == 1) {

					finalTime = events.getTime();
					requestcode = events.getId();
					Log.e("MYLOG ", "data in loop:" + events.toString());

					SetAlarm1(finalTime, requestcode,this);
					break;
				}

			}
		}

	}

	public void SetAlarm1(long time, int req_code,Context context) {

		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		Intent alarmIntent = new Intent(context, AlarmReceiver.class);

		Log.e("MY tag :", "in alarm Function :");

		am.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(context,
				req_code, alarmIntent, PendingIntent.FLAG_ONE_SHOT));

		

	}

	

	public void contactdetails(String title, String details, String date,
			String time, int trigger, int id1) {
		// TODO Auto-generated method stub

		Details_Fragment details_Fragment = (Details_Fragment) getFragmentManager()
				.findFragmentById(R.id.details_fragment);

		if (details_Fragment != null && details_Fragment.isInLayout()) {

			details_Fragment.set_details_weathertext(title, details, date,
					time, trigger, id1);
		} else {

			Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
			intent.putExtra("title", title);
			intent.putExtra("details", details);
			intent.putExtra("time", time);
			intent.putExtra("date", date);
			intent.putExtra("trigger", trigger);
			intent.putExtra("id", id1);

			startActivity(intent);

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.add_event) {

			Intent i = new Intent(this, AddActivity.class);
			this.startActivity(i);

		}

		return super.onOptionsItemSelected(item);
	}

}
