package oose.dea.resources.services;
import oose.dea.resources.dataresources.TokenDAO;
import oose.dea.resources.exceptions.AuthorisationException;

import javax.inject.Inject;
import java.sql.SQLException;

public class AuthorisationService {

    //deze service van trackauthentication moet worden overgezet naar trackdao      Klaar, toch?

    private TokenDAO tokenDAO;

    @Inject
    public void setTokenDAO(TokenDAO tokenDAO){
        this.tokenDAO = tokenDAO;
    }

    public void performPlaylistAuthorisation(String token, int playlist_id) throws AuthorisationException {
        tokenDAO.playlistAuthorisation(token, playlist_id);
    }

    public boolean performTrackAuthentication(String token, int track_id) throws AuthorisationException {
        return tokenDAO.trackAuthentication(token, track_id);
    }

    public void performAuthentication(String token) throws AuthorisationException {
        tokenDAO.performAuthentication(token);
    }
}
