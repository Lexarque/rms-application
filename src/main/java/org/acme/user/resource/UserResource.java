package org.acme.user.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.user.dto.CreateUserRequest;
import org.acme.user.model.User;
import org.acme.user.repository.UserRepository;
import org.acme.user.service.UserService;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;
    UserRepository userRepository;

//    @GET
//    @PermitAll
//    public Response getAllUser() {
//
//    }

    @POST
    @RolesAllowed("admin")
    @Transactional
    @Path("/create")
    public Response createUser(@Valid CreateUserRequest req) {
        User user = userService.createUser(req);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }
}
