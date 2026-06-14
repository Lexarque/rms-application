package org.acme.reservation.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.reservation.dto.CreateReservationRequest;
import org.acme.reservation.dto.ReservationResponse;
import org.acme.reservation.dto.UpdateReservationRequest;
import org.acme.reservation.model.ReservationStatus;
import org.acme.reservation.service.ReservationService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Path("/reservations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"ADMIN", "STAFF", "MANAGER"})
public class ReservationResource {

    @Inject
    ReservationService reservationService;

    @GET
    public List<ReservationResponse> listReservations(@QueryParam("status") ReservationStatus status,
                                                      @QueryParam("tableId") UUID tableId,
                                                      @QueryParam("customerId") UUID customerId) {
        return reservationService.listReservations(status, tableId, customerId)
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @GET
    @Path("/{id}")
    public ReservationResponse getReservation(@PathParam("id") UUID id) {
        return ReservationResponse.from(reservationService.getReservation(id));
    }

    @POST
    public Response createReservation(@Valid CreateReservationRequest request) {
        var created = reservationService.createReservation(request);
        return Response.created(URI.create("/api/reservations/" + created.getId()))
                .entity(ReservationResponse.from(created))
                .build();
    }

    @PUT
    @Path("/{id}")
    public ReservationResponse updateReservation(@PathParam("id") UUID id,
                                                 @Valid UpdateReservationRequest request) {
        return ReservationResponse.from(reservationService.updateReservation(id, request));
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "MANAGER"})
    public Response cancelReservation(@PathParam("id") UUID id) {
        reservationService.cancelReservation(id);
        return Response.noContent().build();
    }
}