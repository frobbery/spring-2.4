package com.example.spring24.repository;

import com.example.spring24.domain.Genre;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    Optional<Genre> findByGenreName(String genreName);
}
