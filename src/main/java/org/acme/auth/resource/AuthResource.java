package org.acme.auth.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.auth.dto.AuthResponse;
import org.acme.auth.dto.LoginRequest;
import org.acme.auth.dto.RegisterRequest;
import org.acme.auth.service.AuthService;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    @PermitAll
    public AuthResponse login(LoginRequest req) {
        return authService.login(req.username, req.password);
    }

    @POST
    @Path("/register")
    @PermitAll
    public Response register(RegisterRequest req) {
        AuthResponse response = authService.register(req);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }
}