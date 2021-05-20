//package oose.dea.serviceTests;
//
//import oose.dea.resources.dataresources.PlaylistDAO;
//import oose.dea.resources.dataresources.TokenDAO;
//import oose.dea.resources.services.AuthorisationService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.sql.SQLException;
//
//public class AuthorisationTest {
//    private AuthorisationService service;
//    private TokenDAO tokenDAOMock;
//    private static final int PLAYLIST_ID = 1;
//    private static final int TRACK_ID = 2;
//    private static final String user = "diegocup";
//    private static final String TOKEN = "7057a9bd-a879-43df-8bec-604ead563e7c";
//
//    @BeforeEach
//    void setup() throws SQLException {
//        tokenDAOMock = Mockito.mock(TokenDAO.class);
//        service = new AuthorisationService();
//        service.setTokenDAO(tokenDAOMock);
//    }
//
//    @Test
//    public void doesPlaylistAuthorisationReturnCorrectBooleanCorrectToken() throws SQLException{
//        //Setup
//        boolean returnValue = true;
//        Mockito.when(tokenDAOMock.getUserByToken(TOKEN)).thenReturn(user);
//
//        //Test
//        boolean actualReturn = service.performPlaylistAuthorisation(TOKEN, PLAYLIST_ID);
//
//        //Verify
//        Assertions.assertEquals(returnValue, actualReturn);
//    }
//
//    @Test
//    public void doesTrackAuthorisationReturnCorrectBooleanCorrectToken() throws SQLException{
//        //Setup
//        boolean returnValue = true;
//        String user = "diegocup";
//        Mockito.when(tokenDAOMock.getUserByToken(TOKEN)).thenReturn(user);
//
//        //Test
//        boolean actualReturn = service.performTrackAuthentication(TOKEN, TRACK_ID);
//
//        //Verify
//        Assertions.assertEquals(returnValue, actualReturn);
//    }
//}
