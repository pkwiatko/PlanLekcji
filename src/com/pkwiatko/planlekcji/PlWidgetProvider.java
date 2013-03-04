package com.pkwiatko.planlekcji;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;

public class PlWidgetProvider extends AppWidgetProvider {

	private static final String ACTION_CLICK = "ACTION_CLICK";
	

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		
		DatabaseHandler db = new DatabaseHandler(context);
		
		List<String> students = db.getAllStudents();
		String student = "";
		if (students.size() > 0)	{
			student = students.get(0);
		}
					    
		Calendar calendar = Calendar.getInstance();			    
	    int currDayNo = calendar.get(Calendar.DAY_OF_WEEK);
	    if (currDayNo == 1) {
	    	currDayNo=7;
	    } else	{
	    	currDayNo--;
	    }
	    //Log.d("PlWidgetProvider", "Student: " + student + " DayNo:" + currDayNo);
	    if (currDayNo == 6 || currDayNo == 7) currDayNo=1;
	    
	    Cursor planCursor = db.getPlanByStudent(student, currDayNo);
	    
		// Get all ids
		ComponentName thisWidget = new ComponentName(context,
				PlWidgetProvider.class);
		
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		
		for (int widgetId : allWidgetIds) {			
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.widget_layout);
			// Log.w("WidgetExample", String.valueOf(number));
			// Set the text
			//remoteViews.setTextViewText(R.id.update, String.valueOf(number));
									
			if (planCursor != null) {				
				if (planCursor.getCount()>0)	{
					planCursor.moveToPosition(0);					
					remoteViews.setTextViewText(R.id.wgt1_plan_lesson_no, planCursor.getString(3));
					remoteViews.setTextViewText(R.id.wgt1_plan_lesson_start_time, planCursor.getString(9));
					remoteViews.setTextViewText(R.id.wgt1_plan_lesson_end_time, planCursor.getString(10));
					remoteViews.setTextViewText(R.id.wgt1_plan_subject, planCursor.getString(12));
					remoteViews.setTextViewText(R.id.wgt1_plan_classroom, planCursor.getString(5));
				}
				if (planCursor.getCount()>1)	{
					planCursor.moveToPosition(1);					
					remoteViews.setTextViewText(R.id.wgt2_plan_lesson_no, planCursor.getString(3));
					remoteViews.setTextViewText(R.id.wgt2_plan_lesson_start_time, planCursor.getString(9));
					remoteViews.setTextViewText(R.id.wgt2_plan_lesson_end_time, planCursor.getString(10));
					remoteViews.setTextViewText(R.id.wgt2_plan_subject, planCursor.getString(12));
					remoteViews.setTextViewText(R.id.wgt2_plan_classroom, planCursor.getString(5));
				}
				if (planCursor.getCount()>2)	{
					planCursor.moveToPosition(2);					
					remoteViews.setTextViewText(R.id.wgt3_plan_lesson_no, planCursor.getString(3));
					remoteViews.setTextViewText(R.id.wgt3_plan_lesson_start_time, planCursor.getString(9));
					remoteViews.setTextViewText(R.id.wgt3_plan_lesson_end_time, planCursor.getString(10));
					remoteViews.setTextViewText(R.id.wgt3_plan_subject, planCursor.getString(12));
					remoteViews.setTextViewText(R.id.wgt3_plan_classroom, planCursor.getString(5));
				}
				if (planCursor.getCount()>3)	{
					planCursor.moveToPosition(3);					
					remoteViews.setTextViewText(R.id.wgt4_plan_lesson_no, planCursor.getString(3));
					remoteViews.setTextViewText(R.id.wgt4_plan_lesson_start_time, planCursor.getString(9));
					remoteViews.setTextViewText(R.id.wgt4_plan_lesson_end_time, planCursor.getString(10));
					remoteViews.setTextViewText(R.id.wgt4_plan_subject, planCursor.getString(12));
					remoteViews.setTextViewText(R.id.wgt4_plan_classroom, planCursor.getString(5));
				}
				if (planCursor.getCount()>4)	{
					planCursor.moveToPosition(4);					
					remoteViews.setTextViewText(R.id.wgt5_plan_lesson_no, planCursor.getString(3));
					remoteViews.setTextViewText(R.id.wgt5_plan_lesson_start_time, planCursor.getString(9));
					remoteViews.setTextViewText(R.id.wgt5_plan_lesson_end_time, planCursor.getString(10));
					remoteViews.setTextViewText(R.id.wgt5_plan_subject, planCursor.getString(12));
					remoteViews.setTextViewText(R.id.wgt5_plan_classroom, planCursor.getString(5));
				}
				if (planCursor.getCount()>5)	{
					planCursor.moveToPosition(5);					
					remoteViews.setTextViewText(R.id.wgt6_plan_lesson_no, planCursor.getString(3));
					remoteViews.setTextViewText(R.id.wgt6_plan_lesson_start_time, planCursor.getString(9));
					remoteViews.setTextViewText(R.id.wgt6_plan_lesson_end_time, planCursor.getString(10));
					remoteViews.setTextViewText(R.id.wgt6_plan_subject, planCursor.getString(12));
					remoteViews.setTextViewText(R.id.wgt6_plan_classroom, planCursor.getString(5));
				}
				if (planCursor.getCount()>6)	{
					planCursor.moveToPosition(6);					
					remoteViews.setTextViewText(R.id.wgt7_plan_lesson_no, planCursor.getString(3));
					remoteViews.setTextViewText(R.id.wgt7_plan_lesson_start_time, planCursor.getString(9));
					remoteViews.setTextViewText(R.id.wgt7_plan_lesson_end_time, planCursor.getString(10));
					remoteViews.setTextViewText(R.id.wgt7_plan_subject, planCursor.getString(12));
					remoteViews.setTextViewText(R.id.wgt7_plan_classroom, planCursor.getString(5));
				}
				if (planCursor.getCount()>7)	{
					planCursor.moveToPosition(7);					
					remoteViews.setTextViewText(R.id.wgt8_plan_lesson_no, planCursor.getString(3));
					remoteViews.setTextViewText(R.id.wgt8_plan_lesson_start_time, planCursor.getString(9));
					remoteViews.setTextViewText(R.id.wgt8_plan_lesson_end_time, planCursor.getString(10));
					remoteViews.setTextViewText(R.id.wgt8_plan_subject, planCursor.getString(12));
					remoteViews.setTextViewText(R.id.wgt8_plan_classroom, planCursor.getString(5));
				}
				
			}
			
			// Register an onClickListener
			Intent intent = new Intent(context, PlWidgetProvider.class);

			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
					0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.wgt1_plan_lesson_no, pendingIntent);
//			remoteViews.setOnClickPendingIntent(R.id.update1, pendingIntent);
//			remoteViews.setOnClickPendingIntent(R.id.update2, pendingIntent);
//			remoteViews.setOnClickPendingIntent(R.id.update3, pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
		
		planCursor.close();
	}
}