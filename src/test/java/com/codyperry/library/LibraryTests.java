package com.codyperry.library;

import com.codyperry.library.model.Library;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

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
}
