// IBookManager.aidl
package com.example.aidldemo;
import com.example.aidldemo.Book;
// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}