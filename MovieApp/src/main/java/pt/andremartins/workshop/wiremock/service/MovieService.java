package pt.andremartins.workshop.wiremock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.andremartins.workshop.wiremock.client.TMDBClient;
import pt.andremartins.workshop.wiremock.client.dto.MovieCreditsDto;
import pt.andremartins.workshop.wiremock.client.dto.MovieDto;
import pt.andremartins.workshop.wiremock.client.dto.MoviesDto;
import pt.andremartins.workshop.wiremock.model.Credit;
import pt.andremartins.workshop.wiremock.model.Movie;
import pt.andremartins.workshop.wiremock.model.MovieDetail;
import pt.andremartins.workshop.wiremock.model.Movies;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final TMDBClient tmdbClient;

    public Movies searchMovies(String query) {
        MoviesDto moviesDto = tmdbClient.searchMovies(query);
        return new Movies(
                moviesDto.count(),
                moviesDto.results().stream()
                        .map(it -> new Movie(it.id(), it.title(), it.releaseDate()))
                        .toList()
        );
    }

    public MovieDetail getMovieDetail(String id) {
        MovieDto movieDto = tmdbClient.getMovieById(id);
        MovieCreditsDto creditsDto = tmdbClient.getMovieCredits(id);
        return new MovieDetail(
                movieDto.id(),
                movieDto.title(),
                movieDto.imdbId(),
                movieDto.overview(),
                movieDto.releaseDate(),
                creditsDto.cast().stream()
                        .map(it -> new Credit(it.id(), it.name(), it.character()))
                        .toList()
        );
    }
}
