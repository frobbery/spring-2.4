package com.example.spring24.services.author;

import com.example.spring24.domain.Author;

public interface AuthorService {

    Author saveAuthorIfNotExists(Author author);
}
