package com.esprit.microservice.genre;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements IGenreService{

    GenreRepository GenreRepository;
    public List<Genre> retrieveAllGenres() {
        return GenreRepository.findAll();
    }
    public Genre retrieveGenre(Integer GenreId) {
        return GenreRepository.findById(GenreId).get();
    }
    public Genre addGenre(Genre c) {
        return GenreRepository.save(c);
    }
    public void removeGenre(Integer GenreId) {
        GenreRepository.deleteById(GenreId);
    }
    public Genre modifyGenre(Genre Genre) {
        return GenreRepository.save(Genre);
    }
    public List<Genre> retrieveTop5() {
        return GenreRepository.findTop5ByOrderByPopularityDesc();
    }

}
