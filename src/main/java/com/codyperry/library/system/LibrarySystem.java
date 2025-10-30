package com.codyperry.library.system;

import com.codyperry.library.model.Book;

import java.util.List;
import java.util.Optional;

public interface LibrarySystem {
    /**
     * Join the library as a member.
     *
     * @param name The name of the new member.
     *
     * @return The ID associated with the new member.
     */
    String joinLibrary(String name);

    /**
     * Borrow a book from the library with the given ISBN. Members are only allowed a maximum of 3 books checked out
     * at a time.
     *
     * @param isbn
     * @param memberId
     *
     * @return Whether the book was successfully borrowed or not.
     */
    boolean borrowBook(String isbn, String memberId);

    /**
     * Return the book with the given ISBN.
     *
     * @param isbn
     * @param memberId
     *
     * @return Whether the book was successfully returned to the library.
     */
    boolean returnBook(String isbn, String memberId);

    /**
     * Donate a book with the given ISBN to the library.
     *
     * @param isbn
     * @param name
     * @param author
     *
     * @return Whether successfully donated or not.
     */
    boolean donateBook(String isbn, String name, String author);

    /**
     * Leave the library network. This will return false if there are still checked out books for this member.
     *
     * @param memberId
     *
     * @return Whether the member successfully left or not.
     */
    boolean leaveLibrary(String memberId);

    /**
     * Get all the available books at this point in time.
     *
     * @return A list of the books not currently checked out.
     */
    List<Book> browseCatalog();

    /**
     * See all the currently checked out books.
     *
     * @return A list of the books currently checked out.
     */
    List<Book> browseCheckedOut();

    /**
     * Get the `n` most active members in the library network. This will be the current most checked out books.
     *
     * @param n The number of top members to retrieve.
     */
    void mostActiveMembers(int n);

    /**
     * Fetch the member's current checked out books.
     *
     * @param memberId
     *
     * @return A possibly empty optional or a list of books.
     */
    Optional<List<Book>> getCheckedOutBooks(String memberId);
}
