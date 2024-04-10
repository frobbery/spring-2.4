package com.example.spring24.services.comment;



import com.example.spring24.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    long addCommentToBook(Comment comment);

    Optional<Comment> getCommentById(long id);

    List<Comment> getAllCommentsOfBook(long bookId);

    void updateCommentTextById(long id, String newText);

    void deleteCommentById(long id);
}
