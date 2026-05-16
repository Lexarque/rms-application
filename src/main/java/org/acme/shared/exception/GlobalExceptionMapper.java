package org.acme.shared.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(Throwable exception) {
        if (exception instanceof ConstraintViolationException cve) {
            Map<String, String> fieldErrors = new LinkedHashMap<>();
            for (ConstraintViolation<?> violation : cve.getConstraintViolations()) {
                String field = violation.getPropertyPath().toString();
                fieldErrors.put(field.substring(field.lastIndexOf('.') + 1), violation.getMessage());
            }
            return build(Response.Status.BAD_REQUEST, "Validation failed", fieldErrors);
        }

        if (exception instanceof WebApplicationException wae) {
            Response.StatusType statusInfo = wae.getResponse().getStatusInfo();
            return build(statusInfo, exception.getMessage(), null);
        }

        return build(Response.Status.INTERNAL_SERVER_ERROR, "Unexpected error", null);
    }

    private Response build(Response.Status status, String message, Map<String, String> fieldErrors) {
        return build((Response.StatusType) status, message, fieldErrors);
    }

    private Response build(Response.StatusType status, String message, Map<String, String> fieldErrors) {
        ErrorResponse body = new ErrorResponse(
                LocalDateTime.now(),
                status.getStatusCode(),
                status.getReasonPhrase(),
                message,
                uriInfo == null ? null : uriInfo.getPath(),
                fieldErrors
        );
        return Response.status(status).entity(body).build();
    }
}
