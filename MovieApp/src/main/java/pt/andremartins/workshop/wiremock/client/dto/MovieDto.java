package pt.andremartins.workshop.wiremock.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record MovieDto(
        String id,
        @JsonProperty("imdb_id")
        String imdbId,
        @JsonProperty("original_title")
        String title,
        String overview,
        @JsonProperty("release_date")
        LocalDate releaseDate
) {
}
