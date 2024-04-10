package com.example.spring24.services.author;


import com.example.spring24.domain.Author;
import com.example.spring24.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public Author saveAuthorIfNotExists(Author author) {
        return authorRepository.findByFullName(author.getFullName()).orElse(authorRepository.save(author));
    }
}
