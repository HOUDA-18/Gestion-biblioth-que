package com.esprit.microservice.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor

@RequestMapping("/Genre")
public class GenreRestController {
    @Autowired
  IGenreService GenreService;
    @GetMapping("/all")
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
    @PostMapping("/ajout")
    public Genre addGenre(@RequestBody Genre c) {
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
    // Injection de la propriété welcome.message depuis AuthorService.properties
    @Value("${welcome.message}")
    private String welcomeMessage;

    // Injection de la propriété some.config.value
    @Value("${some.config.value}")
    private String someConfigValue;

    @GetMapping("/welcome")
    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    @GetMapping("/config")
    public String getSomeConfigValue() {
        return someConfigValue;
    }
}
