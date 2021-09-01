package com.umutsolmaz.librarydemo.service;

import com.umutsolmaz.librarydemo.domain.Book;
import com.umutsolmaz.librarydemo.domain.User;

public interface BooksService {
    Book findById(String id);
    boolean checkIfBookExists(String id);
    Book addBook(Book book);
    void deleteBook(String id);
    Book giveBookToUser(User user, String bookId);
    Book removeBookFromUser(User user, String bookId);
}
