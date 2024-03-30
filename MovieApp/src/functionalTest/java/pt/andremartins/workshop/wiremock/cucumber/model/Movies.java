package pt.andremartins.workshop.wiremock.cucumber.model;

import java.util.List;

public record Movies(
        int count,
        List<Movie> results
) {
}
