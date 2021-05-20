package oose.dea.resources.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class TrackException extends Exception implements ExceptionMapper<TrackException> {

    public int statuscode;
    public TrackException(int statuscode){ this.statuscode = statuscode;}

    @Override
    public Response toResponse(TrackException e){
        return Response.status(statuscode).entity(e.getMessage())
                .type("text/plain").build();
    }

}
