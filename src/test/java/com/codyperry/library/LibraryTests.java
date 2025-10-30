package com.codyperry.library;

import com.codyperry.library.model.Book;
import com.codyperry.library.model.Library;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LibraryTests {
    private final String isbn = "1111";
    private final String name = "Test";
    private final String author = "Author";
    private final String memberId = "A1";

    @Test
    @Order(1)
    void testJoinLibrary() {
        final Library library = new Library();
        String memberId = library.join("A");

        assertEquals(memberId, memberId);
    }

    @Test
    @Order(2)
    void testJoinSubsequent() {
        final Library library = new Library();
        String memberId = library.join("A");

        assertEquals(memberId, memberId);

        String memberId2 = library.join("B");
        assertEquals(memberId2, "B2");
    }

    @Test
    @Order(3)
    void testBorrowNotMember() {
        final Library library = new Library();
        library.join("A");

        boolean borrowed = library.borrow(isbn, "b2323");
        assertEquals(borrowed, false);
    }

    @Test
    @Order(4)
    void testBorrowNotInCatalog() {
        final Library library = new Library();
        library.join("A");

        boolean borrowed = library.borrow(isbn, memberId);
        assertEquals(borrowed, false);
    }

    @Test
    @Order(5)
    void testBorrowTooManyBooks() {
        final Library library = new Library();
        library.join("A");

        library.donate(isbn, name, author);
        library.donate(isbn, name, author);
        library.donate(isbn, name, author);
        library.donate(isbn, name, author);

        library.borrow(isbn, memberId);
        library.borrow(isbn, memberId);
        library.borrow(isbn, memberId);
        boolean borrowed = library.borrow(isbn, memberId);
        assertEquals(borrowed, false);
    }

    @Test
    @Order(6)
    void testBorrowNoCopies() {
        final Library library = new Library();
        library.join("A");

        library.donate(isbn, name, author);

        library.borrow(isbn, memberId);
        boolean borrowed = library.borrow(isbn, memberId);
        assertEquals(borrowed, false);
    }

    @Test
    @Order(7)
    void testBorrowHappyPath() {
        final Library library = new Library();
        library.join("A");

        library.donate(isbn, name, author);

        boolean borrowed = library.borrow(isbn, memberId);
        assertEquals(borrowed, true);
    }

    @Test
    @Order(8)
    void testReturnBookNotMember() {
        final Library library = new Library();
        library.join("A");

        boolean returned = library.returnBook(isbn, "B1");
        assertEquals(returned, false);
    }

    @Test
    @Order(9)
    void testReturnBookNotInCatalog() {
        final Library library = new Library();
        library.join("A");

        library.donate(isbn, name, author);

        boolean returned = library.returnBook("1112", memberId);
        assertEquals(returned, false);
    }

    @Test
    @Order(10)
    void testReturnBookNotCheckedOut() {
        final Library library = new Library();
        library.join("A");

        library.donate(isbn, name, author);

        boolean returned = library.returnBook(isbn, memberId);
        assertEquals(returned, false);
    }

    @Test
    @Order(11)
    void testReturnHappyPath() {
        final Library library = new Library();
        library.join("A");

        library.donate(isbn, name, author);

        boolean borrowed = library.borrow(isbn, memberId);
        assertEquals(borrowed, true);

        boolean returned = library.returnBook(isbn, memberId);
        assertEquals(returned, true);
    }

    @Test
    @Order(12)
    void testDonateBook() {
        final Library library = new Library();

        boolean donated = library.donate(isbn, name, author);
        assertEquals(donated, true);
    }

    @Test
    @Order(13)
    void testLeavingNotMember() {
        final Library library = new Library();

        boolean left = library.leave(memberId);
        assertEquals(left, false);
    }

    @Test
    @Order(14)
    void testLeavingCheckedOutBooks() {
        final Library library = new Library();
        library.join("A");

        library.donate(isbn, name, author);
        library.borrow(isbn, memberId);

        boolean left = library.leave(memberId);
        assertEquals(left, false);
    }

    @Test
    @Order(15)
    void testLeavingHappyPath() {
        final Library library = new Library();
        library.join("A");

        boolean left = library.leave(memberId);
        assertEquals(left, true);
    }

    @Test
    @Order(16)
    void testGetCheckedOutMemberBooksNotMember() {
        final Library library = new Library();

        Optional<List<Book>> books = library.getMembersCheckedOutBooks(memberId);
        assertEquals(books.isEmpty(), true);
        assertEquals(books.isPresent(), false);
    }

    @Test
    @Order(17)
    void testGetCheckedOutMemberBooksNoBooks() {
        final Library library = new Library();
        library.join("A");

        Optional<List<Book>> books = library.getMembersCheckedOutBooks(memberId);
        assertEquals(books.isEmpty(), true);
        assertEquals(books.isPresent(), false);
    }

    @Test
    @Order(18)
    void testGetCheckedOutMemberBooksHappyPath() {
        final Library library = new Library();
        library.join("A");

        library.donate(isbn, name, author);
        library.borrow(isbn, memberId);

        Optional<List<Book>> books = library.getMembersCheckedOutBooks(memberId);
        assertEquals(books.isEmpty(), false);
        assertEquals(books.isPresent(), true);
        assertEquals(books.get().size(), 1);
        assertEquals(books.get().get(0).isbn().equals(isbn), true);
    }

    @Test
    @Order(19)
    void testGetEmptyCatalog() {
        final Library library = new Library();

        List<Book> books = library.getCatalog();
        assertEquals(books.isEmpty(), true);
    }

    @Test
    @Order(20)
    void testGetCatalog() {
        final Library library = new Library();

        library.donate(isbn, name, author);
        library.donate("1112", "Cool Book", "Cool Author");

        List<Book> books = library.getCatalog();
        assertEquals(books.isEmpty(), false);
        assertEquals(books.size(), 2);
    }

    @Test
    @Order(21)
    void testGetEmptyCheckedOut() {
        final Library library = new Library();

        List<Book> books = library.getCheckedOut();
        assertEquals(books.isEmpty(), true);
    }

    @Test
    @Order(22)
    void testGetCheckedOut() {
        final Library library = new Library();
        library.join("A");

        library.donate(isbn, name, author);
        library.donate("1112", "Cool Book", "Cool Author");

        library.borrow(isbn, memberId);
        library.borrow("1112", memberId);

        List<Book> books = library.getCheckedOut();
        assertEquals(books.isEmpty(), false);
        assertEquals(books.size(), 2);
    }
}
