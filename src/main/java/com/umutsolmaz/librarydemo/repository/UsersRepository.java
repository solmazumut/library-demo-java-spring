package com.umutsolmaz.librarydemo.repository;

import com.umutsolmaz.librarydemo.domain.Book;
import com.umutsolmaz.librarydemo.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends MongoRepository<User, String> {
}
