package com.example.lab6;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button play;
    Button stop;
    Button quit;
    TextView current;
    TextView total;
    TextView state;
    ImageView cover;
    SeekBar seekBar;
    IBinder mBinder;
    Bundle bundle;
    Handler handler;
    Thread thread;
    ObjectAnimator animator;
    ServiceConnection mConnection;
    SimpleDateFormat simpleDateFormat;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();
        verifyStoragePermissions(MainActivity.this);
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                mBinder = iBinder;
            }
            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mConnection = null;
            }
        };
        final Intent intent = new Intent(MainActivity.this,MusicService.class);
        startService(intent);
        bindService(intent, mConnection, BIND_AUTO_CREATE);


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(play.getText().toString().equals("PLAY"))
                {
                    if(animator.isStarted()) animator.resume();
                    else animator.start();
                    state.setText(R.string.playing);
                }
                else
                {
                    animator.pause();
                    state.setText(R.string.paused);
                }
                try {
                    @SuppressLint("Recycle") Parcel data = Parcel.obtain();
                    @SuppressLint("Recycle") Parcel reply = Parcel.obtain();
                    mBinder.transact(101,data,reply,0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play.setText(R.string.play);
                try {
                    @SuppressLint("Recycle") Parcel data = Parcel.obtain();
                    @SuppressLint("Recycle") Parcel reply = Parcel.obtain();
                    mBinder.transact(102,data,reply,0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                state.setText(R.string.stopped);
                seekBar.setProgress(0);
                animator.end();
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unbindService(mConnection);
                mConnection = null;
                finish();
                System.exit(0);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                @SuppressLint("Recycle") Parcel data = Parcel.obtain();
                @SuppressLint("Recycle") Parcel reply = Parcel.obtain();
                if(b)
                {
                    current.setText(simpleDateFormat.format(i));
                    try {
                        data.writeInt(i);
                        mBinder.transact(103,data,reply,0);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        thread = new Thread()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(mBinder != null)
                    {
                        handler.obtainMessage(123).sendToTarget();
                    }
                }
            }
        };
        thread.start();


        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case 123:
                        @SuppressLint("Recycle") Parcel data = Parcel.obtain();
                        @SuppressLint("Recycle") Parcel reply = Parcel.obtain();
                        try {
                            mBinder.transact(100,data,reply,0);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        bundle = reply.readBundle(getClass().getClassLoader());
                        if(bundle.getBoolean("isPlaying"))
                        {
                            play.setText(R.string.pause);
                        }
                        else play.setText(R.string.play);
                        seekBar.setProgress(bundle.getInt("currentTime"));
                        seekBar.setMax(bundle.getInt("totalTime"));
                        Date currnetTime = new Date(bundle.getInt("currentTime"));
                        Date totalTime = new Date(bundle.getInt("totalTime"));
                        current.setText(simpleDateFormat.format(currnetTime));
                        total.setText(simpleDateFormat.format(totalTime));
                        break;
                }
            }
        };


//            try {
//                @SuppressLint("Recycle") Parcel data = Parcel.obtain();
//                @SuppressLint("Recycle") Parcel reply = Parcel.obtain();
//                mBinder.transact(100,data,reply,0);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        Log.i("playinggggggg",String.valueOf(bundle.getBoolean("isPlaying")));
//        if(bundle.getBoolean("isPlaying")) animator.start();

    }

    @SuppressLint("SimpleDateFormat")
    void initial(){
        play = findViewById(R.id.play);
        stop = findViewById(R.id.stop);
        quit = findViewById(R.id.quit);
        seekBar = findViewById(R.id.seekBar);
        current = findViewById(R.id.current_time);
        total = findViewById(R.id.total_time);
        state = findViewById(R.id.state);
        cover = findViewById(R.id.cover);
        bundle = new Bundle();
        simpleDateFormat = new SimpleDateFormat("mm:ss");
        animator = ObjectAnimator.ofFloat(cover,"rotation",0,90,180,270,360);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(20000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
    }

    void verifyStoragePermissions(Activity activity)
    {
        int permissiion = ActivityCompat.checkSelfPermission(activity,"android.permission.READ_EXTERNAL_STORAGE");
        if(permissiion != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(activity,new String[]{"android.permission.READ_EXTERNAL_STORAGE"},0);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }
}
