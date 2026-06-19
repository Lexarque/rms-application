package org.acme.order.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.order.dto.*;
import org.acme.order.model.OrderStatus;
import org.acme.order.model.OrderType;
import org.acme.order.service.OrderService;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Path("/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

    @GET
    public List<OrderResponse> listOrders(@QueryParam("q") String search,
                                          @QueryParam("status") OrderStatus status,
                                          @QueryParam("type") OrderType type,
                                          @QueryParam("from") LocalDate fromDate,
                                          @QueryParam("to") LocalDate toDate,
                                          @DefaultValue("0") @QueryParam("page") int page,
                                          @DefaultValue("20") @QueryParam("size") int size,
                                          @DefaultValue("created_at,desc") @QueryParam("sort") String sort) {
        return orderService.listOrders(search, status, type, fromDate, toDate, page, size, sort)
                .stream()
                .map(OrderResponse::from)
                .toList();
    }

    @GET
    @Path("/{id}")
    public OrderResponse getOrder(@PathParam("id") UUID id) {
        return OrderResponse.from(orderService.getOrder(id));
    }

    @POST
    public Response createOrder(@Valid CreateOrderRequest request) {
        var created = orderService.createOrder(request);
        return Response.created(URI.create("/api/orders/" + created.getId()))
                .entity(OrderResponse.from(created))
                .build();
    }

    @PUT
    @Path("/{id}")
    public OrderResponse updateOrder(@PathParam("id") UUID id,
                                     @Valid UpdateOrderRequest request) {
        return OrderResponse.from(orderService.updateOrder(id, request));
    }

    @DELETE
    @Path("/{id}")
    public Response cancelOrder(@PathParam("id") UUID id) {
        orderService.cancelOrder(id);
        return Response.noContent().build();
    }

    @POST
    @Path("/{id}/status")
    public OrderResponse changeStatus(@PathParam("id") UUID id,
                                      @Valid StatusChangeRequest request) {
        return orderService.changeStatus(id, request);
    }
}