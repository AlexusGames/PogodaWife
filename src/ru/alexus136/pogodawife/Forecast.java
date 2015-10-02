package ru.alexus136.pogodawife;

public class Forecast {

	private int mWidgetId;
	
	
	
	private String mDay;
	private String mMonth;
	private String mWeekday;
	private String mTod;
	
	private String mCurrentTemperature;
	private String mMinTemperature;
	private String mMaxTemperature;
	
	
	public Forecast(int id) {
		mWidgetId = id;
	}
	
	
	public int getWidgetId() {
		return mWidgetId;
	}
	
	public String getDay() {
		return mDay;
	}
	public void setDay(String day) {
		mDay = day;
	}
	public String getMonth() {
		return mMonth;
	}
	public void setMonth(String month) {
		mMonth = month;
	}
	public String getWeekday() {
		return mWeekday;
	}
	public void setWeekday(String weekday) {
		mWeekday = weekday;
	}
	public String getTod() {
		return mTod;
	}
	public void setTod(String tod) {
		mTod = tod;
	}
	public String getMinTemperature() {
		return mMinTemperature;
	}
	public void setMinTemperature(String minTemperature) {
		mMinTemperature = minTemperature;
	}
	public String getMaxTemperature() {
		return mMaxTemperature;
	}
	public void setMaxTemperature(String maxTemperature) {
		mMaxTemperature = maxTemperature;
	}
	public String getCurrentTemperature() {
		return mCurrentTemperature;
	}
	public void setCurrentTemperature(String currentTemperature) {
		mCurrentTemperature = currentTemperature;
	}
	
	
	
}
