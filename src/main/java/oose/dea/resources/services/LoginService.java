package oose.dea.resources.services;

import oose.dea.resources.dataresources.LoginDAO;
import oose.dea.resources.models.UserModel;

import javax.inject.Inject;

public class LoginService {

    @Inject
    public void setLoginDAO(LoginDAO loginDAO){ this.loginDAO = loginDAO; }

    private LoginDAO loginDAO;

    public UserModel performFirstLogin(){

    }
}
