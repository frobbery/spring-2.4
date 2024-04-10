package com.example.spring24.repository;

import com.example.spring24.config.YamlPropertySourceFactory;
import com.example.spring24.domain.Author;
import com.example.spring24.domain.Book;
import com.example.spring24.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с книгами должно:")
@DataJpaTest
@Import(BookGenreRepositoryJpa.class)
@TestPropertySource(value = "/application-test.yml", factory = YamlPropertySourceFactory.class)
@Sql(value = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class BookGenreRepositoryJpaTest {

    @Autowired
    private BookGenreRepositoryJpa sut;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Добавлять жанр к книге")
    void shouldAddGenreToBook() {
        //given
        var newGenre = Genre.builder()
                .id(2L)
                .genreName("Romance")
                .build();
        var expectedBook = Book.builder()
                .id(1L)
                .name("Regular adventure novel")
                .author(Author.builder()
                        .id(1L)
                        .fullName("Pushkin")
                        .build())
                .genres(List.of(Genre.builder()
                                .id(1L)
                                .genreName("Adventure")
                                .build(),
                        newGenre))
                .build();

        //when
        sut.addGenreToBook(bookRepository.findById(expectedBook.getId()), newGenre);

        //then
        assertThat(bookRepository.findById(expectedBook.getId()))
                .isPresent()
                .usingRecursiveComparison()
                .ignoringFields("value.comments")
                .isEqualTo(Optional.of(expectedBook));
    }

    @Test
    @DisplayName("Удалять жанры книги")
    void shouldDeleteGenresFromBook() {
        //given
        var expectedBook = Book.builder()
                .id(1L)
                .name("Regular adventure novel")
                .author(Author.builder()
                        .id(1L)
                        .fullName("Pushkin")
                        .build())
                .genres(List.of())
                .build();

        //when
        sut.deleteBookGenreLinks(bookRepository.findById(expectedBook.getId()));

        //then
        assertThat(bookRepository.findById(expectedBook.getId()))
                .isPresent()
                .usingRecursiveComparison()
                .ignoringFields("value.comments")
                .isEqualTo(Optional.of(expectedBook));
    }

    @Test
    @DisplayName("Удалять жанр книги")
    void shouldDeleteGenreFromBook() {
        //given
        var genreToBeDeleted = Genre.builder()
                .id(1L)
                .genreName("Adventure")
                .build();
        var expectedBook = Book.builder()
                .id(1L)
                .name("Regular adventure novel")
                .author(Author.builder()
                        .id(1L)
                        .fullName("Pushkin")
                        .build())
                .genres(List.of())
                .build();

        //when
        sut.deleteGenreFromBook(bookRepository.findById(expectedBook.getId()), genreToBeDeleted);

        //then
        assertThat(bookRepository.findById(expectedBook.getId()))
                .isPresent()
                .usingRecursiveComparison()
                .ignoringFields("value.comments")
                .isEqualTo(Optional.of(expectedBook));
    }
}