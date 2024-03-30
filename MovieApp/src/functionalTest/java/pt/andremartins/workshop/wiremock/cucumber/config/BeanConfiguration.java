package pt.andremartins.workshop.wiremock.cucumber.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.client.HttpAdminClient;
import lombok.NonNull;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

@Configuration
public class BeanConfiguration {
    @Bean
    public HttpAdminClient wiremockHttpAdminClient(WireMockConfig wiremockConfig) throws MalformedURLException {
        URL url = URI.create(wiremockConfig.address()).toURL();
        return new HttpAdminClient(url.getProtocol(), url.getHost(), url.getPort(), url.getPath());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .errorHandler(new ExceptionlessResponseErrorHandler())
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule());
    }

    private static class ExceptionlessResponseErrorHandler implements ResponseErrorHandler {
        @Override
        public boolean hasError(@NonNull ClientHttpResponse response) {
            return false;
        }

        @Override
        public void handleError(@NonNull ClientHttpResponse response) {
        }
    }
}
