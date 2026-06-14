package org.acme.itemMenu.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.itemMenu.dto.AddMenuIngredientRequest;
import org.acme.itemMenu.dto.MenuIngredientResponse;
import org.acme.itemMenu.dto.MenuItemRequest;
import org.acme.itemMenu.dto.MenuItemResponse;
import org.acme.itemMenu.service.MenuService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Path("/menu")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MenuResource {

    @Inject
    MenuService menuItemService;

    @GET
    @PermitAll
    public List<MenuItemResponse> listItems(
            @QueryParam("q") String q,
            @QueryParam("isAvailable") Boolean isAvailable,
            @DefaultValue("0") @QueryParam("page") int page,
            @DefaultValue("50") @QueryParam("size") int size) {
        return menuItemService.listItems(q, isAvailable, page, size)
                .stream()
                .map(MenuItemResponse::from)
                .toList();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    public MenuItemResponse getItem(@PathParam("id") UUID id) {
        return MenuItemResponse.from(menuItemService.getItem(id));
    }

    @POST
    @RolesAllowed({"admin", "manager"})
    public Response createItem(MenuItemRequest request) {
        var created = menuItemService.createItem(
                request.itemName(), request.description(), request.price(),
                request.category(), request.imageUrl(), request.isAvailable());
        return Response.created(URI.create("/api/menu/" + created.getId()))
                .entity(MenuItemResponse.from(created))
                .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"admin", "manager"})
    public MenuItemResponse updateItem(@PathParam("id") UUID id, MenuItemRequest request) {
        return MenuItemResponse.from(menuItemService.updateItem(
                id, request.itemName(), request.description(), request.price(),
                request.category(), request.imageUrl(), request.isAvailable()));
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"admin", "manager"})
    public Response deleteItem(@PathParam("id") UUID id) {
        menuItemService.deleteItem(id);
        return Response.noContent().build();
    }

    // ---------- Ingredients ----------

    @GET
    @Path("/{id}/ingredients")
    @PermitAll
    public List<MenuIngredientResponse> listIngredients(@PathParam("id") UUID id) {
        return menuItemService.listIngredients(id)
                .stream()
                .map(MenuIngredientResponse::from)
                .toList();
    }

    @POST
    @Path("/{id}/ingredients")
    @RolesAllowed({"admin", "manager"})
    public Response addIngredient(@PathParam("id") UUID id, AddMenuIngredientRequest request) {
        var ingredient = menuItemService.addIngredient(id, request);
        return Response.status(Response.Status.CREATED)
                .entity(MenuIngredientResponse.from(ingredient))
                .build();
    }

    @DELETE
    @Path("/{id}/ingredients/{ingredientId}")
    @RolesAllowed({"admin", "manager"})
    public Response deleteIngredient(@PathParam("id") UUID id,
                                     @PathParam("ingredientId") UUID ingredientId) {
        menuItemService.deleteIngredient(id, ingredientId);
        return Response.noContent().build();
    }
}