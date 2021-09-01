package com.umutsolmaz.librarydemo.models.exception;

public class UserHasAnotherBook extends RuntimeException{
    public UserHasAnotherBook(String userId, String bookId) {
        super("The User with Id: " + userId +" has book with id: " + bookId + " Before borrow new book return old one");
    }
}
