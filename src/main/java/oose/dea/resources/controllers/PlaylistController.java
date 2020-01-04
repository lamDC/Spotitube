package oose.dea.resources.controllers;

import oose.dea.resources.dataresources.PlaylistDAO;
import oose.dea.resources.dto.PlaylistRequestDto;
import oose.dea.resources.dto.PlaylistResponseDto;
import oose.dea.resources.services.AuthorisationService;
import oose.dea.resources.services.PlaylistService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/")
public class PlaylistController {

    private PlaylistDAO playlistDAO;
    private AuthorisationService authorisationService;
    private PlaylistService playlistService;

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO){ this.playlistDAO = playlistDAO; }

    @Inject
    public void setPlaylistService(PlaylistService playlistService){ this.playlistService = playlistService;}

    @Inject
    public void setAuthorisationService(AuthorisationService authorisationService){
        this.authorisationService = authorisationService;
    }

    @GET
    @Path("playlists")
    @Produces("application/json")
    public Response Playlist(@QueryParam("token") String token) throws SQLException {
        if(authorisationService.performAuthentication(token)) {
            PlaylistResponseDto playlistResponseDto = playlistService.performGetPlaylists();   // deze doorlussen van controller naar service naar dao
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
            PlaylistResponseDto playlistResponseDto = playlistService.performDeletePlaylistFromDatabase(id);
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
            PlaylistResponseDto playlistResponseDto = playlistService.performEditPlaylist(request, id);
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
        if(authorisationService.performAuthentication(token)) {
            PlaylistResponseDto playlistResponseDto = playlistService.performAddPlaylistToDatabase(request, token);
            return Response.ok().entity(playlistResponseDto).build();
        }
        else {
            return Response.status(403).build();
        }
    }
}
