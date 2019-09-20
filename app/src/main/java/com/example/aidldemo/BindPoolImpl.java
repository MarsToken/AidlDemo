package com.example.aidldemo;

import android.os.IBinder;
import android.os.RemoteException;

/**
 * Created by hp on 2019/9/20.
 */
public class BindPoolImpl extends IBindPool.Stub {

    @Override
    public IBinder searchBinder(int bindType) throws RemoteException {
        IBinder binder = null;
        switch (bindType) {
            case Constant.BIND_BOOK:
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
