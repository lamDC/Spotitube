package oose.dea.resources.dataresources;

import oose.dea.resources.dto.LoginRequestDto;
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

    public UserModel login(LoginRequestDto request) throws SQLException {
        UserModel userModel = new UserModel();
        tokenDAO.generateTokenForUser(request);

        ResultSet resultSet = null;
        PreparedStatement st = null;
        java.sql.Connection cnEmps = connection;
        String sql = "SELECT * FROM [USER]";

        try {
            st = cnEmps.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        }
        if(userModel.getToken() == null){
            userModel.setToken("error");
        }

        return userModel;
    }

}
