package com.example.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
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
    private static final int UPDATE_BOOK = 1;
    //支持并发读写
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    private AtomicBoolean mServiceIsDestroy = new AtomicBoolean(false);
    //保证进程间对象一致性
    private RemoteCallbackList<IUpdateBookListener> mUpdateList = new RemoteCallbackList<>();
    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            Log.e(TAG, "mBinder getBookList=" + mBookList.size());
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.e(TAG, "addBook add=" + book.toString());
            mBookList.add(book);
        }

        @Override
        public void registerUpdateListener(IUpdateBookListener listener) throws RemoteException {
            mUpdateList.register(listener);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Log.e("registerListener", "listener's size=" + mUpdateList.getRegisteredCallbackCount());
            }
        }

        @Override
        public void unRegisterUpdateListener(IUpdateBookListener listener) throws RemoteException {
            mUpdateList.unregister(listener);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Log.e("unRegisterListener", "listener's size=" + mUpdateList.getRegisteredCallbackCount());
            }
        }
    };

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
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "IOS"));
        mHandler.sendEmptyMessageDelayed(UPDATE_BOOK, 3000);
        Log.e(TAG, "onCreate book's size=" + mBookList.size());
    }

    @Override
    public void onDestroy() {
        Log.e("tag", "onDestroy");
        mServiceIsDestroy.set(true);
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    private int count = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_BOOK:
                    try {
                        onBookArrived(new Book(count, "java"));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    if (count == 3) {
                        //killSelf();
                        count = 0;
                    }
                    count++;
                    mHandler.sendEmptyMessageDelayed(UPDATE_BOOK, 3000);
                    break;
            }
        }
    };

    private void onBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
        Log.e("BookManager", "service add book success!");
        final int count = mUpdateList.beginBroadcast();
        for (int i = 0; i < count; i++) {
            IUpdateBookListener listener = mUpdateList.getBroadcastItem(i);
            if (null != listener) {
                listener.onNewBookArrived(book);
            }
        }
        mUpdateList.finishBroadcast();
    }

    private void killSelf() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
