package com.umutsolmaz.librarydemo.service.impl;

import com.umutsolmaz.librarydemo.domain.Book;
import com.umutsolmaz.librarydemo.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BooksService {

    @Autowired
    final BooksRepository booksRepository;

    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public Book findById(String id) {
        Optional<Book> optionalBook = booksRepository.findById(id);
        Book book = optionalBook.orElseThrow();
        return book;
    }
}
