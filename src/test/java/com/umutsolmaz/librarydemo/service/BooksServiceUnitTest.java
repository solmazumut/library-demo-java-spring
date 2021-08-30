package com.umutsolmaz.librarydemo.service;


import com.umutsolmaz.librarydemo.domain.Book;
import com.umutsolmaz.librarydemo.repository.BooksRepository;
import com.umutsolmaz.librarydemo.service.impl.BooksService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BooksServiceUnitTest {

    @MockBean
    private BooksRepository booksRepository;

    @Autowired
    private BooksService booksService;

    @Test
    @Order(1)
    public void findById() {
        //Given
        String testId = "testId";
        String testName = "testName";
        String testAuthor = "testAuthor";
        Mockito.when(booksRepository.findById(testId)).thenReturn(Optional.of(new Book(testId, testName, testAuthor)));

        //When
        Book book = booksService.findById(testId);
        String bookName = book.getName();

        //Then
        assertEquals(testName, bookName);
    }

}
