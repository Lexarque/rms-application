package org.acme.user.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.shared.dto.DataTableResponse;
import org.acme.user.dto.CreateUserRequest;
import org.acme.user.dto.StaffDto;
import org.acme.user.dto.UpdateUserRequest;
import org.acme.user.model.User;
import org.acme.user.repository.UserRepository;
import org.acme.user.service.UserService;

import java.util.List;
import java.util.UUID;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;
    @Inject
    UserRepository userRepository;

    @GET
    @RolesAllowed({"admin", "manager"})
    @Path("/staff")
    public Response getStaffs(
            @QueryParam("name") String name,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size) {

        var query = userRepository.findStaff(name);

        List<User> staffEntities = query.page(page, size).list();
        long filteredRecords = query.count();
        long totalRecords = userRepository.countAllStaff();

        List<StaffDto> dtoList = staffEntities.stream()
                .map(StaffDto::from)
                .toList();

        DataTableResponse<StaffDto> response = new DataTableResponse<>(
                dtoList,
                totalRecords,
                filteredRecords
        );

        return Response.ok(response).build();
    }

    @GET
    @RolesAllowed({"admin", "manager"})
    @Path("/staff/{id}")
    public Response getStaff(
        @PathParam("id") UUID id
    ) {
        User user = userRepository.findById(id);
        if (user == null || !"staff".equals(user.role)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(StaffDto.from(user)).build();
    }

    @POST
    @RolesAllowed({"admin", "manager"})
    @Transactional
    @Path("/create")
    public Response createUser(@Valid CreateUserRequest req) {
        User user = userService.createUser(req);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @PUT
    @RolesAllowed({"admin", "manager"})
    @Transactional
    @Path("/staff/{id}")
    public Response updateStaff(@PathParam("id") UUID id, @Valid UpdateUserRequest req) {
        userService.updateUser(id, req);
        return Response.noContent().build();
    }

    @DELETE
    @RolesAllowed({"admin", "manager"})
    @Transactional
    @Path("/staff/{id}")
    public Response deleteStaff(@PathParam("id") UUID id) {
        userService.deleteUser(id);
        return Response.noContent().build();
    }
}
