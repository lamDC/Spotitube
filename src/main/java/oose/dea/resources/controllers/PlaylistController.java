package oose.dea.resources.controllers;

import oose.dea.resources.dataresources.PlaylistDAO;
import oose.dea.resources.dto.PlaylistRequestDto;
import oose.dea.resources.dto.PlaylistResponseDto;
import oose.dea.resources.exceptions.AuthorisationException;
import oose.dea.resources.exceptions.PlaylistException;
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
    public Response Playlist(@QueryParam("token") String token){
        try{
            authorisationService.performAuthentication(token);
            PlaylistResponseDto playlistResponseDto = playlistService.performGetPlaylists();   // deze doorlussen van controller naar service naar dao
            return Response.ok().entity(playlistResponseDto).build();

        } catch(PlaylistException e){
            return e.toResponse(e);
        } catch(AuthorisationException e){
            return e.toResponse(e);
        }
    }

    @DELETE
    @Path("playlists/{id}")
    @Produces("application/json")
    public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token){
        try{
            authorisationService.performPlaylistAuthorisation(token, id);
            PlaylistResponseDto playlistResponseDto = playlistService.performDeletePlaylistFromDatabase(id);
            return Response.ok().entity(playlistResponseDto).build();

        } catch(PlaylistException e){
            return e.toResponse(e);
        } catch(AuthorisationException e){
            return e.toResponse(e);
        }
    }

    @PUT
    @Path("playlists/{id}")
    @Consumes("application/json")
    public Response editPlaylist(@PathParam("id") int id, @QueryParam("token") String token, PlaylistRequestDto request){
        try{
            authorisationService.performPlaylistAuthorisation(token, id);
            PlaylistResponseDto playlistResponseDto = playlistService.performEditPlaylist(request, id);
            return Response.ok().entity(playlistResponseDto).build();

        } catch(AuthorisationException e){
            return e.toResponse(e);
        } catch(PlaylistException e){
            return e.toResponse(e);
        }

    }

    @POST
    @Path("playlists")
    @Consumes("application/json")
    public Response addPlaylist(@QueryParam("token") String token, PlaylistRequestDto request){
        try{
            authorisationService.performAuthentication(token);
            PlaylistResponseDto playlistResponseDto = playlistService.performAddPlaylistToDatabase(request, token);
            return Response.ok().entity(playlistResponseDto).build();

        } catch(AuthorisationException e){
            return e.toResponse(e);
        } catch(PlaylistException e){
            return e.toResponse(e);
        }

    }
}
