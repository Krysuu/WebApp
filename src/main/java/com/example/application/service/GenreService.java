package com.example.application.service;

import com.example.application.entity.Genre;
import com.example.application.repository.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public void delete(Genre genre) {
        genreRepository.delete(genre);
    }

    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    public Optional<Genre> findById(Long id) {
        return genreRepository.findById(id);
    }
}
