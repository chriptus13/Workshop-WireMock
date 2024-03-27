package pt.andremartins.workshop.wiremock.model;

import java.time.LocalDate;

public record Movie(
        String id,
        String title,
        LocalDate releaseDate
) {
}
