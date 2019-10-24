package oose.dea.resources.controllers;

import oose.dea.resources.dto.LoginRequestDto;
import oose.dea.resources.dataresources.LoginDAO;
import oose.dea.resources.models.UserModel;
import oose.dea.resources.services.LoginService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.*;

@Path("/login")
public class LoginController {

    private LoginService loginService;

    @Inject
    public void setLoginService(LoginService loginService){
        this.loginService = loginService;
    }

    @POST
    @Path("/")
    @Consumes("application/json")
    public Response login(LoginRequestDto request) throws SQLException {

        UserModel userModel = loginService.performFirstLogin(request);
        if("error".equals(userModel.getToken())){
            return Response.status(403).build();
        }
        else {
            return Response.ok().entity(userModel).build();
        }
    }

}
