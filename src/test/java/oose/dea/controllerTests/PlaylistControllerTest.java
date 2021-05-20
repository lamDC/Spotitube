//package oose.dea.controllerTests;
//
//import oose.dea.resources.dataresources.PlaylistDAO;
//import oose.dea.resources.controllers.PlaylistController;
//import oose.dea.resources.dto.PlaylistRequestDto;
//import oose.dea.resources.dto.PlaylistResponseDto;
//import oose.dea.resources.services.AuthorisationService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import javax.ws.rs.core.Response;
//import java.sql.SQLException;
//
//import static org.junit.Assert.assertEquals;
//
//public class PlaylistControllerTest {
//    private static final int PLAYLIST_ID = 1;
//    private PlaylistController playlistController;
//    private PlaylistDAO playlistDAOMock;
//    private AuthorisationService authorisationService;
//    private static final String TOKEN = "7057a9bd-a879-43df-8bec-604ead563e7c";
//
//    @BeforeEach
//    void setup() {
//        playlistDAOMock = Mockito.mock(PlaylistDAO.class);
//        playlistController = new PlaylistController();
//        playlistController.setPlaylistDAO(playlistDAOMock);
//        authorisationService = Mockito.mock(AuthorisationService.class);
//        playlistController.setAuthorisationService(authorisationService);
//    }
//
//    @Test
//    public void doesWrongTokenAuthenticationGivesCorrectErrorPlaylist() throws SQLException {
//        //Setup
//        boolean correctToken = false;
//        int status = 403;
//        String fakeToken = "fakeToken";
//
//        Mockito.when(authorisationService.performAuthentication(fakeToken)).thenReturn(correctToken);
//
//        //Test
//        Response response = playlistController.Playlist(fakeToken);
//
//        //Verify
//        Mockito.verify(authorisationService).performAuthentication(fakeToken);
//        assertEquals(status, response.getStatus());
//    }
//
//    @Test
//    public void doesPlaylistDAOReturnPlaylistResponse() throws SQLException {
//        //Setup
//        PlaylistResponseDto playlistResponseDto = new PlaylistResponseDto();
//
//        Mockito.when(authorisationService.performAuthentication(TOKEN)).thenReturn(true);
//        Mockito.when(playlistDAOMock.getPlaylists()).thenReturn(playlistResponseDto);
//
//        //Test
//        Response response = playlistController.Playlist(TOKEN);
//
//        //Verify
//        Mockito.verify(playlistDAOMock).getPlaylists();
//        assertEquals(playlistResponseDto, response.getEntity());
//    }
//
//    @Test
//    public void doesWrongTokenAuthorisationGivesCorrectErrorDeletePlaylist() throws SQLException {
//        //Setup
//        boolean correctToken = false;
//        int status = 403;
//        String fakeToken = "fakeToken";
//
//        Mockito.when(authorisationService.performPlaylistAuthorisation(fakeToken, PLAYLIST_ID)).thenReturn(correctToken);
//
//        //Test
//        Response response = playlistController.deletePlaylist(PLAYLIST_ID, fakeToken);
//
//        //Verify
//        Mockito.verify(authorisationService).performPlaylistAuthorisation(fakeToken, PLAYLIST_ID);
//        assertEquals(status, response.getStatus());
//    }
//
//    @Test
//    public void doesPlaylistDAOReturnDeletePlaylistResponse() throws SQLException {
//        //Setup
//        PlaylistResponseDto playlistResponseDto = new PlaylistResponseDto();
//        boolean expectedReturn = true;
//
//        Mockito.when(playlistDAOMock.deletePlaylistFromDatabase(PLAYLIST_ID)).thenReturn(playlistResponseDto);
//        Mockito.when(authorisationService.performPlaylistAuthorisation(TOKEN, PLAYLIST_ID)).thenReturn(expectedReturn);
//
//        //Test
//        Response response = playlistController.deletePlaylist(PLAYLIST_ID, TOKEN);
//
//        //Verify
//        Mockito.verify(playlistDAOMock).deletePlaylistFromDatabase(PLAYLIST_ID);
//        assertEquals(playlistResponseDto, response.getEntity());
//    }
//
//    @Test
//    public void doesWrongTokenAuthorisationGivesCorrectErrorEditPlaylist() throws SQLException {
//        //Setup
//        boolean correctToken = false;
//        int status = 403;
//        PlaylistRequestDto playlistRequestDto  = new PlaylistRequestDto();
//        String fakeToken = "fakeToken";
//
//        Mockito.when(authorisationService.performPlaylistAuthorisation(fakeToken, PLAYLIST_ID)).thenReturn(correctToken);
//
//        //Test
//        Response response = playlistController.editPlaylist(PLAYLIST_ID, fakeToken, playlistRequestDto);
//
//        //Verify
//        Mockito.verify(authorisationService).performPlaylistAuthorisation(fakeToken, PLAYLIST_ID);
//        assertEquals(status, response.getStatus());
//    }
//
//    @Test
//    public void doesPlaylistDAOReturnEditPlaylistResponse() throws SQLException {
//        //Setup
//        PlaylistResponseDto playlistResponseDto = new PlaylistResponseDto();
//        PlaylistRequestDto playlistRequestDto = new PlaylistRequestDto();
//        boolean expectedReturn = true;
//
//        Mockito.when(playlistDAOMock.editPlaylist(playlistRequestDto, PLAYLIST_ID)).thenReturn(playlistResponseDto);
//        Mockito.when(authorisationService.performPlaylistAuthorisation(TOKEN, PLAYLIST_ID)).thenReturn(expectedReturn);
//
//        //Test
//        Response response = playlistController.editPlaylist(PLAYLIST_ID, TOKEN, playlistRequestDto);
//
//        //Verify
//        Mockito.verify(playlistDAOMock).editPlaylist(playlistRequestDto, PLAYLIST_ID);
//        assertEquals(playlistResponseDto, response.getEntity());
//    }
//
//    @Test
//    public void doesWrongTokenAuthenticationGivesCorrectErrorAddPlaylist() throws SQLException {
//        //Setup
//        boolean correctToken = false;
//        int status = 403;
//        PlaylistRequestDto playlistRequestDto  = new PlaylistRequestDto();
//        String fakeToken = "fakeToken";
//
//        Mockito.when(authorisationService.performAuthentication(fakeToken)).thenReturn(correctToken);
//
//        //Test
//        Response response = playlistController.addPlaylist(fakeToken, playlistRequestDto);
//
//        //Verify
//        Mockito.verify(authorisationService).performAuthentication(fakeToken);
//        assertEquals(status, response.getStatus());
//    }
//
//    @Test
//    public void doesPlaylistDAOReturnAddPlaylistResponse() throws SQLException {
//        //Setup
//        PlaylistResponseDto playlistResponseDto = new PlaylistResponseDto();
//        PlaylistRequestDto playlistRequestDto = new PlaylistRequestDto();
//        boolean expectedReturn = true;
//
//        Mockito.when(playlistDAOMock.addPlaylistToDatabase(playlistRequestDto, TOKEN)).thenReturn(playlistResponseDto);
//        Mockito.when(authorisationService.performAuthentication(TOKEN)).thenReturn(expectedReturn);
//
//        //Test
//        Response response = playlistController.addPlaylist(TOKEN, playlistRequestDto);
//
//        //Verify
//        Mockito.verify(playlistDAOMock).addPlaylistToDatabase(playlistRequestDto, TOKEN);
//        assertEquals(playlistResponseDto, response.getEntity());
//    }
//}
