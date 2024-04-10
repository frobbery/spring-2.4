package com.example.spring24.repository;

import com.example.spring24.domain.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    Optional<Author> findByFullName(String fullName);
}
