package com.example.lab5;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.widget.RemoteViews;

public class MyDynamicReceiver extends BroadcastReceiver
{
    @SuppressLint("NewApi")
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Goods goods = (Goods) intent.getSerializableExtra("goods");
        NotificationManager manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder=new Notification.Builder(context);
        if(intent.getAction().equals("Dynamic"))
        {
            builder.setContentTitle("马上下单")
                    .setContentText(goods.name+"已添加到购物车")
                    .setTicker("马上下单")
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),goods.imageId))
                    .setSmallIcon(goods.imageId)
                    .setAutoCancel(true);
            Intent boardcastIntent =new Intent(context,MainActivity.class);
            boardcastIntent.putExtra("shoppingVisiablity",true);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, boardcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            Notification notify=builder.build();
            manager.notify((int)System.currentTimeMillis(),notify);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
            views.setTextViewText(R.id.appwidget_text,goods.name+"已添加到购物车");
            views.setImageViewResource(R.id.widgetImage,goods.imageId);
            views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);
            ComponentName me = new ComponentName(context,MyWidget.class);
            appWidgetManager.updateAppWidget(me,views);
        }

    }
}
