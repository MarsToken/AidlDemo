package com.example.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by hp on 2019/8/10.
 */
public class BookManagerService extends Service {
    private static final String TAG = "BookManagerService";
    //支持并发读写
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
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
    };

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "IBinder");
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "IOS"));
        Log.e(TAG, "onCreate size=" + mBookList.size());
    }
}
