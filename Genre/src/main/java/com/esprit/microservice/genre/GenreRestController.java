package com.esprit.microservice.genre;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Genre")
public class GenreRestController {
    IGenreService GenreService;
    @GetMapping("/")
    public List<Genre> getGenres() {
        return  GenreService.retrieveAllGenres();
    }
    @GetMapping("/Top5")
    public List<Genre> getTop5() {
        return GenreService.retrieveTop5();
    }
    @GetMapping("/{Genre-id}")
    public Genre retrieveGenre(@PathVariable("Genre-id") Integer chId) {
        return   GenreService.retrieveGenre(chId);

    }
    @PostMapping("/")
    public Genre addGenre(@RequestBody Genre c) {
        c.setIdGenre(null);
        Genre Genre = GenreService.addGenre(c);
        return Genre;
    }
    @DeleteMapping("/{Genre-id}")
    public void removeGenre(@PathVariable("Genre-id") Integer chId) {
        GenreService.removeGenre(chId);
    }
    @PutMapping("/")
    public Genre modifyGenre(@RequestBody Genre c) {
        Genre Genre = GenreService.modifyGenre(c);
        return Genre;
    }
    @PutMapping("/updatePop")
    public void updatePopularity(@RequestBody Genre c){
        c.setPopularity(c.getPopularity()+1);
        GenreService.modifyGenre(c);
    }
}
