package com.example.weatherlib;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/*
 *1.Messenger是对aidl的封装，用handler传递消息，使用简单： 一次只能处理一个请求，不存在并发情况（handler的原因）
 * 只适合传递信息，而不是调用方法
 * 2.对象序列化，反序列化之后，无法传递对象（即进程间无法传递同一个对象，只能copy）
 * */

/**
 * Created by hp on 2019/8/10.
 */
public class BookManagerService extends Service {
    private static final String TAG = "BookManagerService";
    private IBinder mBinder = new BindPoolImpl();

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        Log.e("tag", "onDestroy");
        super.onDestroy();
    }

}
