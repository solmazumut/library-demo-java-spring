package com.umutsolmaz.librarydemo.repository;

import com.umutsolmaz.librarydemo.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BooksRepository extends MongoRepository<Book, String> {
    public Optional<Book> findById(String id);
}
