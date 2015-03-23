package com.example.alarmmanager;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.EventLogTags.Description;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomizedEventAdaptar extends ArrayAdapter<Events>{
	
	ArrayList<Events>eventlist;
	Activity context;

	public CustomizedEventAdaptar(Context context, ArrayList<Events>events) {
		super(context,R.layout.event_list_item, events);
		this.context = (Activity) context;
		this.eventlist = events;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v=null;
		
		if (convertView == null) {

			LayoutInflater inflater = context.getLayoutInflater();
			v = inflater.inflate(R.layout.event_list_item, null);

			TextView title, details,date,time,r_date,r_time,onoff;
			ImageView imageView;

			title = (TextView) v.findViewById(R.id.tv_title_list);
			date = (TextView) v.findViewById(R.id.tv_date_list);
			//r_date = (TextView) v.findViewById(R.id.tv_r_date);
			time = (TextView) v.findViewById(R.id.tv_time_list);
			onoff = (TextView) v.findViewById(R.id.tv_on_off);
			//r_time = (TextView) v.findViewById(R.id.tv_r_time_title);
			//imageView = (ImageView) v.findViewById(R.id.imageView1);

			Events e =eventlist.get(position);

			
			title.setText(e.getTitleString());
			date.setText(e.getDatesString());
			time.setText(e.getTimestring());
			
			if(e.getTrigger()==0)
			{
				onoff.setText("off");
			}else {
				onoff.setText("on");
			}
			
			//imageView.setImageBitmap(theImage);

		} else {

			v = convertView;
		}
		
		
		return v;
	}

}
