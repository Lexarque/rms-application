package org.acme.restaurantTable.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.restaurantTable.dto.CreateRestaurantTableRequest;
import org.acme.restaurantTable.dto.RestaurantTableResponse;
import org.acme.restaurantTable.dto.UpdateRestaurantTableRequest;
import org.acme.restaurantTable.service.RestaurantTableService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Path("/tables")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"admin", "staff", "manager"})
public class RestaurantTableResource {

    @Inject
    RestaurantTableService tableService;

    @GET
    public List<RestaurantTableResponse> listTables(@QueryParam("activeOnly") @DefaultValue("false") boolean activeOnly) {
        return tableService.listTables(activeOnly)
                .stream()
                .map(RestaurantTableResponse::from)
                .toList();
    }

    @GET
    @Path("/{id}")
    public RestaurantTableResponse getTable(@PathParam("id") UUID id) {
        return RestaurantTableResponse.from(tableService.getTable(id));
    }

    @POST
    @RolesAllowed({"ADMIN", "MANAGER"})
    public Response createTable(@Valid CreateRestaurantTableRequest request) {
        var created = tableService.createTable(request);
        return Response.created(URI.create("/api/tables/" + created.getId()))
                .entity(RestaurantTableResponse.from(created))
                .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "MANAGER"})
    public RestaurantTableResponse updateTable(@PathParam("id") UUID id,
                                               @Valid UpdateRestaurantTableRequest request) {
        return RestaurantTableResponse.from(tableService.updateTable(id, request));
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "MANAGER"})
    public Response deleteTable(@PathParam("id") UUID id) {
        tableService.deleteTable(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/available")
    @PermitAll
    public List<RestaurantTableResponse> listAvailableTables() {
        return tableService.listTables(true)
                .stream()
                .map(RestaurantTableResponse::from)
                .toList();
    }
}