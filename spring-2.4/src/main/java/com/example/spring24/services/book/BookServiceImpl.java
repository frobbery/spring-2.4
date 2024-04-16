package com.example.spring24.services.book;


import com.example.spring24.domain.Author;
import com.example.spring24.domain.Book;
import com.example.spring24.domain.Genre;
import com.example.spring24.repository.BookRepository;
import com.example.spring24.services.author.AuthorService;
import com.example.spring24.services.genre.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final GenreService genreService;

    @Override
    @Transactional
    public long saveBook(Book book) {
        var bookId = bookRepository.save(Book.builder().name(book.getName()).build()).getId();
        var author = authorService.saveAuthorIfNotExists(book.getAuthor());
        bookRepository.updateAuthor(bookId, author);
        saveGenres(bookId, book.getGenres());
        return bookId;
    }

    private void saveGenres(long bookId, List<Genre> genres) {
        if (!isEmpty(genres)) {
            var book = getBookById(bookId);
            if (book.isEmpty()) {
                throw new IllegalArgumentException("There is no book with such id");
            }
            var updateBook = book.get();
            updateBook.setGenres(genres.stream()
                    .map(genreService::saveGenreIfNotExists)
                    .collect(Collectors.toList()));
            bookRepository.save(updateBook);
        }
    }

    @Override
    public Optional<Book> getBookById(long bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public void updateBookById(Book newBook) {
        var oldBookOptional = getBookById(newBook.getId());
        if (oldBookOptional.isEmpty()) {
            throw new IllegalArgumentException("There is no book with such id");
        }
        var oldBook = oldBookOptional.get();
        if ((nonNull(oldBook.getName()) && !oldBook.getName().equals(newBook.getName())) ||
                (isNull(oldBook.getName()) && nonNull(newBook.getName()))) {
            oldBook.setName(newBook.getName());
        }
        if ((nonNull(oldBook.getAuthor()) &&
                (isNull(newBook.getAuthor()) || !oldBook.getAuthor().getFullName().equals(newBook.getAuthor().getFullName())))
                || (isNull(oldBook.getAuthor()) && nonNull(newBook.getAuthor()))) {
            Author author;
            if (isNull(newBook.getAuthor())) {
                author = null;
            } else {
                author = authorService.saveAuthorIfNotExists(newBook.getAuthor());
            }
            oldBook.setAuthor(author);
        }
        if (!(oldBook.getGenres().size() == newBook.getGenres().size() && new HashSet<>(oldBook.getGenres()).containsAll(newBook.getGenres()))) {
            oldBook.setGenres(newBook.getGenres());
        }
        bookRepository.save(oldBook);
    }

    @Override
    @Transactional
    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }
}
