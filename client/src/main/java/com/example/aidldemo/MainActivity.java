package com.example.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "client MainActivity";
    private IBookManager bookManager;
    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected");
            bookManager = IBookManager.Stub.asInterface(service);
            try {
                List<Book> list = bookManager.getBookList();
                Log.e(TAG, String.format("query book list,list type:%s", list.getClass().getCanonicalName()));
                Log.e(TAG, "query book list:" + list.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected=" + name);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();
        intent.setAction("com.example.aidldemo");
        intent.setPackage("com.example.aidldemo");
        bindService(intent, mConn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConn);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                try {
                    bookManager.addBook(new Book(3, "java"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                try {
                    List<Book> list = bookManager.getBookList();
                    Log.e(TAG, "query book list:" + list.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_delete:

                break;
        }

    }

}
