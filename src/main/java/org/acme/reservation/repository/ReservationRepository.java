package org.acme.reservation.repository;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.reservation.model.Reservation;
import org.acme.reservation.model.ReservationStatus;
import org.acme.shared.repository.BaseRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ReservationRepository extends BaseRepository<Reservation> {

    public List<Reservation> findByCustomer(UUID customerId) {
        return find("customer.id", Sort.by("reservationTime").descending(), customerId).list();
    }

    public List<Reservation> findByTable(UUID tableId) {
        return find("table.id", Sort.by("reservationTime").descending(), tableId).list();
    }

    public List<Reservation> findByStatus(ReservationStatus status) {
        return find("status", Sort.by("reservationTime"), status).list();
    }

    public List<Reservation> findByDateRange(LocalDateTime from, LocalDateTime to) {
        return find("reservationTime >= ?1 and reservationTime < ?2",
                Sort.by("reservationTime"), from, to).list();
    }

    public boolean hasConflict(UUID tableId, LocalDateTime start, LocalDateTime end, UUID excludeReservationId) {
        String query = "table.id = ?1 and status in (?2, ?3) and reservationTime < ?4 and reservationTime >= ?5";

        if (excludeReservationId != null) {
            query += " and id != ?6";
            return find(query,
                    tableId,
                    ReservationStatus.PENDING,
                    ReservationStatus.CONFIRMED,
                    end,
                    start,
                    excludeReservationId)
                    .count() > 0;
        }

        return find(query,
                tableId,
                ReservationStatus.PENDING,
                ReservationStatus.CONFIRMED,
                end,
                start)
                .count() > 0;
    }
}