package com.codyperry.library;

import com.codyperry.library.model.Book;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ActiveMemberComparator implements Comparator<Map.Entry<String, List<Book>>> {
    @Override
    public int compare(Map.Entry<String, List<Book>> entry1, Map.Entry<String, List<Book>> entry2) {
        return Integer.compare(entry2.getValue().size(), entry1.getValue().size());
    }
}
