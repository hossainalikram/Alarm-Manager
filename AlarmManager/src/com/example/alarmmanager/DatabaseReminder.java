package com.example.alarmmanager;

import java.util.ArrayList;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseReminder extends SQLiteOpenHelper {

	public static final String DB_NAME = "my_event";
	public static final int DB_Version = 1;

	public static final String EVENT_TABLE = "event";

	public static final String ID_FIELD = "_id";
	public static final String EMAIL_FIELD = "email";
	public static final String CODE_FIELD = "code";
	public static final String TITLE_FIELD = "name";
	public static final String TIME_STRING_FIELD = "timestring";
	public static final String Date_STRING_FIELD = "datestring";

	public static final String PHONE_FIELD = "phone";
	public static final String DETAILS_FIELD = "details";
	public static final String TRIGGER_FIELD = "trigger";
	public static final String TIME_FIELD = "time";

	public static final String MY_EVENT_TABLE_SQL = "CREATE TABLE "
			+ EVENT_TABLE + " (" + ID_FIELD + " INTEGER_PRIMARY KEY, "
			+ TITLE_FIELD + " TEXT, " + EMAIL_FIELD + " TEXT, " + PHONE_FIELD
			+ " TEXT, " + TIME_STRING_FIELD + " TEXT, " + Date_STRING_FIELD
			+ " TEXT, " + TIME_FIELD + " LONG, " + CODE_FIELD + " INTEGER, "
			+ TRIGGER_FIELD + " INTEGER, " + DETAILS_FIELD + " TEXT)";

	public DatabaseReminder(Context context) {
		super(context, DB_NAME, null, DB_Version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(MY_EVENT_TABLE_SQL);

		Log.e("MY tag :", "yes db created");

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	public long insertEvent(Events emp) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(TIME_FIELD, emp.getTime());
		values.put(TITLE_FIELD, emp.getTitleString());
		values.put(DETAILS_FIELD, emp.detailsString);
		values.put(ID_FIELD, emp.getId());
		values.put(TRIGGER_FIELD, emp.getTrigger());
		values.put(TIME_STRING_FIELD, emp.getTimestring());
		values.put(Date_STRING_FIELD, emp.getDatesString());
		values.put(PHONE_FIELD, emp.getPhone());
		values.put(EMAIL_FIELD, emp.getEmail());
		values.put(CODE_FIELD, emp.getCode());

		long inserted = db.insert(EVENT_TABLE, null, values);
		db.close();

		return inserted;
	}

	public ArrayList<Events> getAllEvent() {

		ArrayList<Events> allEmployees = new ArrayList<Events>();
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(EVENT_TABLE, null, null, null, null, null,
				null);

		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();

			for (int i = 0; i < cursor.getCount(); i++) {

				int id = cursor.getInt(cursor.getColumnIndex(ID_FIELD));
				int trigger = cursor.getInt(cursor
						.getColumnIndex(TRIGGER_FIELD));
				int code = cursor.getInt(cursor.getColumnIndex(CODE_FIELD));

				String title = cursor.getString(cursor
						.getColumnIndex(TITLE_FIELD));
				long time = cursor.getLong(cursor.getColumnIndex(TIME_FIELD));

				String details = cursor.getString(cursor
						.getColumnIndex(DETAILS_FIELD));
				String datestrString = cursor.getString(cursor
						.getColumnIndex(Date_STRING_FIELD));
				String timeString = cursor.getString(cursor
						.getColumnIndex(TIME_STRING_FIELD));

				String phone = cursor.getString(cursor
						.getColumnIndex(PHONE_FIELD));
				String email = cursor.getString(cursor
						.getColumnIndex(EMAIL_FIELD));

				/*
				 * Events e = new Events(id, trigger, time, title, details,
				 * timeString, datestrString);
				 */

				Events e = new Events(id, trigger, code, time, title, details,
						timeString, datestrString, email, phone);

				allEmployees.add(e);

				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();

		return allEmployees;
	}

	public void InsertedallEvent(ArrayList<Events> eventsarraArrayList) {
		if (eventsarraArrayList.size() > 0) {
			for (int i = 0; i < eventsarraArrayList.size(); i++) {
				Events emp = eventsarraArrayList.get(i);

				insertEvent(emp);

			}
		}

	}

	public void DeleteTableData() {

		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(EVENT_TABLE, null, null);
		db.close();
	}

	public void ResetallEvent() {

		ArrayList<Events> eventsarraArrayList = SortedallEvent();
		DeleteTableData();
		InsertedallEvent(eventsarraArrayList);
	}

	public ArrayList<Events> SortedallEvent() {
		ArrayList<Events> allEmployees = new ArrayList<Events>();
		String query = "select * from event order by time";
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();

			for (int i = 0; i < cursor.getCount(); i++) {

				// int id = cursor.getInt(cursor.getColumnIndex(ID_FIELD));
				int id = i;
				int trigger = cursor.getInt(cursor
						.getColumnIndex(TRIGGER_FIELD));

				int code = cursor.getInt(cursor.getColumnIndex(CODE_FIELD));
				String title = cursor.getString(cursor
						.getColumnIndex(TITLE_FIELD));
				long time = cursor.getLong(cursor.getColumnIndex(TIME_FIELD));

				String details = cursor.getString(cursor
						.getColumnIndex(DETAILS_FIELD));

				String datestrString = cursor.getString(cursor
						.getColumnIndex(Date_STRING_FIELD));
				String timeString = cursor.getString(cursor
						.getColumnIndex(TIME_STRING_FIELD));

				String phone = cursor.getString(cursor
						.getColumnIndex(PHONE_FIELD));
				String email = cursor.getString(cursor
						.getColumnIndex(EMAIL_FIELD));

				/*
				 * Events e = new Events(id, trigger, time, title, details,
				 * timeString, datestrString);
				 */

				Events e = new Events(id, trigger, code, time, title, details,
						timeString, datestrString, email, phone);

				// Events e = new Events(id, trigger, time, title, details);
				allEmployees.add(e);

				cursor.moveToNext();
			}
		}

		cursor.close();
		db.close();

		return allEmployees;
	}

	public int updateEmployee(int id) {

		SQLiteDatabase db1 = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(TRIGGER_FIELD, 0);
		int updated = db1.update(EVENT_TABLE, values, ID_FIELD + "=?",
				new String[] { "" + id });

		db1.close();
		return updated;
	}

	public int updateEmployee_withtrigger(int id, int trigger) {

		SQLiteDatabase db1 = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		if(trigger==0){
			values.put(TRIGGER_FIELD, 1);
		}else {
			values.put(TRIGGER_FIELD, 0);
		}
			
		
		int updated = db1.update(EVENT_TABLE, values, ID_FIELD + "=?",
				new String[] { "" + id });

		db1.close();
		
		
		return updated;
	}

	public void delete_one_row(int id) {

		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(EVENT_TABLE, ID_FIELD + "=?",
				new String[] { String.valueOf(id) }); // KEY_ID= id of row and
														// third parameter is
														// argument.
		db.close();
	}

}
