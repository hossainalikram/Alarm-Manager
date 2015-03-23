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

	DatabaseReminder db;

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
		//sw = (TextView) view.findViewById(R.id.switch1);

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

	}

}
