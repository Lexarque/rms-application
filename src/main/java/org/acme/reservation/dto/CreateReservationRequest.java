package org.acme.reservation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateReservationRequest(
        @NotNull UUID customerId,
        @NotNull UUID tableId,
        @NotNull LocalDateTime reservationTime,
        @NotNull @Min(1) Integer partySize,
        String specialRequests
) {}