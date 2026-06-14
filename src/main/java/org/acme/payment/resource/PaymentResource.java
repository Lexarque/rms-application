package org.acme.payment.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.payment.dto.PaymentResponse;
import org.acme.payment.dto.ProcessPaymentRequest;
import org.acme.payment.service.PaymentService;

import java.util.UUID;

@Path("/payments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PaymentResource {

    @Inject
    PaymentService paymentService;

    @GET
    @Path("/order/{orderId}")
    @PermitAll
    public PaymentResponse getByOrder(@PathParam("orderId") UUID orderId) {
        return PaymentResponse.from(paymentService.getPaymentByOrder(orderId));
    }

    @POST
    @Path("/order/{orderId}")
    @PermitAll
    public PaymentResponse processPayment(@PathParam("orderId") UUID orderId,
                                          @Valid ProcessPaymentRequest request) {
        return PaymentResponse.from(paymentService.processPayment(orderId, request));
    }
}