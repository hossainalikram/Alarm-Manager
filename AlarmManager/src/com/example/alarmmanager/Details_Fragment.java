package com.example.alarmmanager;

import java.io.ByteArrayInputStream;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Details_Fragment extends Fragment {

	TextView tv_title, tv_details, tv_date, tv_time, sw;

	private static String key_title = "null";
	private static String key_details = "null";
	private static String key_time = "null";
	private static String key_date = "null";

	private static String key = "t";

	private static Events ev;

	private static String key_ti;
	private static String key_de;
	private static String key_tim;
	private static String key_dat;

	DatabaseReminder db;
	Events events;

	// ToggleButton toggleButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.details_fragment, container,
				false);

		tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_details = (TextView) view.findViewById(R.id.tv_details);
		tv_time = (TextView) view.findViewById(R.id.tv_time);
		tv_date = (TextView) view.findViewById(R.id.tv_date);
		// sw = (TextView) view.findViewById(R.id.switch1);

		if (savedInstanceState != null) {

			/*
			 * Details_Fragment.key_dat = savedInstanceState
			 * .getString(Details_Fragment.key_date); Details_Fragment.key_tim =
			 * savedInstanceState .getString(Details_Fragment.key_time);
			 */
			/*Details_Fragment.key_de = savedInstanceState
					.getString(Details_Fragment.key_details);
			Details_Fragment.key_ti = savedInstanceState
					.getString(Details_Fragment.key_details);*/

			String[] strings = savedInstanceState.getStringArray(key);

			Details_Fragment.key_dat = strings[0];
			Details_Fragment.key_tim = strings[1];
			Details_Fragment.key_de=strings[2];
			Details_Fragment.key_ti=strings[3];

			set_details_weathertext(Details_Fragment.key_ti,
					Details_Fragment.key_de, Details_Fragment.key_dat,
					Details_Fragment.key_tim, 0, 100);

			Log.e("My tag : ", "date : in det" + Details_Fragment.key_dat);
			Log.e("My tag : ", "time :  in details" + Details_Fragment.key_tim);
			Log.e("My tag : ", "details : in" + Details_Fragment.key_de);

			// tv_max.setText(DetailsFragment.key_ma);

		}

		return view;
	}

	public void set_details_weathertext(String title, String details,
			String date, String time, int trigger, int id1) {

		tv_title.setText(title);
		tv_details.setText(details);
		tv_time.setText(time);
		tv_date.setText(date);
		final int id = id1;
		final int tri = trigger;
		db = new DatabaseReminder(getActivity());
		events = new Events(title, details, time, date);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (events != null) {
			/*
			 * outState.putString(Details_Fragment.key_date,
			 * events.getDatesString());
			 * outState.putString(Details_Fragment.key_time,
			 * events.getTimestring());
			 * 
			 * outState.putString(Details_Fragment.key_details,
			 * events.getDetailsString());
			 * outState.putString(Details_Fragment.key_title,
			 * events.getTitleString());
			 */

			String[] test = { events.getDatesString(), events.getTimestring(),
					events.getDetailsString(), events.getTitleString() };

			outState.putStringArray(key, test);

			Log.e("My tag : ", "date : out" + events.getDatesString());
			Log.e("My tag : ", "time : out" + events.timestring);
			Log.e("My tag : ", "details : out" + events.getDetailsString());
		}
		super.onSaveInstanceState(outState);
	}

}
