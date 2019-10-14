package oose.dea.resources.controllers;

import oose.dea.resources.dataresources.PlaylistDAO;
import oose.dea.resources.dto.PlaylistRequestDto;
import oose.dea.resources.dto.PlaylistResponseDto;
import oose.dea.resources.services.AuthenticationService;
import oose.dea.resources.services.AuthorisationService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/")
public class PlaylistController {

    private PlaylistDAO playlistDAO;
    private AuthenticationService authenticationService;
    private AuthorisationService authorisationService;

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO){ this.playlistDAO = playlistDAO; }

    @Inject
    public void setAuthenticationService(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @Inject
    public void setAuthorisationService(AuthorisationService authorisationService){
        this.authorisationService = authorisationService;
    }

    @GET
    @Path("playlists")
    @Produces("application/json")
    public Response Playlist(@QueryParam("token") String token) throws SQLException {
        if(authenticationService.performAuthentication(token)) {
            PlaylistResponseDto playlistResponseDto = playlistDAO.getPlaylists();   // deze doorlussen van controller naar service naar dao
            return Response.ok().entity(playlistResponseDto).build();
        }
        else {
            return Response.status(403).build();
        }
    }

    @DELETE
    @Path("playlists/{id}")
    @Produces("application/json")
    public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token) throws SQLException {
        if(authorisationService.performPlaylistAuthorisation(token, id)){
            PlaylistResponseDto playlistResponseDto = playlistDAO.deletePlaylistFromDatabase(id);
            return Response.ok().entity(playlistResponseDto).build();
        }
        else {
            return Response.status(403).build();
        }

    }

    @PUT
    @Path("playlists/{id}")
    @Consumes("application/json")
    public Response editPlaylist(@PathParam("id") int id, @QueryParam("token") String token, PlaylistRequestDto request) throws SQLException {
        if(authorisationService.performPlaylistAuthorisation(token, id)) {
            PlaylistResponseDto playlistResponseDto = playlistDAO.editPlaylist(request, id);
            return Response.ok().entity(playlistResponseDto).build();
        }
        else {
            return Response.status(403).build();
        }
    }

    @POST
    @Path("playlists")
    @Consumes("application/json")
    public Response addPlaylist(@QueryParam("token") String token, PlaylistRequestDto request) throws SQLException {
        if(authenticationService.performAuthentication(token)) {
            PlaylistResponseDto playlistResponseDto = playlistDAO.addPlaylistToDatabase(request, token);
            return Response.ok().entity(playlistResponseDto).build();
        }
        else {
            return Response.status(403).build();
        }
    }
}
