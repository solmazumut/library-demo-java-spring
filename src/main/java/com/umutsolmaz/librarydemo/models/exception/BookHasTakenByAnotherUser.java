package com.umutsolmaz.librarydemo.models.exception;

public class BookHasTakenByAnotherUser extends RuntimeException{
    public BookHasTakenByAnotherUser(String id) {
        super("Someone else take the book with id: " + id);
    }
}
