package com.umutsolmaz.librarydemo.controller;

import com.umutsolmaz.librarydemo.domain.Book;
import com.umutsolmaz.librarydemo.domain.User;
import com.umutsolmaz.librarydemo.repository.BooksRepository;
import com.umutsolmaz.librarydemo.repository.UsersRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UsersController {
    @Autowired
    private UsersRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return repository.findAll();
    }


    @RequestMapping(value = "/", method = RequestMethod.POST)
    public User addNewUser(@Valid @RequestBody User user) {
        user.setId(ObjectId.get().toString());
        return repository.save(user);
    }
}
