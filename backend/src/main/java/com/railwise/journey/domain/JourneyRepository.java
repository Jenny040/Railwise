package com.railwise.journey.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JourneyRepository extends JpaRepository<Journey, Long> {

    List<Journey> findByRouteId(Long routeId);
}
