package pt.andremartins.workshop.wiremock.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("tmdb")
public record TMDBConfig(
        String url,
        String accessToken
) {
}