package oose.dea.controllerTests;

import oose.dea.resources.dataresources.TrackDAO;
import oose.dea.resources.controllers.TrackController;
import oose.dea.resources.dto.TrackRequestDto;
import oose.dea.resources.dto.TrackResponseDto;
import oose.dea.resources.models.TrackModel;
import oose.dea.resources.services.AuthorisationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TrackControllerTest {

    private static final int PLAYLIST_ID = 1;
    private static final int TRACK_ID = 3;
    private AuthorisationService authorisationService;
    private static final String TOKEN = "7057a9bd-a879-43df-8bec-604ead563e7c";
    private TrackDAO trackDAOMock;
    private TrackController trackController;

    @BeforeEach
    void setup(){
        trackDAOMock = Mockito.mock(TrackDAO.class);
        trackController = new TrackController();
        trackController.setTrackDAO(trackDAOMock);
        authorisationService = Mockito.mock(AuthorisationService.class);
        trackController.setAuthorisationService(authorisationService);

    }
    @Test
    public void doesWrongTokenAuthenticationGivesCorrectErrorGeefTracks() throws SQLException {
        //Setup
        boolean correctToken = false;
        int status = 403;
        String fakeToken = "fakeToken";

        Mockito.when(authorisationService.performAuthentication(fakeToken)).thenReturn(correctToken);

        //Test
        Response response = trackController.geefTracks(PLAYLIST_ID, fakeToken);

        //Verify
        Mockito.verify(authorisationService).performAuthentication(fakeToken);
        assertEquals(status, response.getStatus());
    }

    @Test
    public void doesTrackDAOReturnAvailableTracksResponse() throws SQLException {
        //Setup
        TrackResponseDto trackResponseDto = new TrackResponseDto();
        ArrayList<TrackModel> tracks = new ArrayList<>();
        TrackModel trackModel = new TrackModel();
        tracks.add(trackModel);
        trackResponseDto.setTracks(tracks);
        boolean expectedReturn = true;

        Mockito.when(trackDAOMock.geefBeschikbareTracks(PLAYLIST_ID)).thenReturn(trackResponseDto);
        Mockito.when(authorisationService.performAuthentication(TOKEN)).thenReturn(expectedReturn);

        //Test
        Response response = trackController.geefTracks(PLAYLIST_ID, TOKEN);

        //Verify
        Mockito.verify(trackDAOMock).geefBeschikbareTracks(PLAYLIST_ID);
        assertEquals(trackResponseDto, response.getEntity());

    }
    @Test
    public void doesWrongTokenAuthenticationGivesCorrectErrorTracksVanPlaylist() throws SQLException {
        //Setup
        boolean correctToken = false;
        int status = 403;
        String fakeToken = "fakeToken";

        Mockito.when(authorisationService.performAuthentication(fakeToken)).thenReturn(correctToken);

        //Test
        Response response = trackController.tracksVanPlaylist(PLAYLIST_ID, fakeToken);

        //Verify
        Mockito.verify(authorisationService).performAuthentication(fakeToken);
        assertEquals(status, response.getStatus());
    }

    @Test
    public void doesTrackDAOReturnCorrectTrackResponse() throws SQLException {
        //Setup
        TrackResponseDto trackResponseDto = new TrackResponseDto();
        ArrayList<TrackModel> tracks = new ArrayList<>();
        TrackModel trackModel = new TrackModel();
        tracks.add(trackModel);
        trackResponseDto.setTracks(tracks);
        boolean expectedReturn = true;

        Mockito.when(trackDAOMock.getTrackVanPlaylist(PLAYLIST_ID)).thenReturn(trackResponseDto);
        Mockito.when(authorisationService.performAuthentication(TOKEN)).thenReturn(expectedReturn);

        //Test
        Response response = trackController.tracksVanPlaylist(PLAYLIST_ID, TOKEN);

        //Verify
        Mockito.verify(trackDAOMock).getTrackVanPlaylist(PLAYLIST_ID);
        assertEquals(trackResponseDto, response.getEntity());
    }

    @Test
    public void doesWrongTokenAuthenticationGivesCorrectErrorDeleteTrack() throws SQLException {
        //Setup
        boolean correctToken = false;
        int status = 403;
        String fakeToken = "fakeToken";

        Mockito.when(authorisationService.performTrackAuthentication(fakeToken, TRACK_ID)).thenReturn(correctToken);

        //Test
        Response response = trackController.deleteTrack(PLAYLIST_ID,TRACK_ID, fakeToken);

        //Verify
        Mockito.verify(authorisationService).performTrackAuthentication(fakeToken, TRACK_ID);
        assertEquals(status, response.getStatus());
    }

    @Test
    public void doesTrackDAOReturnDeleteTrackResponse() throws SQLException {
        //Setup
        TrackResponseDto trackResponseDto = new TrackResponseDto();
        ArrayList<TrackModel> tracks = new ArrayList<>();
        TrackModel trackModel = new TrackModel();
        tracks.add(trackModel);
        trackResponseDto.setTracks(tracks);
        boolean expectedReturn = true;

        Mockito.when(trackDAOMock.deleteTrackVanPlaylist(PLAYLIST_ID, TRACK_ID)).thenReturn(trackResponseDto);
        Mockito.when(authorisationService.performTrackAuthentication(TOKEN, TRACK_ID)).thenReturn(expectedReturn);

        //Test
        Response response = trackController.deleteTrack(PLAYLIST_ID, TRACK_ID, TOKEN);

        //Verify
        Mockito.verify(trackDAOMock).deleteTrackVanPlaylist(PLAYLIST_ID, TRACK_ID);
        assertEquals(trackResponseDto, response.getEntity());
    }

    @Test
    public void doesWrongTokenAuthenticationGivesCorrectErrorAddTrack() throws SQLException {
        //Setup
        boolean correctToken = false;
        int status = 403;
        String fakeToken = "fakeToken";
        TrackRequestDto trackRequestDto = new TrackRequestDto();

        Mockito.when(authorisationService.performPlaylistAuthorisation(fakeToken, PLAYLIST_ID)).thenReturn(correctToken);

        //Test
        Response response = trackController.addTrackToPlaylist(fakeToken, PLAYLIST_ID, trackRequestDto);

        //Verify
        Mockito.verify(authorisationService).performPlaylistAuthorisation(fakeToken, PLAYLIST_ID);
        assertEquals(status, response.getStatus());
    }

    @Test
    public void doesTrackDAOReturnAddTrackResponse() throws SQLException {
        //Setup
        TrackResponseDto trackResponseDto = new TrackResponseDto();
        TrackRequestDto trackRequestDto = new TrackRequestDto();
        ArrayList<TrackModel> tracks = new ArrayList<>();
        TrackModel trackModel = new TrackModel();
        tracks.add(trackModel);
        trackResponseDto.setTracks(tracks);
        boolean expectedReturn = true;

        Mockito.when(trackDAOMock.addTrackToPlaylist(trackRequestDto, PLAYLIST_ID)).thenReturn(trackResponseDto);
        Mockito.when(authorisationService.performPlaylistAuthorisation(TOKEN, PLAYLIST_ID)).thenReturn(expectedReturn);

        //Test
        Response response = trackController.addTrackToPlaylist(TOKEN, PLAYLIST_ID, trackRequestDto);

        //Verify
        Mockito.verify(trackDAOMock).addTrackToPlaylist(trackRequestDto, PLAYLIST_ID);
        assertEquals(trackResponseDto, response.getEntity());
    }

}
