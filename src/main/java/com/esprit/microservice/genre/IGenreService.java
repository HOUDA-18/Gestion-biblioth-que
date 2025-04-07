package com.esprit.microservice.genre;

import java.util.List;

public interface IGenreService {
    public List<Genre> retrieveAllGenres();
    public Genre retrieveGenre(Integer GenreId);
    public Genre addGenre(Genre c);
    public void removeGenre(Integer GenreId);
    public Genre modifyGenre(Genre Genre);
    public List<Genre> retrieveTop5();
}
