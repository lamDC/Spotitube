package oose.dea.resources.services;

import oose.dea.resources.dataresources.TokenDAO;

import javax.inject.Inject;
import java.sql.SQLException;

public class AuthenticationService {

    private TokenDAO tokenDAO;

    @Inject
    public void setTokenDAO(TokenDAO tokenDAO){
        this.tokenDAO = tokenDAO;
    }

    public boolean performAuthentication(String token) throws SQLException {
        return tokenDAO.performAuthentication(token);
    }

}
