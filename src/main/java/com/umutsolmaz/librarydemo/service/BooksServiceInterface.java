package com.umutsolmaz.librarydemo.service;

import com.umutsolmaz.librarydemo.domain.Book;

public interface BooksServiceInterface {
    Book findById(String testId);
}
