package com.umutsolmaz.librarydemo.service;

import com.umutsolmaz.librarydemo.domain.Book;
import com.umutsolmaz.librarydemo.domain.User;
import com.umutsolmaz.librarydemo.models.exception.EntityNotFoundException;
import com.umutsolmaz.librarydemo.repository.UsersRepository;
import com.umutsolmaz.librarydemo.service.impl.BooksServiceImpl;
import com.umutsolmaz.librarydemo.service.impl.UsersServiceImpl;
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
public class UsersServiceImplUnitTest {

    @MockBean
    private UsersRepository usersRepository;

    @MockBean
    private BooksServiceImpl booksService;

    @Autowired
    private UsersServiceImpl usersService;

    @Test
    @Order(1)
    public void findById() {
        //Given
        String testId = "testId";
        String testName = "testName";
        Mockito.when(usersRepository.findById(testId)).thenReturn(Optional.of(new User(testId, testName)));

        //When
        User user = usersService.findById(testId);
        String userName = user.getName();

        //Then
        assertEquals(testName, userName);
    }

    @Test
    @Order(2)
    public void addUser() {
        //Given
        String testId = "testId";
        String testName = "testName";
        User user = new User(testId, testName);
        Mockito.when(usersRepository.findById(testId)).thenThrow(new EntityNotFoundException("test"));
        Mockito.when(usersRepository.save(user)).thenReturn(user);

        //When
        User savedUser = usersService.addUser(user);
        String userName = savedUser.getName();

        //Then
        assertEquals(testName, userName);
    }

    @Test
    @Order(3)
    public void userBorrowsNewBook() {
        //Given
        String testUserId = "testUserId";
        String testUserName = "testUserName";
        User user = new User(testUserId, testUserName);

        String testBookId = "testBookId";
        String testBookName = "testBookName";
        String testBookAuthor = "testBookAuthor";
        Book userAddedBook = new Book(testBookId, testBookName, testBookAuthor);
        userAddedBook.setUserHasBook(user);

        Mockito.when(usersRepository.findById(testUserId)).thenReturn(Optional.of(user));
        Mockito.when(booksService.giveBookToUser(user, testBookId)).thenReturn(userAddedBook);
        Mockito.when(usersRepository.save(user)).thenReturn(user);

        //When
        usersService.borrowNewBookForUserWithId(testUserId, testBookId);
        User foundedUser = usersService.findById(testUserId);

        //Then
        assertEquals(testBookName, foundedUser.getTakenBook().getName());
    }

    @Test
    @Order(4)
    public void userReturnsBook() {
        //Given
        String testUserId = "testUserId";
        String testUserName = "testUserName";
        User user = new User(testUserId, testUserName);

        String testBookId = "testBookId";
        String testBookName = "testBookName";
        String testBookAuthor = "testBookAuthor";
        Book book = new Book(testBookId, testBookName, testBookAuthor);
        Book freeBook = new Book(testBookId, testBookName, testBookAuthor);

        book.setUserHasBook(user);
        user.setTakenBook(book);

        Mockito.when(usersRepository.findById(testUserId)).thenReturn(Optional.of(user));
        Mockito.when(booksService.removeBookFromUser(user, testBookId)).thenReturn(freeBook);
        Mockito.when(usersRepository.save(user)).thenReturn(user);

        //When
        usersService.returnBookToLibraryForUserWithId(testUserId);
        User foundedUser = usersService.findById(testUserId);

        //Then
        assertEquals(null, foundedUser.getTakenBook());
    }

}
