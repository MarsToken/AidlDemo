package com.example.weatherlib;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by hp on 2019/9/20.
 */
public class BindPoolImpl extends IBindPool.Stub {
    public BindPoolImpl() {
        super();
    }

    @Override
    public IBinder searchBinder(int bindType) throws RemoteException {
        IBinder binder = null;
        switch (bindType) {
            case Constant.BIND_BOOK:
                Log.e("BindPoolImpl", "Constant.BIND_BOOK");
                binder = new BookManagerImpl();
                break;
            case Constant.BIND_NOTICE:
                break;
            default:
                break;
        }
        return binder;
    }
}
