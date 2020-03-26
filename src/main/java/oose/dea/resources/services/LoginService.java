package oose.dea.resources.services;

import oose.dea.resources.dataresources.LoginDAO;
import oose.dea.resources.dto.LoginRequestDto;
import oose.dea.resources.exceptions.LoginException;
import oose.dea.resources.models.UserModel;

import javax.inject.Inject;
import java.sql.SQLException;

public class LoginService {

    @Inject
    public void setLoginDAO(LoginDAO loginDAO){ this.loginDAO = loginDAO; }

    private LoginDAO loginDAO;

    public UserModel performFirstLogin(LoginRequestDto request) throws SQLException, LoginException {
    return loginDAO.login(request);
    }
}
