package com.example.weatherlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "client MainActivity";
//    private static final int UPDATE_BOOK = 1;
//    private IBookManager bookManager;
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case UPDATE_BOOK:
//                    Book book = (Book) msg.obj;
//                    Log.e("tag", book.toString());
//                    break;
//            }
//        }
//    };
//    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
//        @Override
//        public void binderDied() {
//            if (bookManager == null) {
//                return;
//            }
//            bookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
//            bookManager = null;
//            //todo 重新绑定远程Service
//            bindServices();
//        }
//    };
//    private IUpdateBookListener mUpdateBookListener = new IUpdateBookListener.Stub() {
//        @Override
//        public void onNewBookArrived(Book newBook) throws RemoteException {
//            mHandler.obtainMessage(UPDATE_BOOK, newBook).sendToTarget();
//        }
//    };
//    private ServiceConnection mConn = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            Log.e(TAG, "onServiceConnected");
//            bookManager = IBookManager.Stub.asInterface(service);
//            try {
//                service.linkToDeath(mDeathRecipient, 0);
//                List<Book> list = bookManager.getBookList();
//                Log.e(TAG, String.format("query book list,list type:%s", list.getClass().getCanonicalName()));
//                Log.e(TAG, "query book list:" + list.toString());
//                bookManager.registerUpdateListener(mUpdateBookListener);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            Log.e(TAG, "onServiceDisconnected=" + name);
//            bookManager = null;
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //bindServices();
        BinderPool binderPool = BinderPool.getInstance(this);
        binderPool.initBindPool(Constant.BIND_BOOK);
        Log.e("tag", "onCreate finished!");
    }

//    private void bindServices() {//com.example.aidldemo.ACCESS_BOOK_SERVICE
////        int checkResult = checkCallingOrSelfPermission("com.example.aidldemo.ACCESS_BOOK_SERVICE");
////        if (checkResult == PackageManager.PERMISSION_DENIED) {
////            Log.e(TAG, "onBind failed,permission deny!");
////            return;
////        }
//        Intent intent = new Intent();
//        intent.setClassName("com.example.aidldemo", "com.example.aidldemo.BookManagerService");
////        intent.setAction("com.example.aidldemo");
////        intent.setPackage("com.example.aidldemo");
//        bindService(intent, mConn, BIND_AUTO_CREATE);
//    }
//
//    @Override
//    protected void onDestroy() {
//        if (null != bookManager && bookManager.asBinder().isBinderAlive()) {
//            try {
//                bookManager.unRegisterUpdateListener(mUpdateBookListener);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }
//        unbindService(mConn);
//        super.onDestroy();
//    }
//
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn_add:
//                try {
//                    bookManager.addBook(new Book(3, "java"));
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    List<Book> list = bookManager.getBookList();
//                    Log.e(TAG, "query book list:" + list.toString());
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//                break;
//            case R.id.btn_delete:
//                break;
//        }
//    }

}
