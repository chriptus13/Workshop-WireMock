package pt.andremartins.workshop.wiremock.cucumber.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import pt.andremartins.workshop.wiremock.cucumber.model.Credit;
import pt.andremartins.workshop.wiremock.cucumber.model.Movie;
import pt.andremartins.workshop.wiremock.cucumber.model.MovieDetail;
import pt.andremartins.workshop.wiremock.cucumber.model.Movies;
import pt.andremartins.workshop.wiremock.cucumber.service.MovieService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class MovieSteps {
    private final ObjectMapper objectMapper;
    private final MovieService movieService;
    private final Context context = new Context();

    private static class Context {
        ResponseEntity<Movies> movies;
        ResponseEntity<MovieDetail> movieDetail;
        ResponseEntity<?> lastResponse;
    }

    @When("I search for {string}")
    public void searchFor(String query) {
        context.lastResponse = context.movies = movieService.search(query);
    }

    @When("I get movie with id {string}")
    public void getMovieById(String id) {
        context.lastResponse = context.movieDetail = movieService.getById(id);
    }

    @Then("response status is {int}")
    public void assertResponseStatus(int status) {
        assertEquals(status, context.lastResponse.getStatusCode().value());
    }

    @Then("returned movie has the following details")
    public void assertMovieDetails(MovieDetails movieDetails) {
        MovieDetail actualMovie = context.movieDetail.getBody();
        assertEquals(movieDetails.id, actualMovie.id());
        assertEquals(movieDetails.title, actualMovie.title());
        assertEquals(movieDetails.imdbId, actualMovie.imdbId());
        assertEquals(movieDetails.releaseDate, actualMovie.releaseDate());
    }

    @Then("returned movie has the following overview")
    public void assertMovieOverview(String overview) {
        MovieDetail actualMovie = context.movieDetail.getBody();
        assertEquals(overview, actualMovie.overview());
    }

    @Then("returned movie includes the following cast")
    public void assertMovieCast(List<Credit> cast) {
        MovieDetail actualMovie = context.movieDetail.getBody();
        assertTrue(cast.size() <= actualMovie.cast().size());
        assertTrue(actualMovie.cast().containsAll(cast));
    }

    @Then("returned movies include")
    public void assertMovies(List<Movie> expectedMovies) {
        Movies actualMovies = context.movies.getBody();
        assertTrue(expectedMovies.size() <= actualMovies.count());
        assertTrue(actualMovies.results().containsAll(expectedMovies));
    }

    @DataTableType
    public Movie movie(Map<String, String> data) throws JsonProcessingException {
        return objectMapper.readValue(objectMapper.writeValueAsString(data), Movie.class);
    }

    public record MovieDetails(
            String id,
            String title,
            String imdbId,
            LocalDate releaseDate
    ) {
    }

    @DataTableType
    public MovieDetails movieDetails(Map<String, String> data) throws JsonProcessingException {
        return objectMapper.readValue(objectMapper.writeValueAsString(data), MovieDetails.class);
    }

    @DataTableType
    public Credit credit(Map<String, String> data) throws JsonProcessingException {
        return objectMapper.readValue(objectMapper.writeValueAsString(data), Credit.class);
    }
}
