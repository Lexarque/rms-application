package org.acme.reservation.dto;

import org.acme.reservation.model.Reservation;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationResponse(
        UUID id,
        UUID customerId,
        String customerName,
        UUID tableId,
        String tableNumber,
        LocalDateTime reservationTime,
        Integer partySize,
        String status,
        String specialRequests,
        LocalDateTime createdAt
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getCustomer().getId(),
                reservation.getCustomer().getFullName(),
                reservation.getTable().getId(),
                reservation.getTable().getTableNumber(),
                reservation.getReservationTime(),
                reservation.getPartySize(),
                reservation.getStatus().name(),
                reservation.getSpecialRequests(),
                reservation.getCreatedAt()
        );
    }
}