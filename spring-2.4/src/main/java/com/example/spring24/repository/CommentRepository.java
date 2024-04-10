package com.example.spring24.repository;

import com.example.spring24.domain.Book;
import com.example.spring24.domain.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Modifying
    @Query("update Comment c set c.book = :book where c.id = :id")
    void updateCommentBook(@Param("id") long id, @Param("book") Book book);

    @EntityGraph(attributePaths = "book")
    List<Comment> findAllByBookId(long bookId);

    @Modifying
    @Query("update Comment c set c.text = :newText where c.id = :id")
    void updateTextById(@Param("id") long id, @Param("newText") String newText);
}
