package com.example.alarmmanager;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.R.integer;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.net.Uri;
import android.os.Handler;
import android.provider.CalendarContract.EventsEntity;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

	private static Context context;

	DatabaseReminder databaseReminder;

	MainActivity mainActivity = new MainActivity();

	String to, body, part1 = "", part2 = "", title;
	int event_code;

	long time;
	int req_code;
	int interval = 26 * 1000;
	int flag = 0;
	Handler handler;

	@Override
	public void onReceive(Context context, Intent intent) {

		databaseReminder = new DatabaseReminder(context);
		databaseReminder.ResetallEvent();

		ArrayList<Events> eventsarraArrayList = databaseReminder.getAllEvent();

		if (eventsarraArrayList.size() > 0) {

			// Log.e("MYLOG ", "data :" + eventsarraArrayList.toString());

			for (int i = 0; i < eventsarraArrayList.size(); i++) {

				Events events = eventsarraArrayList.get(i);

				if (events.getTrigger() == 1) {

					event_code = events.getCode();
					to = events.getPhone();
					title = events.getTitleString();
					body = events.getDetailsString();
					databaseReminder.updateEmployee(events.getId());

					MainActivity.flag_alarm=0;
					break;
				}

			}

		}

		

		switch (event_code) {

		case 100:

			Log.d("My log : ", "After Alarm In Alarm ! ALarm  ");
			MediaPlayer mPlayer;
			mPlayer = MediaPlayer.create(context, R.raw.htc);
			mPlayer.start();

		
			break;

		case 101:

			String replySmsbody = body;
			String number = null;

			if (to.equals("")) {
				// Toast.makeText(context, "No Number ",
				// Toast.LENGTH_LONG).show();
				
				MainActivity.flag_alarm=1;

			} else {

				SmsManager manager = SmsManager.getDefault();
				try {
					manager.sendTextMessage(to, null, body, null, null);
					Toast.makeText(context, "Reply sent", Toast.LENGTH_LONG)
							.show();
					flag = 1;

				} catch (Exception e) {
					Toast.makeText(context,
							"Message Not Send . Check your number or balance",
							Toast.LENGTH_LONG).show();
				}
				
				MainActivity.flag_alarm=0;

			}

			break;

		default:
			break;
		}

		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification notify = new Notification(R.drawable.clock,
				"Are You Forgot About This :", System.currentTimeMillis());
		Context context2 = context;

		CharSequence titleCharSequence;
		
		if (event_code == 101) {

			if (flag == 1) {
				titleCharSequence = "Message Send to " + to;
			} else {
				titleCharSequence = " OH ! Message Not Send to " + to;
			}

		} else {

			titleCharSequence = title;

		}

		CharSequence detailsCharSequence = body;
		Intent intent101 = new Intent(context2, AlarmManager.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context2, 0,
				intent, 0);
		notify.setLatestEventInfo(context2, titleCharSequence,
				detailsCharSequence, pendingIntent);
		nm.notify(0, notify);

		databaseReminder.ResetallEvent();
		ArrayList<Events> eventsarraArrayList2 = databaseReminder.getAllEvent();

		// mainActivity.SetAlarm1(eventsarraArrayList);
		if (eventsarraArrayList2.size() > 0) {

			for (int i = 0; i < eventsarraArrayList2.size(); i++) {

				Events events = eventsarraArrayList2.get(i);

				if (events.getTrigger() == 1) {

					time = events.getTime();
					req_code = events.getId();

					Log.e("After update : ", "Data :" + events.toString());
					
					MainActivity.flag_alarm=1;

					Intent intent2 = new Intent(context, MainActivity.class);
					intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent2);

					

					break;
				}

			}
		}

	}

	
}
