package pt.andremartins.workshop.wiremock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pt.andremartins.workshop.wiremock.model.MovieDetail;
import pt.andremartins.workshop.wiremock.model.Movies;
import pt.andremartins.workshop.wiremock.service.MovieService;

@RestController
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieDetail> getMovie(@PathVariable String id) {
        return ResponseEntity.ok(movieService.getMovieDetail(id));
    }

    @GetMapping("/movies")
    public ResponseEntity<Movies> searchMovies(@RequestParam String query) {
        return ResponseEntity.ok(movieService.searchMovies(query));
    }
}
