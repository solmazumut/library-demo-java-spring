package com.umutsolmaz.librarydemo.service.impl;

import com.umutsolmaz.librarydemo.domain.Book;
import com.umutsolmaz.librarydemo.domain.User;
import com.umutsolmaz.librarydemo.models.exception.EntityNotFoundException;
import com.umutsolmaz.librarydemo.models.exception.EntityWithSuchIdAlreadyExists;
import com.umutsolmaz.librarydemo.models.exception.UserDoesntHaveABook;
import com.umutsolmaz.librarydemo.models.exception.UserHasAnotherBook;
import com.umutsolmaz.librarydemo.repository.UsersRepository;
import com.umutsolmaz.librarydemo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    final UsersRepository usersRepository;

    @Autowired
    final BooksServiceImpl booksService;

    public UsersServiceImpl(UsersRepository usersRepository, BooksServiceImpl booksService) {
        this.usersRepository = usersRepository;
        this.booksService = booksService;
    }

    @Override
    public User findById(String id) {
        Optional<User> optionalUser = usersRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("User does not found with id: " + id));
        return user;
    }

    @Override
    public boolean checkIfUserExists(String id) {
        boolean result = true;
        try {
            findById(id);
        } catch (EntityNotFoundException e) {
            result = false;
        }
        return result;
    }

    @Override
    public User addUser(User user) {
        String userId = user.getId();
        boolean doesUserExist = checkIfUserExists(userId);
        if(!doesUserExist) {
            return usersRepository.save(user);
        } else {
            throw new EntityWithSuchIdAlreadyExists(userId);
        }
    }

    @Override
    public void deleteUser(String id) {
        usersRepository.deleteById(id);
    }

    @Override
    public void borrowNewBookForUserWithId(String userId, String bookId) {
        User user = findById(userId);
        Book bookBorrowedByUser = user.getTakenBook();
        if(bookBorrowedByUser != null) {
            throw new UserHasAnotherBook(userId, bookBorrowedByUser.getId());
        }
        Book book = booksService.giveBookToUser(user, bookId);
        user.setTakenBook(book);
        usersRepository.save(user);
    }

    @Override
    public void returnBookToLibraryForUserWithId(String userId) throws RuntimeException {
        User user = findById(userId);
        Book bookBorrowedByUser = user.getTakenBook();
        if(bookBorrowedByUser == null) {
            throw new UserDoesntHaveABook(userId);
        }
        booksService.removeBookFromUser(user, bookBorrowedByUser.getId());
        user.setTakenBook(null);
        usersRepository.save(user);
    }
}
