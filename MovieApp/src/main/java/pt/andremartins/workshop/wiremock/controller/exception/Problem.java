package pt.andremartins.workshop.wiremock.controller.exception;

public record Problem(
        String type,
        String detail
) {
}
