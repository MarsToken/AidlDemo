// IUpdateBookListener.aidl
package com.example.weatherlib;
// Declare any non-default types here with import statements
//接口监听
import com.example.weatherlib.Book;
interface IUpdateBookListener {
    void onNewBookArrived(in Book newBook);
}
