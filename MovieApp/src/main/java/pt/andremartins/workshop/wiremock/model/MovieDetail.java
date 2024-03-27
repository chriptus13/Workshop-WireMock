package pt.andremartins.workshop.wiremock.model;

import java.time.LocalDate;
import java.util.List;

public record MovieDetail(
        String id,
        String title,
        String imdbId,
        String overview,
        LocalDate releaseDate,
        List<Credit> cast
) {
}
