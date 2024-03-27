package pt.andremartins.workshop.wiremock.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pt.andremartins.workshop.wiremock.client.config.TMDBConfig;
import pt.andremartins.workshop.wiremock.client.dto.MovieCreditsDto;
import pt.andremartins.workshop.wiremock.client.dto.MovieDto;
import pt.andremartins.workshop.wiremock.client.dto.MoviesDto;

@Component
@RequiredArgsConstructor
public class TMDBClient {
    private final RestTemplate restTemplate;
    private final TMDBConfig config;

    public MovieDto getMovieById(String id) {
        return restTemplate.exchange(
                RequestEntity.get(config.url() + "/movie/" + id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + config.accessToken())
                        .build(),
                MovieDto.class
        ).getBody();
    }

    public MoviesDto searchMovies(String query) {
        return restTemplate.exchange(
                RequestEntity.get(config.url() + "/search/movie?query=" + query)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + config.accessToken())
                        .build(),
                MoviesDto.class
        ).getBody();
    }

    public MovieCreditsDto getMovieCredits(String id) {
        return restTemplate.exchange(
                RequestEntity.get(config.url() + "/movie/" + id + "/credits")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + config.accessToken())
                        .build(),
                MovieCreditsDto.class
        ).getBody();
    }
}
