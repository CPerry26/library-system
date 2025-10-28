package com.codyperry.library.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {
    private final Map<String, List<Book>> catalog;
    private final Map<String, Member> members;
    private final Map<String, List<Book>> checkedOut;

    public Library() {
        this.catalog = new HashMap<>();
        this.members = new HashMap<>();
        this.checkedOut = new HashMap<>();
    }

    public String join(String name) {
        // The below should really have some form of deduplication logic, otherwise
        // people could just keep joining as a new member. In the interest of keeping
        // this simpler, I'm ignoring that for now.
        StringBuilder builder = new StringBuilder();
        builder.append(name);

        int memberCount = this.members.size() + 1;
        builder.append(memberCount);

        String memberId = builder.toString();
        Member newMember = new Member(name, memberId);
        this.members.put(memberId, newMember);

        return memberId;
    }

    public boolean borrow(String isbn, String memberId) {
        if (!this.members.containsKey(memberId) || !this.catalog.containsKey(isbn)) {
            return false;
        }

        List<Book> checkedOutBooks = this.checkedOut.getOrDefault(memberId, new ArrayList<>());
        if (checkedOutBooks.size() == 3) {
            return false;
        }

        List<Book> copies = this.catalog.get(isbn);
        if (copies.size() == 0) {
            return false;
        }

        // Just remove the first copy in the list, it doesn't matter since they're duplicates.
        Book book = copies.remove(0);
        checkedOutBooks.add(book);

        this.checkedOut.put(memberId, checkedOutBooks);
        this.catalog.put(isbn, copies);

        return true;
    }
}
