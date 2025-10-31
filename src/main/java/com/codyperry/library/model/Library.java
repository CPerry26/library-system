package com.codyperry.library.model;

import com.codyperry.library.ActiveMemberComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public boolean returnBook(String isbn, String memberId) {
        if (!this.members.containsKey(memberId) || !this.catalog.containsKey(isbn)) {
            return false;
        }

        // Get the member's checked out books. If they don't have the book checked out, return false.
        List<Book> memberCheckedOut = this.checkedOut.getOrDefault(memberId, new ArrayList<>());

        Optional<Book> checkedOutBook = memberCheckedOut.stream().filter((book -> book.isbn().equals(isbn))).findAny();
        if (!checkedOutBook.isPresent()) {
            return false;
        }

        // Remove the checked out book and update the member's current entries.
        memberCheckedOut.remove(checkedOutBook.get());
        this.checkedOut.put(memberId, memberCheckedOut);

        // Add the book back to the catalog.
        List<Book> catalogBooks = this.catalog.getOrDefault(isbn, new ArrayList<>());
        catalogBooks.add(checkedOutBook.get());
        this.catalog.put(isbn, catalogBooks);

        return true;
    }

    public boolean donate(String isbn, String name, String author) {
        List<Book> catalogEntry = this.catalog.getOrDefault(isbn, new ArrayList<>());
        catalogEntry.add(new Book(isbn, name, author));

        this.catalog.put(isbn, catalogEntry);

        return true;
    }

    public boolean leave(String memberId) {
        if (!this.members.containsKey(memberId) || this.checkedOut.containsKey(memberId)) {
            return false;
        }

        this.members.remove(memberId);

        return true;
    }

    public List<Book> getCatalog() {
        return this.catalog.values().stream().flatMap(List::stream).collect(Collectors.toList());
    }

    public List<Book> getCheckedOut() {
        return this.checkedOut.values().stream().flatMap(List::stream).collect(Collectors.toList());
    }

    public Optional<List<Book>> getMembersCheckedOutBooks(String memberId) {
        if (!this.members.containsKey(memberId) || !this.checkedOut.containsKey(memberId)) {
            return Optional.empty();
        }

        return Optional.of(this.checkedOut.get(memberId));
    }

    public List<String> getActiveMembers(int n) {
        List<String> activeMembers = new ArrayList<>();

        Map<String, List<Book>> sortedBooks = this.checkedOut.entrySet().stream().sorted(new ActiveMemberComparator())
                .limit(n).collect(
                        Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue,
                                LinkedHashMap::new
                        ));

        for (Map.Entry<String, List<Book>> entry : sortedBooks.entrySet()) {
            activeMembers.add("(" + entry.getKey() + ", " + entry.getValue().size() + ")");
        }

        return activeMembers;
    }
}
