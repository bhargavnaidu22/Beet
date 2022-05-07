package com.gbnsolutions.beet.Notification;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import static com.gbnsolutions.beet.Notification.ApplicationClass.*;

public class MusicService extends Service {
    ActionPlay actionPlaying;
    private final  IBinder mBinder = new MyBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    public class MyBinder extends Binder{
        public MusicService getService(){
            return MusicService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getStringExtra("myActionName");
        if (action!=null) {
            switch (action) {
                case ACTION_PLAY:
                    if (actionPlaying!=null){
                        actionPlaying.PlayClicked();
                    }
                    break;
                case ACTION_PREV:
                    if (actionPlaying!=null){
                        actionPlaying.PrevClicked();
                    }
                    break;
                case ACTION_NEXT:
                    if (actionPlaying!=null){
                        actionPlaying.NextClicked();
                    }
                    break;
            }
        }
        return START_STICKY;
    }
    public void setCallBack(ActionPlay actionPlaying1){
        this.actionPlaying = actionPlaying1;
    }
}
