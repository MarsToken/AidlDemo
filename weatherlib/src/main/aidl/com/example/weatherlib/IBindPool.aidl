// IBindPool.aidl
package com.example.weatherlib;

// Declare any non-default types here with import statements

interface IBindPool {
    IBinder searchBinder(int bindType);
}
