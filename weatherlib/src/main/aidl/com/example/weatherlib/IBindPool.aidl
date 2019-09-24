// IBindPool.aidl
package com.example.weatherlib;
//获取对应的binder
// Declare any non-default types here with import statements

interface IBindPool {
    IBinder searchBinder(int bindType);
}
