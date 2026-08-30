package com.railwise.journey.web.dto;

import com.railwise.journey.domain.Journey;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public class JourneyDtos {

    public record CreateJourneyRequest(
            @NotNull Long routeId,
            @NotNull Instant scheduledDeparture,
            Instant scheduledArrival,
            String notes
    ) {}

    public record UpdateJourneyStatusRequest(
            @NotNull Journey.JourneyStatus status,
            Instant actualDeparture,
            Instant actualArrival,
            Integer delayMinutes
    ) {}

    public record JourneyResponse(
            Long id,
            Long routeId,
            Instant scheduledDeparture,
            Instant actualDeparture,
            Instant scheduledArrival,
            Instant actualArrival,
            Journey.JourneyStatus status,
            Integer delayMinutes,
            String notes,
            Instant createdAt
    ) {}
}
