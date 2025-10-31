package com.codyperry.library.system;

import com.codyperry.library.model.Book;
import com.codyperry.library.model.Library;

import java.util.List;
import java.util.Optional;

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
    public synchronized boolean returnBook(String isbn, String memberId) {
        return this.library.returnBook(isbn, memberId);
    }

    /**
     * Donate a book with the given ISBN to the library.
     *
     * @param isbn
     * @param name
     * @param author
     *
     * @return Whether successfully donated or not.
     */
    @Override
    public synchronized boolean donateBook(String isbn, String name, String author) {
        return this.library.donate(isbn, name, author);
    }

    /**
     * Leave the library network. This will return false if there are still checked out books for this member.
     *
     * @param memberId
     * @return Whether the member successfully left or not.
     */
    @Override
    public synchronized boolean leaveLibrary(String memberId) {
        return this.library.leave(memberId);
    }

    /**
     * Get all the available books at this point in time.
     *
     * @return A list of the books not currently checked out.
     */
    @Override
    public synchronized List<Book> browseCatalog() {
        return this.library.getCatalog();
    }

    /**
     * See all the currently checked out books.
     *
     * @return A list of the books currently checked out.
     */
    @Override
    public synchronized List<Book> browseCheckedOut() {
        return this.library.getCheckedOut();
    }

    /**
     * Get the `n` most active members in the library network. This will be the current most checked out books.
     *
     * @param n The number of top members to retrieve.
     *
     * @return A list of strings in the format (memberId, numBooks).
     */
    @Override
    public synchronized List<String> mostActiveMembers(int n) {
        return this.library.getActiveMembers(n);
    }

    /**
     * Fetch the member's current checked out books.
     *
     * @param memberId
     * @return A possibly empty list of checked out books for the member.
     */
    @Override
    public synchronized Optional<List<Book>> getCheckedOutBooks(String memberId) {
        return this.library.getMembersCheckedOutBooks(memberId);
    }
}
