package com.umutsolmaz.librarydemo.controller;

import com.umutsolmaz.librarydemo.domain.Book;
import com.umutsolmaz.librarydemo.repository.BooksRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/books")
public class BooksController {
    @Autowired
    private BooksRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Book> getAllBooks() {
        return repository.findAll();
    }
/*
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Book getBookById(@PathVariable("id") String id) {
        return repository.findOne(id);
    }
*/
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateBookById(@PathVariable("id") ObjectId id, @Valid @RequestBody Book book) {
        book.setId(id.toString());
        repository.save(book);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Book addNewCar(@Valid @RequestBody Book book) {
        book.setId(ObjectId.get().toString());
        return repository.save(book);
    }
}
