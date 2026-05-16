package org.acme.inventories.resource;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.inventories.dto.AdjustQuantityRequest;
import org.acme.inventories.dto.CreateInventoryItemRequest;
import org.acme.inventories.dto.InventoryItemResponse;
import org.acme.inventories.dto.InventoryMovementResponse;
import org.acme.inventories.dto.UpdateInventoryItemRequest;
import org.acme.inventories.mapper.InventoryMapper;
import org.acme.inventories.model.StockStatus;
import org.acme.inventories.service.InventoryService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Path("/inventory")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InventoryResource {

    private final InventoryService inventoryService;
    private final InventoryMapper mapper;

    public InventoryResource(InventoryService inventoryService, InventoryMapper mapper) {
        this.inventoryService = inventoryService;
        this.mapper = mapper;
    }

    @GET
    public List<InventoryItemResponse> listItems(@QueryParam("q") String q,
                                                 @QueryParam("status") StockStatus status,
                                                 @DefaultValue("0") @QueryParam("page") int page,
                                                 @DefaultValue("20") @QueryParam("size") int size,
                                                 @DefaultValue("last_updated,desc") @QueryParam("sort") String sort) {
        return inventoryService.listItems(q, status, page, size, sort)
                .stream()
                .map(mapper::toItemResponse)
                .toList();
    }

    @GET
    @Path("/{id}")
    public InventoryItemResponse getItem(@PathParam("id") UUID id) {
        return mapper.toItemResponse(inventoryService.getItem(id));
    }

    @POST
    public Response createItem(@Valid CreateInventoryItemRequest request) {
        var created = inventoryService.createItem(request);
        return Response.created(URI.create("/api/inventory/" + created.id))
                .entity(mapper.toItemResponse(created))
                .build();
    }

    @PUT
    @Path("/{id}")
    public InventoryItemResponse updateItem(@PathParam("id") UUID id,
                                            @Valid UpdateInventoryItemRequest request) {
        return mapper.toItemResponse(inventoryService.updateItem(id, request));
    }

    @POST
    @Path("/{id}/movements")
    public InventoryItemResponse postMovement(@PathParam("id") UUID id,
                                              @Valid AdjustQuantityRequest request) {
        return mapper.toItemResponse(inventoryService.adjustQuantity(id, request));
    }

    @DELETE
    @Path("/{id}")
    public Response deleteItem(@PathParam("id") UUID id) {
        inventoryService.deleteItem(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/movements")
    public List<InventoryMovementResponse> listItemMovements(@PathParam("id") UUID id,
                                                             @QueryParam("month") String month) {
        return inventoryService.listMovements(id, month)
                .stream()
                .map(mapper::toMovementResponse)
                .toList();
    }

    @GET
    @Path("/movements")
    public List<InventoryMovementResponse> listMovements(@QueryParam("month") String month) {
        return inventoryService.listMovements(null, month)
                .stream()
                .map(mapper::toMovementResponse)
                .toList();
    }
}
