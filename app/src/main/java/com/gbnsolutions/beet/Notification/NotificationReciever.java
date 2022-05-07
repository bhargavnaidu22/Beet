package com.gbnsolutions.beet.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import static com.gbnsolutions.beet.Notification.ApplicationClass.*;

public class NotificationReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context,MusicService.class);
        if (intent.getAction()!=null) {
            switch (intent.getAction()){
                case ACTION_PLAY:
//                    Toast.makeText(context,ACTION_PLAY,Toast.LENGTH_SHORT).show();
                    intent1.putExtra("myActionName",intent.getAction());
                    context.startService(intent1);
                    break;
                case ACTION_PREV:
//                    Toast.makeText(context,ACTION_PREV,Toast.LENGTH_SHORT).show();
                    intent1.putExtra("myActionName",intent.getAction());
                    context.startService(intent1);
                    break;
                case ACTION_NEXT:
//                    Toast.makeText(context,ACTION_NEXT,Toast.LENGTH_SHORT).show();
                    intent1.putExtra("myActionName",intent.getAction());
                    context.startService(intent1);
                    break;
            }
        }
    }
}
