package com.example.weatherlib;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

/**
 * Created by hp on 2019/9/23.
 */
public class BinderPool {
    private static final String TAG = "BinderPool";
    private static final int UPDATE_BOOK = 1;
    private Context mContext;
    private IBindPool mBinderPool;
    private static volatile BinderPool sInstance;
    private CountDownLatch mConnectBinderPoolCountDownLatch;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_BOOK:
                    Book book = (Book) msg.obj;
                    if (null != onBookUpdateListener) {
                        onBookUpdateListener.onBookUpdate(book);
                    }
                    //Log.e("tag", book.toString());
                    break;
            }
        }
    };

    private BinderPool(Context context) {
        mContext = context.getApplicationContext();
    }

    public static BinderPool getInstance(Context context) {
        if (sInstance == null) {
            synchronized (BinderPool.class) {
                if (sInstance == null) {
                    sInstance = new BinderPool(context);
                }
            }
        }
        return sInstance;
    }

    private int mServiceType;

    public synchronized BinderPool connectBinderPoolService(int type) {
        mServiceType = type;
        //mConnectBinderPoolCountDownLatch = new CountDownLatch(1);
        Intent intent = new Intent();
        intent.setClassName("com.example.weatherlib", "com.example.weatherlib.BookManagerService");
//        intent.setAction("com.example.weatherlib");
//        intent.setPackage("com.example.aidldemo");
        mContext.bindService(intent, mBinderPoolConnection, Context.BIND_AUTO_CREATE);
//        try {
//            mConnectBinderPoolCountDownLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return this;
    }

    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected=" + name);
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected=" + name + "service'name=" + service.toString());
            mBinderPool = IBindPool.Stub.asInterface(service);
            try {
                mBinderPool.asBinder().linkToDeath(mBinderPoolDeathRecipient, 0);
                IBinder bookBinder = searchBinder(mServiceType);
                IBookManager manager = BookManagerImpl.asInterface(bookBinder);
                try {
                    manager.registerUpdateListener(mUpdateBookListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            //mConnectBinderPoolCountDownLatch.countDown();
        }
    };

    private IBinder.DeathRecipient mBinderPoolDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e(TAG, "binder died.");
            mBinderPool.asBinder().unlinkToDeath(mBinderPoolDeathRecipient, 0);
            mBinderPool = null;
            connectBinderPoolService(mServiceType);
        }
    };

    private IBinder searchBinder(int binderCode) {
        IBinder binder = null;
        try {
            if (mBinderPool != null) {
                binder = mBinderPool.searchBinder(binderCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }

    private IUpdateBookListener mUpdateBookListener = new IUpdateBookListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(UPDATE_BOOK, newBook).sendToTarget();
        }
    };

    public void unBindService(int type) {
        IBinder bookBinder = searchBinder(type);
        IBookManager manager = BookManagerImpl.asInterface(bookBinder);
        if (bookBinder.isBinderAlive()) {
            try {
                manager.unRegisterUpdateListener(mUpdateBookListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mContext.unbindService(mBinderPoolConnection);
    }

    private void initBindPool(int type) {
        Log.e("tag", "initBindPool!");
        IBinder bookBinder = searchBinder(type);
        IBookManager manager = BookManagerImpl.asInterface(bookBinder);
        try {
            manager.registerUpdateListener(mUpdateBookListener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "visit bookManager");
    }

    private OnBookUpdateListener onBookUpdateListener;

    public void setBookUpdateListener(OnBookUpdateListener listener) {
        onBookUpdateListener = listener;
    }

    public interface OnBookUpdateListener {
        void onBookUpdate(Book book);
    }
}
