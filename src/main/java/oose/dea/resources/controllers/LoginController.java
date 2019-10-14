package oose.dea.resources.controllers;

import oose.dea.resources.dto.LoginRequestDto;
import oose.dea.resources.dataresources.LoginDAO;
import oose.dea.resources.models.UserModel;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.*;

@Path("/login")
public class LoginController {

    private LoginDAO loginDAO;

    @Inject
    public void setLoginDAO(LoginDAO loginDAO){ this.loginDAO = loginDAO; }

    @POST
    @Path("/")
    @Consumes("application/json")
    public Response login(LoginRequestDto request) throws SQLException {

        UserModel userModel = loginDAO.login(request);

        if("error".equals(userModel.getToken())){
            return Response.status(403).build();
        }
        else {
            return Response.ok().entity(userModel).build();
        }
    }

}
