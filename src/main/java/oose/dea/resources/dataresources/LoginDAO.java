package oose.dea.resources.dataresources;

import oose.dea.resources.dto.LoginRequestDto;
import oose.dea.resources.exceptions.AuthorisationException;
import oose.dea.resources.exceptions.LoginException;
import oose.dea.resources.models.UserModel;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    private Connection connection;

    @Inject
    public void setTokenDAO(TokenDAO tokenDAO) {
        this.tokenDAO = tokenDAO;
    }

    private TokenDAO tokenDAO;

    @Inject
    public LoginDAO(DatabaseConnection databaseConnection){
        connection = databaseConnection.getConnection();
    }

    public UserModel login(LoginRequestDto request) throws LoginException {
        UserModel userModel = new UserModel();
        try {
            tokenDAO.generateTokenForUser(request);
        } catch(AuthorisationException e){
            throw new LoginException(e.statuscode);
        }

        ResultSet resultSet = null;
        PreparedStatement st = null;
        java.sql.Connection cnEmps = connection;
        String sql = "SELECT * FROM [USER] WHERE USERNAME = ? AND PASSWORD = ?";

        try {
            st = cnEmps.prepareStatement(sql);
            st.setString(1, request.getUser());
            st.setString(2, request.getPassword());
            resultSet = st.executeQuery();
            while (resultSet.next())
            {
                String     username   = resultSet.getString("USERNAME");
                String     password   = resultSet.getString("PASSWORD");
                if(username.equals(request.getUser()) && password.equals(request.getPassword())){
                    String     token   = resultSet.getString("TOKEN");
                    String     user   = resultSet.getString("NAME");
                    userModel.setToken(token);

                    userModel.setUser(user);
                }
                else {
                    userModel.setToken(null);
                    userModel.setUser(null);
                }
            }
            if(userModel.getToken() == null)
                throw new LoginException(401);

            return userModel;
        } catch (SQLException e) {
            throw new LoginException(e.getErrorCode());
        }
    }

}
