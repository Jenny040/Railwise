package com.railwise.journey.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "routes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String routeName;

    @NotBlank
    private String originStation;

    @NotBlank
    private String destinationStation;

    private Double distanceKm;

    // e.g. METRORAIL, GAUTRAIN, SHOSHOLOZA_MEYL
    private String operator;
}
