package org.acme.dashboard.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.dashboard.dto.DashboardResponse;
import org.acme.dashboard.service.DashboardService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Set;

@Path("/dashboard")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"admin", "manager", "staff"})
public class DashboardResource {

    @Inject
    DashboardService dashboardService;

    @Inject
    JsonWebToken jwt;

    @GET
    @Transactional
    public DashboardResponse getDashboard() {
        String role = jwt.getGroups().stream().findFirst().orElse("");
        return dashboardService.getDashboard(role);
    }
}