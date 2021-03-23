package oose.dea.resources.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class AuthorisationException extends Exception implements ExceptionMapper<AuthorisationException> {

    public int statuscode;
    public AuthorisationException(int statuscode){
        this.statuscode = statuscode;
    }

    @Override
    public Response toResponse(AuthorisationException e) {
        return Response.status(statuscode).entity(e.getMessage())
                .type("text/plain").build();
    }
}
