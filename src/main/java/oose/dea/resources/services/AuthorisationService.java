package oose.dea.resources.services;
import oose.dea.resources.dataresources.TokenDAO;

import javax.inject.Inject;
import java.sql.SQLException;

public class AuthorisationService {

    //deze service van trackauthentication moet worden overgezet naar trackdao      Klaar, toch?

    private TokenDAO tokenDAO;

    @Inject
    public void setTokenDAO(TokenDAO tokenDAO){
        this.tokenDAO = tokenDAO;
    }

    public boolean performPlaylistAuthorisation(String token, int playlist_id) throws SQLException {
        return tokenDAO.playlistAuthorisation(token, playlist_id);

    }

    public boolean performTrackAuthentication(String token, int track_id) throws SQLException {
        return tokenDAO.trackAuthentication(token, track_id);
    }

    public boolean performAuthentication(String token) throws SQLException {
        return tokenDAO.performAuthentication(token);
    }
}
