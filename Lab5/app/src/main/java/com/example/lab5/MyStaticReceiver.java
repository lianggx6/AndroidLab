package com.example.lab5;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

public class MyStaticReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
        Goods goods = (Goods) intent.getSerializableExtra("goods");
        NotificationManager manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder=new Notification.Builder(context);
        if(intent.getAction().equals("Static"))
        {
            builder.setContentTitle("新商品热卖")
                    .setContentText(goods.name+"仅售"+goods.price)
                    .setTicker("新商品热卖")
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),goods.imageId))
                    .setSmallIcon(goods.imageId)
                    .setAutoCancel(true);
            Intent boardcastIntent =new Intent(context,InfoActivity.class);
            boardcastIntent.putExtra("goods",goods);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, boardcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            Notification notify=builder.build();
            manager.notify((int)System.currentTimeMillis(),notify);
        }
    }
}
