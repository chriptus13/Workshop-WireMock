package pt.andremartins.workshop.wiremock.cucumber.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pt.andremartins.workshop.wiremock.cucumber.config.MovieConfig;
import pt.andremartins.workshop.wiremock.cucumber.model.MovieDetail;
import pt.andremartins.workshop.wiremock.cucumber.model.Movies;

import java.net.URLEncoder;
import java.nio.charset.Charset;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final RestTemplate restTemplate;
    private final MovieConfig movieConfig;

    public ResponseEntity<Movies> search(String query) {
        return restTemplate.exchange(
                RequestEntity.get(movieConfig.address() + "/movies?query=" + URLEncoder.encode(query, Charset.defaultCharset()))
                        .build(),
                Movies.class
        );
    }

    public ResponseEntity<MovieDetail> getById(String id) {
        return restTemplate.exchange(
                RequestEntity.get(movieConfig.address() + "/movies/" + id)
                        .build(),
                MovieDetail.class
        );
    }
}
