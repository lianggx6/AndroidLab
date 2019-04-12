package com.example.lab6;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import java.io.IOException;

public class MusicService extends Service {

    IBinder mBinder = new MyBinder();
    public static MediaPlayer mediaPlayer = new MediaPlayer();;

    public MusicService() {
        try {
            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory() + "/Lab6/melt.mp3");
            mediaPlayer.prepareAsync();
            mediaPlayer.setLooping(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder
    {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            switch (code) {
                case 100:
                    if(mediaPlayer != null)
                    {
                        Bundle bundle = new Bundle();
                        if(mediaPlayer.isPlaying()) bundle.putBoolean("isPlaying",true);
                        else bundle.putBoolean("isPlaying",false);
                        bundle.putInt("currentTime",mediaPlayer.getCurrentPosition());
                        bundle.putInt("totalTime",mediaPlayer.getDuration());
                        Log.i("currenttimeeeeee",String.valueOf(mediaPlayer.getCurrentPosition()));
                        reply.writeBundle(bundle);
                    }
                    break;
                case 101:
                    if(mediaPlayer.isPlaying())
                        mediaPlayer.pause();
                    else mediaPlayer.start();
                    break;
                case 102:
                   if (mediaPlayer != null){
                        mediaPlayer.stop();
                        try {
                            mediaPlayer.reset();
                            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory() + "/Lab6/melt.mp3");
                            mediaPlayer.prepareAsync();
                            mediaPlayer.seekTo(0);
                            Log.i("stoppppppppppppp","stopppppppppppppppppppppppppppppp");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 103:
                    mediaPlayer.seekTo(data.readInt());
                    Log.i("seekbarrrrrrrrrr","cccccccccccccccccccccccc");
                    break;
            }
            return super.onTransact(code, data, reply, flags);
        }
    }
}
