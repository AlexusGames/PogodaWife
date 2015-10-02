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
	private Context mContext;
	String texttt;
	int aa = 0;
	ArrayList<Forecast> items = new ArrayList<Forecast>();
	
	
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		mContext = context;
		Log.d(LOG_TAG, "onUpdate " + Arrays.toString(appWidgetIds));
		for (int id : appWidgetIds) {
			updateWidget(context, appWidgetManager,	id);
		}
		Log.d(LOG_TAG, "onUpdate complete");
	}

	 void updateWidget(Context context,
			AppWidgetManager appWidgetManager, int id) {
		
		 // Читаем параметры Preferences
	    /*String widgetText = sp.getString(ConfigActivity.WIDGET_TEXT + widgetID, null);
	    if (widgetText == null) return;
	    int widgetColor = sp.getInt(ConfigActivity.WIDGET_COLOR + widgetID, 0);*/
		new FetchItemsTask().execute(id);
	    // Настраиваем внешний вид виджета
	    widgetView = new RemoteViews(context.getPackageName(),
	        R.layout.widget_main);
	    widgetView.setTextViewText(R.id.widgetButton, texttt + context.getString(R.string.default_temperature));
	   
	    Intent updateIntent = new Intent(context, PogodaWidget.class);
	    updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
	    updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
	        new int[] { id });
	    PendingIntent pIntent = PendingIntent.getBroadcast(context, id, updateIntent, 0);
	    widgetView.setOnClickPendingIntent(R.id.widgetButton, pIntent);
	    
	   
	   
	    
	    
        //widgetView.setTextViewText(R.id.widgetButton, "" + aa);
        Log.d(LOG_TAG, "onUpdate progress");
	    appWidgetManager.updateAppWidget(id, widgetView);
		
	}
	
	 @Override
		public void onReceive(Context context, Intent intent) {
			super.onReceive(context, intent);
	}
	 
	private class FetchItemsTask extends AsyncTask< Integer,Void,ArrayList<Forecast>> {
        @Override
        protected ArrayList<Forecast> doInBackground(Integer... ids) {
            return new HttpQuery().fetchWeather(ids[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Forecast> items) {
            widgetView.setTextViewText(R.id.widgetButton, items.get(0).getCurrentTemperature());
            //texttt = items.get(0).getCurrentTemperature();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
            appWidgetManager.updateAppWidget(items.get(0).getWidgetId(), widgetView);
            Log.d(LOG_TAG, "onPost " + texttt);
           
        }
    }
	
	

}
