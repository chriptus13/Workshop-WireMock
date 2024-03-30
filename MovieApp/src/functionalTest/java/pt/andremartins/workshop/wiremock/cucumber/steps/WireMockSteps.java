package pt.andremartins.workshop.wiremock.cucumber.steps;

import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.github.tomakehurst.wiremock.matching.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pt.andremartins.workshop.wiremock.cucumber.service.WireMockService;
import wiremock.com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@RequiredArgsConstructor
public class WireMockSteps {

    private final static RequestPattern EMPTY_PATTERN = RequestPatternBuilder.newRequestPattern().build();

    private final WireMockService wiremockService;
    private RequestPatternBuilder requestPatternBuilder = RequestPatternBuilder.newRequestPattern();

    @Given("reset WireMock requests")
    public void resetWireMockRequests() {
        wiremockService.resetRequests();
    }

    @Given("reset WireMock mappings")
    public void resetWireMockMappings() {
        wiremockService.resetMappings();
    }

    @Given("build request pattern for method {string} and path {string}")
    public void buildRequestPattern(String method, String path) {
        requestPatternBuilder = new RequestPatternBuilder(
                RequestMethod.fromString(method),
                UrlPathPattern.fromOneOf(null, null, path, null, null)
        );
    }

    @Given("request pattern with query key {string} and value {string}")
    public void setRequestQueryParam(String key, String value) {
        requestPatternBuilder.withQueryParam(key, new EqualToPattern(value));
    }

    @Given("request pattern with query params")
    public void setRequestQueryParams(Map<String, String> queryParams) {
        queryParams.forEach(this::setRequestQueryParam);
    }

    @Given("request pattern with header {string} and value {string}")
    public void setRequestHeader(String key, String value) {
        requestPatternBuilder.withHeader(key, new EqualToPattern(value));
    }

    @Given("request pattern with headers")
    public void setRequestHeaders(Map<String, String> headers) {
        headers.forEach(this::setRequestHeader);
    }

    @Given("request pattern without body field {string}")
    public void setRequestAbsentBodyField(String fieldJsonPath) {
        requestPatternBuilder.withRequestBody(
                new MatchesJsonPathPattern("$[?(!@." + fieldJsonPath + ")]")
        );
    }

    @Given("request pattern without body fields")
    public void setRequestAbsentBodyFields(List<String> fields) {
        fields.forEach(this::setRequestAbsentBodyField);
    }

    @Given("request pattern for body field {string} with value {string}")
    public void setRequestBodyFieldWithValue(String fieldJsonPath, String value) {
        requestPatternBuilder.withRequestBody(
                new MatchesJsonPathPattern(fieldJsonPath, new EqualToPattern(value))
        );
    }

    @Given("request pattern for body fields")
    public void setRequestBodyFieldsWithValues(Map<String, String> fields) {
        fields.forEach(this::setRequestBodyFieldWithValue);
    }


    @Given("request pattern with body")
    public void setRequestBodyPattern(String body) {
        requestPatternBuilder.withRequestBody(
                new EqualToJsonPattern(body, true, false)
        );
    }

    @Given("stub {string} has the following response body")
    public void updateStubResponseBody(String stubName, String jsonBody) throws JsonProcessingException {
        wiremockService.updateStubJsonResponseBody(stubName, jsonBody);
    }

    @Given("stub {string} has response status of {int}")
    public void updateStubResponseStatus(String stubName, int status) {
        wiremockService.updateStubResponseStatus(stubName, status);
    }

    @Then("number of matching requests is {int}")
    public void countRequestsMatching(int matching) {
        assertEquals(matching, wiremockService.countRequestsMatching(requestPatternBuilder.build()));
    }

    @Then("no calls to WireMock were made")
    public void assertNoCallsToWireMock() {
        assertEquals(0, wiremockService.countRequestsMatching(EMPTY_PATTERN));
    }
}
