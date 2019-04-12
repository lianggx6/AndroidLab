package com.example.lab5;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class MyWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pend = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_text,pend);
        ComponentName me = new ComponentName(context,MyWidget.class);
        appWidgetManager.updateAppWidget(me, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context,intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        if(intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATE"))
        {
            Goods goods = (Goods) intent.getSerializableExtra("goods");
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
            views.setTextViewText(R.id.appwidget_text,goods.name+"仅售"+goods.price);
            views.setImageViewResource(R.id.widgetImage,goods.imageId);
            Intent infoIntent = new Intent(context,InfoActivity.class);
            infoIntent.putExtra("goods",goods);
            PendingIntent pend = PendingIntent.getActivity(context,1,infoIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.appwidget_text,pend);
            ComponentName me = new ComponentName(context,MyWidget.class);
            appWidgetManager.updateAppWidget(me,views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

