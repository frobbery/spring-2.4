package com.example.spring24.repository;

import com.example.spring24.domain.Book;
import com.example.spring24.domain.Genre;

import java.util.Optional;

public interface BookGenreRepository {

    void addGenreToBook(Optional<Book> bookOptional, Genre genre);

    void deleteBookGenreLinks(Optional<Book> bookOptional);

    void deleteGenreFromBook(Optional<Book> bookOptional, Genre genre);
}
