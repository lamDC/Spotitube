package oose.dea.resources.dataresources;

import oose.dea.resources.dto.LoginRequestDto;
import oose.dea.resources.exceptions.AuthorisationException;
import oose.dea.resources.exceptions.PlaylistException;
import oose.dea.resources.models.PlaylistModel;
import oose.dea.resources.models.TokenModel;
import oose.dea.resources.models.UserModel;
import oose.dea.resources.util.TokenGenerator;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenDAO {
    private Connection connection;

    @Inject
    public void TokenDAO(DatabaseConnection databaseConnection){
        connection = databaseConnection.getConnection();
    }

    public void performAuthentication(String token) throws SQLException, AuthorisationException {
        boolean isTokenVanEenUser = false;
        ResultSet resultSet = null;
        PreparedStatement st = null;
        DatabaseConnection connection = new DatabaseConnection();
        java.sql.Connection cnEmps = connection.getConnection();
        String sql = "SELECT TOKEN FROM [USER] WHERE TOKEN = ?";

        try {
            st = cnEmps.prepareStatement(sql);
            st.setString(1, token);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        resultSet = st.executeQuery();
        while (resultSet.next())
        {
            String     tokenDB      = resultSet.getString("TOKEN");
            if(tokenDB.equals(token)){
                isTokenVanEenUser = true;
            }
        }
        if(!isTokenVanEenUser)
            throw new AuthorisationException(403);
    }

    public void generateTokenForUser(LoginRequestDto request) throws SQLException {
        TokenModel tokenModel = new TokenModel();
        TokenGenerator util = new TokenGenerator();

        tokenModel.setToken(util.generateToken());
        tokenModel.setUsername(request.getUser());

        PreparedStatement st = null;
        java.sql.Connection cnEmps = connection;

        String deleteSql = "UPDATE [USER] SET TOKEN = ? WHERE USERNAME = ?";
        try {
            st = cnEmps.prepareStatement(deleteSql);
            st.setString(1, tokenModel.getToken());
            st.setString(2, request.getUser());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        st.execute();

    }

    public String getUserByToken(String token) throws SQLException {
        ResultSet resultSet = null;
        PreparedStatement st = null;
        java.sql.Connection cnEmps = connection;
        String sql = "SELECT USERNAME FROM [USER] WHERE TOKEN = ?";

        try {
            st = cnEmps.prepareStatement(sql);
            st.setString(1, token);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        UserModel user  = new UserModel();
        user.setToken(token);
        resultSet = st.executeQuery();
        while (resultSet.next())
        {
            user.setUser(resultSet.getString("USERNAME"));
        }
        return user.getUser();
    }

    public boolean playlistAuthorisation(String token, int playlist_id) throws SQLException, AuthorisationException {
        String user = getUserByToken(token);
        boolean isTokenVanUser = false;

        ResultSet resultSet = null;
        PreparedStatement st = null;
        java.sql.Connection cnEmps = connection;
        String sql = "SELECT USERNAME, OWNER FROM PLAYLIST WHERE PLAYLIST_ID = " + playlist_id;

        try {
            st = cnEmps.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resultSet = st.executeQuery();
        while (resultSet.next())
        {
            String playlistUser = resultSet.getString("USERNAME");
            if(playlistUser.equals(user)){
                boolean owner = resultSet.getBoolean("OWNER");
                if(owner){
                    isTokenVanUser = true;
                }
            }
        }
        if(!isTokenVanUser)
            throw new AuthorisationException(403);
        return isTokenVanUser;
    }

    public boolean trackAuthentication(String token, int track_id) throws SQLException {
        String user = getUserByToken(token);
        boolean isTokenVanUser = false;

        ResultSet resultSet = null;
        PreparedStatement st = null;
        DatabaseConnection connection = new DatabaseConnection();
        java.sql.Connection cnEmps = connection.getConnection();
        String sql = "SELECT P.OWNER FROM [USER] U INNER JOIN PLAYLIST P ON U.USERNAME = P.USERNAME INNER JOIN TRACK_IN_PLAYLIST TIP ON P.PLAYLIST_ID " +
                "= TIP.PLAYLIST_ID WHERE TRACK_ID = " + track_id +
                " AND P.USERNAME = ?";

        try {
            st = cnEmps.prepareStatement(sql);
            st.setString(1, user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resultSet = st.executeQuery();
        while (resultSet.next())
        {
            boolean owner = resultSet.getBoolean("OWNER");
            if(owner){
                isTokenVanUser = true;
            }
        }
        return isTokenVanUser;
    }

}
