package pt.andremartins.workshop.wiremock.cucumber.service;

import com.github.tomakehurst.wiremock.client.HttpAdminClient;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.matching.RequestPattern;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiremock.com.fasterxml.jackson.core.JsonProcessingException;
import wiremock.com.fasterxml.jackson.databind.JsonNode;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath;

@Service
@RequiredArgsConstructor
public class WireMockService {
    private final HttpAdminClient httpAdminClient;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public int countRequestsMatching(RequestPattern requestPattern) {
        return httpAdminClient.countRequestsMatching(requestPattern).getCount();
    }

    public void updateStubJsonResponseBody(String stubName, String responseBody) throws JsonProcessingException {
        StubMapping mapping = getStubMappingByName(stubName);
        JsonNode json = objectMapper.readTree(responseBody);
        mapping.setResponse(
                ResponseDefinitionBuilder.like(mapping.getResponse())
                        .withJsonBody(json)
                        .build()
        );
        httpAdminClient.editStubMapping(mapping);
    }

    public void updateStubResponseStatus(String stubName, int status) {
        StubMapping mapping = getStubMappingByName(stubName);
        mapping.setResponse(
                ResponseDefinitionBuilder.like(mapping.getResponse())
                        .withStatus(status)
                        .build()
        );
        httpAdminClient.editStubMapping(mapping);
    }

    public void resetRequests() {
        httpAdminClient.resetRequests();
    }

    public void resetMappings() {
        httpAdminClient.resetToDefaultMappings();
    }

    private StubMapping getStubMappingByName(String name) {
        StubMapping mapping = httpAdminClient.findAllStubsByMetadata(matchingJsonPath("$.name", equalTo(name)))
                .getMappings().getFirst();
        if (mapping == null) {
            throw new IllegalArgumentException("Stub " + name + " not found!");
        }
        return mapping;
    }
}
