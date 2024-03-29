package pt.andremartins.workshop.wiremock.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Configuration
public class WebClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .interceptors((request, body, execution) -> {
                    String method = request.getMethod().name();
                    URI uri = request.getURI();
                    long start = System.currentTimeMillis();
                    log.info("Client request - {} {}", method, uri);
                    ClientHttpResponse response = execution.execute(request, body);
                    long responseTime = System.currentTimeMillis() - start;
                    log.info("Client response - {} {} - {} ({} ms)", method, uri, response.getStatusCode(), responseTime);
                    return response;
                })
                .build();
    }

}
