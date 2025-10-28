package com.codyperry.library;

import com.codyperry.library.model.Library;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LibraryTests {
    @Test
    @Order(1)
    void testJoinLibrary() {
        final Library library = new Library();
        String memberId = library.join("A");

        assertEquals(memberId, "A1");
    }

    @Test
    @Order(2)
    void testJoinSubsequent() {
        final Library library = new Library();
        String memberId = library.join("A");

        assertEquals(memberId, "A1");

        String memberId2 = library.join("B");
        assertEquals(memberId2, "B2");
    }

    @Test
    @Order(3)
    void testBorrowNotMember() {
        final Library library = new Library();
        library.join("A");

        boolean borrowed = library.borrow("1111", "b2323");
        assertEquals(borrowed, false);
    }

    @Test
    @Order(4)
    void testBorrowNotInCatalog() {
        final Library library = new Library();
        library.join("A");

        boolean borrowed = library.borrow("1111", "A1");
        assertEquals(borrowed, false);
    }

    @Test
    @Order(5)
    void testBorrowTooManyBooks() {

    }

    @Test
    @Order(6)
    void testBorrowNoCopies() {

    }

    @Test
    @Order(7)
    void testBorrowHappyPath() {

    }
}
