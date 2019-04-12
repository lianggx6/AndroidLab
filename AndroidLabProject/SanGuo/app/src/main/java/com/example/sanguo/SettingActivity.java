package com.example.sanguo;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class SettingActivity extends AppCompatActivity {

    ImageView search;
    ImageView collect;
    /*后台服务相关*/
    private IBinder mMusicBinder;
    private ServiceConnection mMusicServiceConnection;
    ImageButton musicb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initial();
//        startMusicService();
        search.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("PrivateResource")
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this,StartActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
                finish();
            }
        });
        collect.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("PrivateResource")
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this,CollectActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
                finish();
            }
        });
        musicb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                playOrPauseMusic();
            }
        });
    }

    void initial()
    {
        search = findViewById(R.id.search_image);
        collect = findViewById(R.id.list_image);
        musicb = findViewById(R.id.musicButton);
    }



//
//    /************************后台音乐相关**********************/
//    void startMusicService(){
//        /*开启音乐服务*/
//        mMusicServiceConnection = new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName name, IBinder service) {
//                mMusicBinder = service;
//                initMediaPlayer();
//                playOrPauseMusic();//默认连接成功后就开始播放音乐
//                Log.e("debug", "服务连接成功");
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName name) {
//                mMusicBinder = null;
//                Log.e("debug", "服务意外断开！（客户端解除绑定服务时，该方法不会被调用）");
//            }
//        };
//        Intent bindIntent = new Intent(this, MusicService.class);
//        startService(bindIntent);//开启服务
//        bindService(bindIntent, mMusicServiceConnection, BIND_AUTO_CREATE);//绑定服务
//    }
//
//    void initMediaPlayer() {
//        /*初始化MediaPlayer*/
//        Parcel inputData = Parcel.obtain();
//        Parcel expectedReply = Parcel.obtain();
//        try {
//            mMusicBinder.transact(MusicService.INIT_MEDIAPLAYER, inputData, expectedReply, 0);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
//    void playOrPauseMusic(){
//        /*发送播放音乐（或暂定音乐）的信息到后台服务*/
//        Parcel inputData = Parcel.obtain();
//        Parcel expectedReply = Parcel.obtain();
//        try {
//            mMusicBinder.transact(MusicService.PLAY_MUSIC, inputData, expectedReply, 0);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.e("debug", "\n退出MainAcitvity");
//        /*发送点击了quit按钮的信息到后台服务，提示后台服务释放资源*/
//        Parcel inputData = Parcel.obtain();
//        Parcel expectedReply = Parcel.obtain();
//        try {
//            mMusicBinder.transact(MusicService.QUIT_APP, inputData, expectedReply, 0);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//        /*取消绑定服务并停止服务*/
//        unbindService(mMusicServiceConnection);
//        mMusicServiceConnection = null;
//        Intent stopIntent = new Intent(this, MusicService.class);
//        stopService(stopIntent);
//    }
//
//    @Override
//    public void onBackPressed() {
//        //实现Home效果
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        startActivity(intent);
//    }
//    /************************后台音乐相关**********************/

}
