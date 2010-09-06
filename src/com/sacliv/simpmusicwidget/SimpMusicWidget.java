/**
 * 
 */
package com.sacliv.simpmusicwidget;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * @author isaacliu
 * 
 */
public class SimpMusicWidget extends AppWidgetProvider {
    public static String ACTION_WIDGET_RECEIVER = "PLAYActionReceiverWidget";
    public static String ACTION_WIDGET_CONFIGURE = "SimpMusicWidgetActionReceiverWidget";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if (intent.getAction().equals(ACTION_WIDGET_RECEIVER)) {
            String msg = "null";
            try {
                msg = intent.getStringExtra("msg");
            } catch (NullPointerException e) {
                Log.e("Error", "msg = null");
            } 
            
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                    intent, 0);
            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            Notification noty = new Notification(R.drawable.icon,
                    "Music Player Started - 2", System.currentTimeMillis());
            noty.setLatestEventInfo(context, "Notice", msg, contentIntent);
            notificationManager.notify(1, noty);
        }
        else {
            Toast.makeText(context, intent.getAction(), Toast.LENGTH_SHORT).show();
        }
            
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
            int[] appWidgetIds) {
        // TODO Auto-generated method stub
        
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.main);

        Toast.makeText(context, "onUpdate", Toast.LENGTH_SHORT).show();
        
        Intent configIntent = new Intent(context, ConfigureSMW.class);
        configIntent.setAction(ACTION_WIDGET_CONFIGURE);
        PendingIntent configurePendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);
        
        Intent action = new Intent(context, SimpMusicWidget.class);
        action.setAction(ACTION_WIDGET_RECEIVER);
        action.putExtra("msg", "This starts playing");
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context,
                0, action, 0);

        remoteViews.setOnClickPendingIntent(R.id.play_button,
                actionPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.next_button,
                configurePendingIntent);
        
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
    

}


