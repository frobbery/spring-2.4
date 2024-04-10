package com.example.spring24.services.book;




import com.example.spring24.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    long saveBook(Book book);

    Optional<Book> getBookById(long id);

    List<Book> getAllBooks();

    void updateBookById(Book book);

    void deleteBookById(long id);
}
