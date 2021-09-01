package com.umutsolmaz.librarydemo.models.exception;

public class EntityWithSuchIdAlreadyExists extends RuntimeException{
    public EntityWithSuchIdAlreadyExists(String id) {
        super("There is another entity with same Id: " + id);
    }
}
