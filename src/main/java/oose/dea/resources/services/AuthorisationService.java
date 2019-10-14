package oose.dea.resources.services;
import oose.dea.resources.dataresources.DatabaseConnection;
import oose.dea.resources.dataresources.PlaylistDAO;
import oose.dea.resources.dataresources.TokenDAO;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorisationService {

    private TokenDAO tokenDAO;

    @Inject
    public void setTokenDAO(TokenDAO tokenDAO){
        this.tokenDAO = tokenDAO;
    }

    public boolean performPlaylistAuthorisation(String token, int playlist_id) throws SQLException {
        return tokenDAO.playlistAuthorisation(token, playlist_id);

    }

    public boolean performTrackAuthentication(String token, int track_id) throws SQLException {

        String user = tokenDAO.getUserByToken(token);
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
