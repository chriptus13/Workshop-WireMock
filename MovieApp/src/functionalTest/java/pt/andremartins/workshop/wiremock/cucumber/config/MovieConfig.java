package pt.andremartins.workshop.wiremock.cucumber.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("service")
public record MovieConfig(
        String address
) {
}
