package org.acme.reservation.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateReservationRequest(
        UUID tableId,
        LocalDateTime reservationTime,
        Integer partySize,
        String specialRequests
) {}