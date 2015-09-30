package ru.alexus136.pogodawife;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;


public class PogodaWidget extends AppWidgetProvider {

	private static final String LOG_TAG = "Logs";
	RemoteViews widgetView;
	String texttt;
	
	ArrayList<Forecast>
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Log.d(LOG_TAG, "onUpdate " + Arrays.toString(appWidgetIds));
		for (int id : appWidgetIds) {
			updateWidget(context, appWidgetManager,	id);
		}
		Log.d(LOG_TAG, "onUpdate complete");
	}

	private void updateWidget(Context context,
			AppWidgetManager appWidgetManager, int id) {
		
		 // Читаем параметры Preferences
	    /*String widgetText = sp.getString(ConfigActivity.WIDGET_TEXT + widgetID, null);
	    if (widgetText == null) return;
	    int widgetColor = sp.getInt(ConfigActivity.WIDGET_COLOR + widgetID, 0);*/
	    
	    // Настраиваем внешний вид виджета
	    widgetView = new RemoteViews(context.getPackageName(),
	        R.layout.widget_main);
	    widgetView.setTextViewText(R.id.widgetButton, context.getString(R.string.default_temperature));
	   
	    Intent updateIntent = new Intent(context, PogodaWidget.class);
	    updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
	    updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
	        new int[] { id });
	    PendingIntent pIntent = PendingIntent.getBroadcast(context, id, updateIntent, 0);
	    widgetView.setOnClickPendingIntent(R.id.widgetButton, pIntent);
	    
	   
	    //new FetchItemsTask().execute();
	    
	    
	    appWidgetManager.updateAppWidget(id, widgetView);
		
	}
	
	private class FetchItemsTask extends AsyncTask< Void,Void,ArrayList<Forecast>> {
        @Override
        protected ArrayList<Forecast> doInBackground(Void... params) {
            return new HttpQuery().fetchWeather();
        }

        @Override
        protected void onPostExecute(ArrayList<Forecast> items) {
            //widgetView.setTextViewText(R.id.widgetButton, items.get(0).getCurrentTemperature());
            texttt = items.get(0).getCurrentTemperature();
            Log.d(LOG_TAG, "onPost " + texttt);
            updateWidget(context,
        			AppWidgetManager appWidgetManager, int id)
        }
    }
	
	

}
