package pt.andremartins.workshop.wiremock.cucumber.model;

import java.time.LocalDate;

public record Movie(
        String id,
        String title,
        LocalDate releaseDate
) {
}
