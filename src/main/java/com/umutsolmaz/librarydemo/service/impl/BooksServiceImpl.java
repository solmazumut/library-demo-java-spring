package com.umutsolmaz.librarydemo.service.impl;

import com.umutsolmaz.librarydemo.domain.Book;
import com.umutsolmaz.librarydemo.domain.User;
import com.umutsolmaz.librarydemo.models.exception.BookHasTakenByAnotherUser;
import com.umutsolmaz.librarydemo.models.exception.EntityNotFoundException;
import com.umutsolmaz.librarydemo.models.exception.EntityWithSuchIdAlreadyExists;
import com.umutsolmaz.librarydemo.models.exception.UserDoesntHaveABook;
import com.umutsolmaz.librarydemo.repository.BooksRepository;
import com.umutsolmaz.librarydemo.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BooksServiceImpl implements BooksService {

    @Autowired
    final BooksRepository booksRepository;

    public BooksServiceImpl(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public Book findById(String id) {
        Optional<Book> optionalBook = booksRepository.findById(id);
        Book book = optionalBook.orElseThrow(() -> new EntityNotFoundException("Book does not found with id: " + id));
        return book;
    }

    @Override
    public boolean checkIfBookExists(String id) {
        boolean result = true;
        try {
            findById(id);
        } catch (EntityNotFoundException e) {
            result = false;
        }
        return result;
    }

    @Override
    public Book addBook(Book book) {
        String bookId = book.getId();
        boolean doesBookExist = checkIfBookExists(bookId);
        if(!doesBookExist) {
            return booksRepository.save(book);
        } else {
            throw new EntityWithSuchIdAlreadyExists(bookId);
        }
    }

    @Override
    public void deleteBook(String id) {
        booksRepository.deleteById(id);
    }

    @Override
    public Book giveBookToUser(User user, String bookId) {
        Book book = findById(bookId);
        User aUserAlreadyHasBook = book.getUserHasBook();
        if(aUserAlreadyHasBook != null) {
            throw new BookHasTakenByAnotherUser(bookId);
        }
        book.setUserHasBook(user);
        return booksRepository.save(book);
    }

    @Override
    public Book removeBookFromUser(User user, String bookId) {
        Book book = findById(bookId);
        String userId = user.getId();
        User aUserAlreadyHasBook = book.getUserHasBook();
        if(aUserAlreadyHasBook == null
                || !aUserAlreadyHasBook.getId().equals(userId)) {
            throw new UserDoesntHaveABook(userId,bookId);
        }
        book.setUserHasBook(null);
        return booksRepository.save(book);
    }



}
