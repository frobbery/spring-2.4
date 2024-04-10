package com.example.spring24.services.genre;


import com.example.spring24.domain.Genre;

public interface GenreService {

    Genre saveGenreIfNotExists(Genre genre);
}
