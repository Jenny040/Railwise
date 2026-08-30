package com.railwise.journey.web;

import com.railwise.journey.domain.Route;
import com.railwise.journey.domain.RouteRepository;
import com.railwise.journey.web.dto.RouteDtos.CreateRouteRequest;
import com.railwise.journey.web.dto.RouteDtos.RouteResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteRepository routeRepository;

    @GetMapping
    public List<RouteResponse> getAll() {
        return routeRepository.findAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public RouteResponse getOne(@PathVariable Long id) {
        return routeRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new NoSuchElementException("Route " + id + " not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RouteResponse create(@Valid @RequestBody CreateRouteRequest request) {
        Route route = new Route();
        route.setRouteName(request.routeName());
        route.setOriginStation(request.originStation());
        route.setDestinationStation(request.destinationStation());
        route.setDistanceKm(request.distanceKm());
        route.setOperator(request.operator());
        return toResponse(routeRepository.save(route));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        routeRepository.deleteById(id);
    }

    private RouteResponse toResponse(Route route) {
        return new RouteResponse(
                route.getId(),
                route.getRouteName(),
                route.getOriginStation(),
                route.getDestinationStation(),
                route.getDistanceKm(),
                route.getOperator()
        );
    }
}
