package org.acme.reservation.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.acme.reservation.dto.CreateReservationRequest;
import org.acme.reservation.dto.UpdateReservationRequest;
import org.acme.reservation.model.Reservation;
import org.acme.reservation.model.ReservationStatus;
import org.acme.reservation.repository.ReservationRepository;
import org.acme.restaurantTable.model.RestaurantTable;
import org.acme.restaurantTable.repository.RestaurantTableRepository;
import org.acme.user.model.User;
import org.acme.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ReservationService {

    @Inject
    ReservationRepository reservationRepository;

    @Inject
    RestaurantTableRepository tableRepository;

    @Inject
    UserRepository userRepository;

    public List<Reservation> listReservations(ReservationStatus status, UUID tableId, UUID customerId) {
        if (status != null) {
            return reservationRepository.findByStatus(status);
        }
        if (tableId != null) {
            return reservationRepository.findByTable(tableId);
        }
        if (customerId != null) {
            return reservationRepository.findByCustomer(customerId);
        }
        return reservationRepository.listAll();
    }

    public Reservation getReservation(UUID id) {
        Reservation reservation = reservationRepository.findById(id);
        if (reservation == null) {
            throw new NotFoundException("Reservation not found");
        }
        return reservation;
    }

    @Transactional
    public Reservation createReservation(CreateReservationRequest request) {
        if (request.reservationTime().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Reservation time must be in the future");
        }

        User customer = userRepository.findById(request.customerId());
        if (customer == null) {
            throw new NotFoundException("Customer not found");
        }

        RestaurantTable table = tableRepository.findById(request.tableId());
        if (table == null) {
            throw new NotFoundException("Table not found");
        }
        if (!Boolean.TRUE.equals(table.getIsActive())) {
            throw new BadRequestException("Table is not active");
        }
        if (table.getCapacity() < request.partySize()) {
            throw new BadRequestException("Table capacity is insufficient for party size");
        }

        LocalDateTime windowEnd = request.reservationTime().plusHours(2);
        if (reservationRepository.hasConflict(request.tableId(), request.reservationTime(), windowEnd, null)) {
            throw new BadRequestException("Table is already reserved for that time slot");
        }

        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setTable(table);
        reservation.setReservationTime(request.reservationTime());
        reservation.setPartySize(request.partySize());
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setSpecialRequests(normalize(request.specialRequests()));

        reservationRepository.persist(reservation);
        return reservation;
    }

    @Transactional
    public Reservation updateReservation(UUID id, UpdateReservationRequest request) {
        Reservation reservation = getReservation(id);

        if (reservation.getStatus() == ReservationStatus.CANCELLED ||
                reservation.getStatus() == ReservationStatus.SEATED) {
            throw new BadRequestException("Cannot update a " + reservation.getStatus().name().toLowerCase() + " reservation");
        }

        if (request.tableId() != null) {
            RestaurantTable table = tableRepository.findById(request.tableId());
            if (table == null) {
                throw new NotFoundException("Table not found");
            }
            if (!Boolean.TRUE.equals(table.getIsActive())) {
                throw new BadRequestException("Table is not active");
            }
            reservation.setTable(table);
        }

        if (request.reservationTime() != null) {
            if (request.reservationTime().isBefore(LocalDateTime.now())) {
                throw new BadRequestException("Reservation time must be in the future");
            }
            LocalDateTime windowEnd = request.reservationTime().plusHours(2);
            if (reservationRepository.hasConflict(reservation.getTable().getId(),
                    request.reservationTime(), windowEnd, id)) {
                throw new BadRequestException("Table is already reserved for that time slot");
            }
            reservation.setReservationTime(request.reservationTime());
        }

        if (request.partySize() != null) {
            if (reservation.getTable().getCapacity() < request.partySize()) {
                throw new BadRequestException("Table capacity is insufficient for party size");
            }
            reservation.setPartySize(request.partySize());
        }

        if (request.specialRequests() != null) {
            reservation.setSpecialRequests(normalize(request.specialRequests()));
        }

        return reservation;
    }

    @Transactional
    public void cancelReservation(UUID id) {
        Reservation reservation = getReservation(id);
        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new BadRequestException("Reservation is already cancelled");
        }
        reservation.setStatus(ReservationStatus.CANCELLED);
    }

    private String normalize(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}