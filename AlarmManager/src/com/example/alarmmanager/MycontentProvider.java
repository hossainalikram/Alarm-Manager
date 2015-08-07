package com.example.alarmmanager;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class MycontentProvider extends ContentProvider {

	private DatabaseReminder database;

	private static final int TODOS = 10;
	private static final int TODO_ID = 20;

	private static final String AUTHORITY = "com.example.alarmmanager";

	private static final String BASE_PATH = "event";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH);

	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/event";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/event";

	private static final UriMatcher sURIMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, BASE_PATH, TODOS);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", TODO_ID);
	}

	@Override
	public boolean onCreate() {
		database = new DatabaseReminder(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub

		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		// check if the caller has requested a column which does not exists
		checkColumns(projection);

		// Set the table
		queryBuilder.setTables(DatabaseReminder.EVENT_TABLE);

		int uriType = sURIMatcher.match(uri);
		switch (uriType) {
		case TODOS:
			break;
		case TODO_ID:
			// adding the ID to the original query
			queryBuilder.appendWhere(DatabaseReminder.ID_FIELD + "="
					+ uri.getLastPathSegment());
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		SQLiteDatabase db = database.getWritableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, null, null, sortOrder);
		// make sure that potential listeners are getting notified
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;
		switch (uriType) {
		case TODOS:
			rowsDeleted = sqlDB.delete(DatabaseReminder.EVENT_TABLE, selection,
					selectionArgs);
			break;
		case TODO_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(DatabaseReminder.EVENT_TABLE,
						DatabaseReminder.ID_FIELD + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(DatabaseReminder.EVENT_TABLE,
						DatabaseReminder.ID_FIELD + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;

	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;
		long id = 0;
		switch (uriType) {
		case TODOS:
			id = sqlDB.insert(DatabaseReminder.EVENT_TABLE, null, values);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(BASE_PATH + "/" + id);

	}

	private void checkColumns(String[] projection) {
		// TODO Auto-generated method stub

		String[] available = { DatabaseReminder.ID_FIELD,
				DatabaseReminder.EMAIL_FIELD, DatabaseReminder.CODE_FIELD,
				DatabaseReminder.TITLE_FIELD,
				DatabaseReminder.TIME_STRING_FIELD,
				DatabaseReminder.Date_STRING_FIELD,
				DatabaseReminder.PHONE_FIELD, DatabaseReminder.TRIGGER_FIELD,
				DatabaseReminder.DETAILS_FIELD, DatabaseReminder.TIME_FIELD };
		if (projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(
					Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(
					Arrays.asList(available));
			// check if all columns which are requested are available
			if (!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException(
						"Unknown columns in projection");
			}
		}
	}

	// }

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

}
