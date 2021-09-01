package com.umutsolmaz.librarydemo.models.exception;

public class UserDoesntHaveABook extends RuntimeException{

    public UserDoesntHaveABook(String userId) {
        super("The User with Id: " + userId +" has no book");
    }

    public UserDoesntHaveABook(String userId, String bookId) {
        super("The User with Id: " + userId +" has no book with id: " + bookId);
    }
}
