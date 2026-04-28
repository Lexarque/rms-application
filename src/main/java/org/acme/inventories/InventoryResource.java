package org.acme.inventories;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.inventories.dto.CreateInventoryItemRequest;
import org.acme.inventories.dto.CreateInventoryTransactionRequest;
import org.acme.inventories.dto.InventoryItemResponse;
import org.acme.inventories.dto.InventoryTransactionResponse;
import org.acme.inventories.dto.UpdateInventoryItemRequest;
import org.acme.inventories.service.InventoryService;

import java.util.List;

@Path("/inventory")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InventoryResource {

    private final InventoryService inventoryService;

    public InventoryResource(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GET
    @Path("/items")
    public List<InventoryItemResponse> listItems(@QueryParam("search") String search,
                                                 @QueryParam("category") String category,
                                                 @QueryParam("status") String status,
                                                 @QueryParam("lowStock") Boolean lowStock) {
        return inventoryService.listItems(search, category, status, lowStock)
                .stream()
                .map(InventoryItemResponse::from)
                .toList();
    }

    @GET
    @Path("/items/{id}")
    public InventoryItemResponse getItem(@PathParam("id") Long id) {
        return InventoryItemResponse.from(inventoryService.getItemById(id));
    }

    @POST
    @Path("/items")
    public Response createItem(CreateInventoryItemRequest request) {
        InventoryItemResponse body = InventoryItemResponse.from(inventoryService.createItem(request));
        return Response.status(Response.Status.CREATED).entity(body).build();
    }

    @PUT
    @Path("/items/{id}")
    public InventoryItemResponse updateItem(@PathParam("id") Long id, UpdateInventoryItemRequest request) {
        return InventoryItemResponse.from(inventoryService.updateItem(id, request));
    }

    @DELETE
    @Path("/items/{id}")
    public Response deactivateItem(@PathParam("id") Long id) {
        inventoryService.deactivateItem(id);
        return Response.noContent().build();
    }

    @POST
    @Path("/items/{id}/transactions")
    public Response createTransaction(@PathParam("id") Long id, CreateInventoryTransactionRequest request) {
        InventoryTransactionResponse body = InventoryTransactionResponse.from(inventoryService.createTransaction(id, request));
        return Response.status(Response.Status.CREATED).entity(body).build();
    }

    @GET
    @Path("/items/{id}/transactions")
    public List<InventoryTransactionResponse> listItemTransactions(@PathParam("id") Long id) {
        return inventoryService.listItemTransactions(id)
                .stream()
                .map(InventoryTransactionResponse::from)
                .toList();
    }

    @GET
    @Path("/transactions")
    public List<InventoryTransactionResponse> listTransactions(@QueryParam("itemId") Long itemId,
                                                               @QueryParam("txnType") String txnType) {
        return inventoryService.listTransactions(itemId, txnType)
                .stream()
                .map(InventoryTransactionResponse::from)
                .toList();
    }
}
