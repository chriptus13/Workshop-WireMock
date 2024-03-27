package pt.andremartins.workshop.wiremock.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record MoviesDto(
        int page,
        @JsonProperty("total_results")
        int count,
        List<MovieDto> results
) {
}
