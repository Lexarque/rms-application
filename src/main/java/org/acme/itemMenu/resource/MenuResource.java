package org.acme.itemMenu.resource;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.itemMenu.dto.AddMenuIngredientRequest;
import org.acme.itemMenu.dto.CreateMenuItemRequest;
import org.acme.itemMenu.dto.MenuIngredientResponse;
import org.acme.itemMenu.dto.MenuItemResponse;
import org.acme.itemMenu.dto.UpdateMenuItemRequest;
import org.acme.itemMenu.mapper.MenuMapper;
import org.acme.itemMenu.service.MenuService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Path("/api/menu")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MenuResource {

    private final MenuService service;
    private final MenuMapper mapper;

    public MenuResource(MenuService service, MenuMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GET
    public List<MenuItemResponse> listItems(
            @QueryParam("q") String q,
            @QueryParam("isAvailable") Boolean isAvailable,
            @DefaultValue("0") @QueryParam("page") int page,
            @DefaultValue("20") @QueryParam("size") int size,
            @QueryParam("sort") String sort
    ) {
        return service.listItems(q, isAvailable, page, size, sort)
                .stream()
                .map(mapper::toItemResponse)
                .toList();
    }

    @GET
    @Path("/{id}")
    public MenuItemResponse getItem(@PathParam("id") UUID id) {
        return mapper.toItemResponse(service.getItem(id));
    }

    @POST
    public Response createItem(@Valid CreateMenuItemRequest request) {
        var created = service.createItem(request);

        return Response.created(URI.create("/api/menu/" + created.id))
                .entity(mapper.toItemResponse(created))
                .build();
    }

    @PUT
    @Path("/{id}")
    public MenuItemResponse updateItem(@PathParam("id") UUID id,
                                       @Valid UpdateMenuItemRequest request) {
        return mapper.toItemResponse(service.updateItem(id, request));
    }

    @DELETE
    @Path("/{id}")
    public Response deleteItem(@PathParam("id") UUID id) {
        service.deleteItem(id);
        return Response.noContent().build();
    }

    // =========================
    // INGREDIENTS
    // =========================

    @GET
    @Path("/{id}/ingredients")
    public List<MenuIngredientResponse> getIngredients(@PathParam("id") UUID id) {
        return service.getIngredients(id)
                .stream()
                .map(mapper::toIngredientResponse)
                .toList();
    }

    @POST
    @Path("/{id}/ingredients")
    public Response addIngredient(@PathParam("id") UUID id,
                                @Valid AddMenuIngredientRequest request) {

        var created = service.addIngredient(id, request);

        return Response.created(URI.create("/api/menu/" + id + "/ingredients/" + created.id))
                .entity(mapper.toIngredientResponse(created))
                .build();
    }

    @DELETE
    @Path("/{menuId}/ingredients/{ingredientId}")
    public Response removeIngredient(@PathParam("menuId") UUID menuId,
                                     @PathParam("ingredientId") UUID ingredientId) {
        service.removeIngredient(menuId, ingredientId);
        return Response.noContent().build();
    }
}