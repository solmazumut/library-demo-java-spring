package com.umutsolmaz.librarydemo.domain;

import com.mongodb.lang.Nullable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "books")
public class Book {
    @Id
    private String id;
    @Field
    private String name;
    @Field
    private String author;
    @Field
    private User userHasBook;

    public Book() {
    }

    public Book(String id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.userHasBook = null;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public User getUserHasBook() {
        return userHasBook;
    }

    public void setUserHasBook(User userHasBook) {
        this.userHasBook = userHasBook;
    }

}
