package com.umutsolmaz.librarydemo.service;


import com.umutsolmaz.librarydemo.domain.Book;
import com.umutsolmaz.librarydemo.domain.User;
import com.umutsolmaz.librarydemo.models.exception.EntityNotFoundException;
import com.umutsolmaz.librarydemo.models.exception.EntityWithSuchIdAlreadyExists;
import com.umutsolmaz.librarydemo.repository.BooksRepository;
import com.umutsolmaz.librarydemo.service.impl.BooksServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BooksServiceImplUnitTest {

    @MockBean
    private BooksRepository booksRepository;

    @Autowired
    private BooksServiceImpl booksServiceImpl;

    @Test
    @Order(1)
    public void findById() {
        //Given
        String testId = "testId";
        String testName = "testName";
        String testAuthor = "testAuthor";
        Mockito.when(booksRepository.findById(testId)).thenReturn(Optional.of(new Book(testId, testName, testAuthor)));

        //When
        Book book = booksServiceImpl.findById(testId);
        String bookName = book.getName();

        //Then
        assertEquals(testName, bookName);
    }

    @Test
    @Order(2)
    public void addBook() {
        //Given
        String testId = "testId";
        String testName = "testName";
        String testAuthor = "testAuthor";
        Book book = new Book(testId, testName, testAuthor);
        Mockito.when(booksRepository.findById(testId)).thenThrow(new EntityNotFoundException("test"));
        Mockito.when(booksRepository.save(book)).thenReturn(book);

        //When
        Book savedBook = booksServiceImpl.addBook(book);
        String bookName = savedBook.getName();

        //Then
        assertEquals(testName, bookName);
    }

    @Test
    @Order(3)
    public void addExistedBook() {
        //Given
        String testId = "testId";
        String testName = "testName";
        String testAuthor = "testAuthor";
        Book book = new Book(testId, testName, testAuthor);
        Mockito.when(booksRepository.findById(testId)).thenReturn(Optional.of(book));

        //When
        EntityWithSuchIdAlreadyExists exception = assertThrows(EntityWithSuchIdAlreadyExists.class, () ->
                booksServiceImpl.addBook(book)
        );

        //Then
        assertEquals("There is another entity with same Id: " + testId, exception.getMessage());
    }

    @Test
    @Order(4)
    public void userBorrowsNewBook() {
        //Given
        String testBookId = "testBookId";
        String testBookName = "testBookName";
        String testBookAuthor = "testBookAuthor";
        Book book = new Book(testBookId, testBookName, testBookAuthor);

        String testUserId = "testUserId";
        String testUserName = "testUserName";
        User user = new User(testUserId, testUserName);

        Mockito.when(booksRepository.findById(testBookId)).thenReturn(Optional.of(book));
        Mockito.when(booksRepository.save(book)).thenReturn(book);

        //When
        booksServiceImpl.giveBookToUser(user, testBookId);
        Book foundedBook = booksServiceImpl.findById(testBookId);

        //Then
        assertEquals(testUserName, foundedBook.getUserHasBook().getName());
    }

    @Test
    @Order(5)
    public void userReturnsBook() {
        //Given
        String testBookId = "testBookId";
        String testBookName = "testBookName";
        String testBookAuthor = "testBookAuthor";
        Book book = new Book(testBookId, testBookName, testBookAuthor);

        String testUserId = "testUserId";
        String testUserName = "testUserName";
        User user = new User(testUserId, testUserName);

        book.setUserHasBook(user);
        user.setTakenBook(book);

        Mockito.when(booksRepository.findById(testBookId)).thenReturn(Optional.of(book));
        Mockito.when(booksRepository.save(book)).thenReturn(book);

        //When
        booksServiceImpl.removeBookFromUser(user, testBookId);
        Book foundedBook = booksServiceImpl.findById(testBookId);

        //Then
        assertEquals(null, foundedBook.getUserHasBook());
    }


}
