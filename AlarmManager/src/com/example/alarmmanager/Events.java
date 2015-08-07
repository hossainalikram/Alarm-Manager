package com.example.alarmmanager;

import android.R.integer;

public class Events {

	int id, trigger, code;
	long time;
	String titleString;
	String detailsString, timestring, datesString, email, phone;

	public Events(String titleString, String detailsString, String timestring,
			String datesString) {
		super();

		
		this.titleString = titleString;
		this.detailsString = detailsString;
		this.timestring = timestring;
		this.datesString = datesString;
		
	}

	public Events(int trigger, int code, long time, String titleString,
			String detailsString, String timestring, String datesString,
			String email, String phone) {
		super();

		this.trigger = trigger;
		this.code = code;
		this.time = time;
		this.titleString = titleString;
		this.detailsString = detailsString;
		this.timestring = timestring;
		this.datesString = datesString;
		this.email = email;
		this.phone = phone;
	}

	public Events(int id, int trigger, int code, long time, String titleString,
			String detailsString, String timestring, String datesString,
			String email, String phone) {
		super();
		this.id = id;
		this.trigger = trigger;
		this.code = code;
		this.time = time;
		this.titleString = titleString;
		this.detailsString = detailsString;
		this.timestring = timestring;
		this.datesString = datesString;
		this.email = email;
		this.phone = phone;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Events(int trigger, long time, String titleString,
			String detailsString, String timestring, String datesString) {
		super();
		this.trigger = trigger;
		this.time = time;
		this.titleString = titleString;
		this.detailsString = detailsString;
		this.timestring = timestring;
		this.datesString = datesString;
	}

	public Events(int id, int trigger, long time, String titleString,
			String detailsString, String timestring, String datesString) {
		super();
		this.id = id;
		this.trigger = trigger;
		this.time = time;
		this.titleString = titleString;
		this.detailsString = detailsString;
		this.timestring = timestring;
		this.datesString = datesString;
	}

	public String getDatesString() {
		return datesString;
	}

	public void setDatesString(String datesString) {
		this.datesString = datesString;
	}

	public String getTimestring() {
		return timestring;
	}

	public void setTimestring(String timestring) {
		this.timestring = timestring;
	}

	public int getTrigger() {
		return trigger;
	}

	public void setTrigger(int trigger) {
		this.trigger = trigger;
	}

	/*
	 * public Events(int id, long time, String titleString, String
	 * detailsString) { super(); this.id = id; this.time = time;
	 * this.titleString = titleString; this.detailsString = detailsString; }
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getTitleString() {
		return titleString;
	}

	public void setTitleString(String titleString) {
		this.titleString = titleString;
	}

	@Override
	public String toString() {
		return "Events [id=" + id + ", trigger=" + trigger + ", time=" + time
				+ ", titleString=" + titleString + ", detailsString="
				+ detailsString + ", timestring=" + timestring
				+ ", datesString=" + datesString + "]";
	}

	public String getDetailsString() {
		return detailsString;
	}

	public void setDetailsString(String detailsString) {
		this.detailsString = detailsString;
	}

}
