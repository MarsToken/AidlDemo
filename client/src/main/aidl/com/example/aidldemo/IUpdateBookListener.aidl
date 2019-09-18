// IUpdateBookListener.aidl
package com.example.aidldemo;
// Declare any non-default types here with import statements
import com.example.aidldemo.Book;
interface IUpdateBookListener {
    void onNewBookArrived(in Book newBook);
}
