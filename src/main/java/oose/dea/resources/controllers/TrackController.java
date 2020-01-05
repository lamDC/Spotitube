package oose.dea.resources.controllers;

import oose.dea.resources.dataresources.TrackDAO;
import oose.dea.resources.dto.TrackRequestDto;
import oose.dea.resources.dto.TrackResponseDto;
import oose.dea.resources.services.AuthorisationService;
import oose.dea.resources.services.TrackService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/")
public class TrackController {

    private TrackDAO trackDAO;
    private TrackService trackService;
    private AuthorisationService authorisationService;

    @Inject
    public void setAuthorisationService(AuthorisationService authorisationService) {
        this.authorisationService = authorisationService;
    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO){
        this.trackDAO = trackDAO;
    }

    @Inject
    public void setTrackService(TrackService trackService) { this.trackService = trackService;}

    @GET
    @Path("tracks")
    @Produces("application/json")
    public Response geefTracks(@QueryParam("forPlaylist") int id, @QueryParam("token") String token) throws SQLException {
        if(authorisationService.performAuthentication(token)) {
            TrackResponseDto trackResponseDto = trackService.performGeefBeschikbareTracks(id);
            return Response.ok().entity(trackResponseDto).build();
        }
        else {
            return Response.status(403).build();
        }
    }

    @GET
    @Path("playlists/{id}/tracks")
    @Produces("application/json")
    public Response tracksVanPlaylist(@PathParam("id") int id, @QueryParam("token") String token) throws SQLException {
        if(authorisationService.performAuthentication(token)){
            TrackResponseDto trackResponseDto = trackService.performGetTrackVanPlaylist(id);
            return Response.ok().entity(trackResponseDto).build();
        }
           else {
               return Response.status(403).build();
        }
    }

    @DELETE
    @Path("playlists/{playlist_id}/tracks/{id}")
    @Produces("application/json")
    public Response deleteTrack(@PathParam("playlist_id") int playlist_id, @PathParam("id") int track_id, @QueryParam("token") String token) throws SQLException {
        if(authorisationService.performTrackAuthentication(token, track_id)) {
            TrackResponseDto trackResponseDto = trackService.performDeleteTrackVanPlaylist(playlist_id, track_id);
            return Response.ok().entity(trackResponseDto).build();
        }
        else {
            return Response.status(403).build();
        }
    }


    @POST
    @Path("playlists/{id}/tracks")
    @Consumes("application/json")
    public Response addTrackToPlaylist(@QueryParam("token") String token, @PathParam("id") int playlist_id, TrackRequestDto trackRequestDto) throws SQLException {
        if(authorisationService.performPlaylistAuthorisation(token, playlist_id)) {
            TrackResponseDto trackResponseDto = trackService.performAddTrackToPlaylist(trackRequestDto, playlist_id);
            return Response.ok().entity(trackResponseDto).build();
        }
        else {
            return Response.status(402).build();
        }

    }
}
