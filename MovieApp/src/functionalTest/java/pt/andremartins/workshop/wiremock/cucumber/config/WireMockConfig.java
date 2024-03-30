package pt.andremartins.workshop.wiremock.cucumber.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("wiremock")
public record WireMockConfig(
        String address
) {
}
