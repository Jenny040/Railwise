package com.railwise.journey.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "journeys")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Journey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @NotNull
    private Instant scheduledDeparture;

    private Instant actualDeparture;

    private Instant scheduledArrival;

    private Instant actualArrival;

    @Enumerated(EnumType.STRING)
    private JourneyStatus status = JourneyStatus.SCHEDULED;

    private Integer delayMinutes;

    private String notes;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public enum JourneyStatus {
        SCHEDULED,
        IN_PROGRESS,
        COMPLETED,
        DELAYED,
        CANCELLED
    }
}
