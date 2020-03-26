package oose.dea.resources.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class PlaylistException extends Exception implements ExceptionMapper<LoginException> {

    public int statuscode;
    public PlaylistException(int statuscode){
        this.statuscode = statuscode;
    }

    @Override
    public Response toResponse(LoginException e) {
        return Response.status(statuscode).entity(e.getMessage())
                .type("text/plain").build();
    }
}