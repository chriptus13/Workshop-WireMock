package pt.andremartins.workshop.wiremock.client.dto;

import java.util.List;

public record MovieCreditsDto(
        String id,
        List<CreditDto> cast
) {
}
