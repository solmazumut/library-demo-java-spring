package com.umutsolmaz.librarydemo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
public class User {
    @Id
    String id;
    @Field
    String name;
    @Field
    Book takenBook;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.takenBook = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Book getTakenBook() {
        return takenBook;
    }

    public void setTakenBook(Book takenBook) {
        this.takenBook = takenBook;
    }
}
