package com.railwise.journey.web.dto;

import jakarta.validation.constraints.NotBlank;

public class RouteDtos {

    public record CreateRouteRequest(
            @NotBlank String routeName,
            @NotBlank String originStation,
            @NotBlank String destinationStation,
            Double distanceKm,
            String operator
    ) {}

    public record RouteResponse(
            Long id,
            String routeName,
            String originStation,
            String destinationStation,
            Double distanceKm,
            String operator
    ) {}
}
