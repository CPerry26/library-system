package com.codyperry.library.system;

import com.codyperry.library.model.Book;
import com.codyperry.library.model.Library;

import java.util.List;

public class LibrarySystemImpl implements LibrarySystem {
    private final Library library;

    public LibrarySystemImpl() {
        this.library = new Library();
    }

    /**
     * Join the library as a member.
     *
     * @param name The name of the new member.
     * @return The ID associated with the new member.
     */
    @Override
    public synchronized String joinLibrary(String name) {
        return this.library.join(name);
    }

    /**
     * Borrow a book from the library with the given ISBN. Members are allowed a max of 3 books checked out at a time.
     *
     * @param isbn
     * @param memberId
     * @return Whether the book was successfully borrowed or not.
     */
    @Override
    public synchronized boolean borrowBook(String isbn, String memberId) {
        return this.library.borrow(isbn, memberId);
    }

    /**
     * Return the book with the given ISBN.
     *
     * @param isbn
     * @param memberId
     */
    @Override
    public synchronized void returnBook(String isbn, String memberId) {

    }

    /**
     * Donate a book with the given ISBN to the library.
     *
     * @param isbn
     * @return Whether successfully donated or not.
     */
    @Override
    public synchronized boolean donateBook(String isbn) {
        return false;
    }

    /**
     * Leave the library network. This will return false if there are still checked out books for this member.
     *
     * @param memberId
     * @return Whether the member successfully left or not.
     */
    @Override
    public synchronized boolean leaveLibrary(String memberId) {
        return false;
    }

    /**
     * Get all the available books at this point in time.
     *
     * @return A list of the books not currently checked out.
     */
    @Override
    public synchronized List<Book> browseCatalog() {
        return null;
    }

    /**
     * Get the `n` most active members in the library network. This will be the current most checked out books.
     *
     * @param n The number of top members to retrieve.
     */
    @Override
    public synchronized void mostActiveMembers(int n) {

    }

    /**
     * Fetch the member's current checked out books.
     *
     * @param memberId
     * @return A possibly empty list of checked out books for the member.
     */
    @Override
    public synchronized List<Book> getCheckedOutBooks(String memberId) {
        return null;
    }
}
