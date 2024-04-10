package com.example.spring24.repository;

import com.example.spring24.domain.Book;
import com.example.spring24.domain.Genre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookGenreRepositoryJpa implements BookGenreRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void addGenreToBook(Optional<Book> bookOptional, Genre genre) {
        if (bookOptional.isEmpty()) {
            throw new IllegalArgumentException("There is no book with such id");
        }
        Book book = bookOptional.get();
        book.getGenres().add(genre);
        entityManager.merge(book);
    }

    @Override
    public void deleteBookGenreLinks(Optional<Book> bookOptional) {
        if (bookOptional.isEmpty()) {
            throw new IllegalArgumentException("There is no book with such id");
        }
        Book book = bookOptional.get();
        book.setGenres(new ArrayList<>());
        entityManager.merge(book);
    }

    @Override
    public void deleteGenreFromBook(Optional<Book> bookOptional, Genre genre) {
        if (bookOptional.isEmpty()) {
            throw new IllegalArgumentException("There is no book with such id");
        }
        Book book = bookOptional.get();
        book.getGenres().remove(genre);
        entityManager.merge(book);
    }
}
