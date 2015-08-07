package com.example.alarmmanager;

import java.util.ArrayList;

import android.R.integer;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class List_Fragment extends Fragment {

	private static final String SELECTED_KEY = null;
	private Communicator communicator;
	ListView lv;
	DatabaseReminder databaseReminder;
	CustomizedEventAdaptar customizedEventAdaptar;
	public static ArrayList<Events> contactArrayList;
	MainActivity mainActivity = new MainActivity();
	static int mposition;
	int save = -1;
	int s = -1;

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);

		if (activity instanceof Communicator) {
			communicator = (Communicator) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implemenet MyListFragment.Communicator");
		}
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.list_fragment, container, false);
		lv = (ListView) view.findViewById(R.id.lv_event);
		databaseReminder = new DatabaseReminder(getActivity());

		List_Fragment.contactArrayList = databaseReminder.getAllEvent();

		if (List_Fragment.contactArrayList != null
				&& List_Fragment.contactArrayList.size() > 0) {

			customizedEventAdaptar = new CustomizedEventAdaptar(getActivity(),
					List_Fragment.contactArrayList);

			lv.setAdapter(customizedEventAdaptar);

		}

		if (savedInstanceState != null
				&& savedInstanceState.containsKey(SELECTED_KEY)) {

			List_Fragment.mposition = savedInstanceState.getInt(SELECTED_KEY);
			Log.e("MY LOG : ", "mposition = " + List_Fragment.mposition); //

			lv.setItemChecked(List_Fragment.mposition, true);

		}

		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long id) {

				List_Fragment.contactArrayList.get(position);
				Events w = List_Fragment.contactArrayList.get(position);

				Log.e("My tag : ", "Touched : " + w.toString());

				String title = w.getTitleString();
				String details = w.getDetailsString();
				String date = w.getDatesString();
				String time = w.getTimestring();
				int trigger = w.getTrigger();
				int id1 = w.getId();

				updatefragment(title, details, date, time, trigger, id1);

			}

		});

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View arg1,
					int p, long id) {

				parent.getChildAt(p).setBackgroundColor(
						getResources()
								.getColor(android.R.color.holo_green_dark));

				if (s != -1 && s != p) {
					parent.getChildAt(s).setBackgroundColor(
							getResources()
									.getColor(android.R.color.darker_gray));
				}

				s = p;
				Log.e("MY log ", "LOng pressed : " + p);

				List_Fragment.contactArrayList.get(p);
				Events w = contactArrayList.get(p);
				int id1 = w.getId();
				List_Fragment.contactArrayList.remove(p);
				DatabaseReminder db = new DatabaseReminder(getActivity());
				db.delete_one_row(id1);
				
				//MycontentProvider provider=new MycontentProvider();
	

				/*Uri uri = Uri.parse(MycontentProvider.CONTENT_URI + "/" + id1);
				getcdelete(uri, null, null);*/
				
				
				

				db.ResetallEvent();
				customizedEventAdaptar.notifyDataSetChanged();
				lv.setAdapter(customizedEventAdaptar);

				return true;
			}

		});

		return view;
	}
	
	

	public interface Communicator {
		public void contactdetails(String title, String details, String date,
				String time, int trigger, int id1);
	}

	public void updatefragment(String title, String details, String date,
			String time, int trigger, int id1) {
		// Log.e("MY LOG : ", " on item Click :"+imagebyte);
		communicator.contactdetails(title, details, date, time, trigger, id1);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub

		if (List_Fragment.mposition != ListView.INVALID_POSITION) {
			outState.putInt(SELECTED_KEY, List_Fragment.mposition);
			Log.e("MY LOG : ", "mposition = " + List_Fragment.mposition);

		}
		super.onSaveInstanceState(outState);
	}

}
