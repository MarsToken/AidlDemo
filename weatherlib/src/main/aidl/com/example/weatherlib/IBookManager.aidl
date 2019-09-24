// IBookManager.aidl
package com.example.weatherlib;
import com.example.weatherlib.Book;
import com.example.weatherlib.IUpdateBookListener;
// Declare any non-default types here with import statements
//aidl交互方法
interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerUpdateListener(IUpdateBookListener listener);
    void unRegisterUpdateListener(IUpdateBookListener listener);
}
