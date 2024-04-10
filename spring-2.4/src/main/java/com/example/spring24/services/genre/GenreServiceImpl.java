package com.example.spring24.services.genre;



import com.example.spring24.domain.Genre;
import com.example.spring24.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService{

    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public Genre saveGenreIfNotExists(Genre genre) {
        return genreRepository.findByGenreName(genre.getGenreName()).orElse(genreRepository.save(genre));
    }
}
