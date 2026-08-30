package com.railwise.journey.web;

import com.railwise.journey.domain.Journey;
import com.railwise.journey.domain.JourneyRepository;
import com.railwise.journey.domain.Route;
import com.railwise.journey.domain.RouteRepository;
import com.railwise.journey.web.dto.JourneyDtos.CreateJourneyRequest;
import com.railwise.journey.web.dto.JourneyDtos.JourneyResponse;
import com.railwise.journey.web.dto.JourneyDtos.UpdateJourneyStatusRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/journeys")
@RequiredArgsConstructor
public class JourneyController {

    private final JourneyRepository journeyRepository;
    private final RouteRepository routeRepository;

    @GetMapping
    public List<JourneyResponse> getAll(@RequestParam(required = false) Long routeId) {
        List<Journey> journeys = routeId != null
                ? journeyRepository.findByRouteId(routeId)
                : journeyRepository.findAll();
        return journeys.stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public JourneyResponse getOne(@PathVariable Long id) {
        return journeyRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new NoSuchElementException("Journey " + id + " not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JourneyResponse create(@Valid @RequestBody CreateJourneyRequest request) {
        Route route = routeRepository.findById(request.routeId())
                .orElseThrow(() -> new NoSuchElementException("Route " + request.routeId() + " not found"));

        Journey journey = new Journey();
        journey.setRoute(route);
        journey.setScheduledDeparture(request.scheduledDeparture());
        journey.setScheduledArrival(request.scheduledArrival());
        journey.setNotes(request.notes());
        return toResponse(journeyRepository.save(journey));
    }

    // e.g. mark a journey delayed/completed/cancelled as it progresses
    @PatchMapping("/{id}/status")
    public JourneyResponse updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateJourneyStatusRequest request) {
        Journey journey = journeyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Journey " + id + " not found"));

        journey.setStatus(request.status());
        if (request.actualDeparture() != null) journey.setActualDeparture(request.actualDeparture());
        if (request.actualArrival() != null) journey.setActualArrival(request.actualArrival());
        if (request.delayMinutes() != null) journey.setDelayMinutes(request.delayMinutes());

        return toResponse(journeyRepository.save(journey));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        journeyRepository.deleteById(id);
    }

    private JourneyResponse toResponse(Journey journey) {
        return new JourneyResponse(
                journey.getId(),
                journey.getRoute().getId(),
                journey.getScheduledDeparture(),
                journey.getActualDeparture(),
                journey.getScheduledArrival(),
                journey.getActualArrival(),
                journey.getStatus(),
                journey.getDelayMinutes(),
                journey.getNotes(),
                journey.getCreatedAt()
        );
    }
}
