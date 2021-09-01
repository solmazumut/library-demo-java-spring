package com.umutsolmaz.librarydemo.service;

import com.umutsolmaz.librarydemo.domain.Book;
import com.umutsolmaz.librarydemo.domain.User;

public interface UsersService {
    User findById(String id);
    boolean checkIfUserExists(String id);
    User addUser(User user);
    void deleteUser(String id);
    void borrowNewBookForUserWithId(String userId, String bookId);
    void returnBookToLibraryForUserWithId(String userId);
}
