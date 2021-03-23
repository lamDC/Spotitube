package oose.dea.resources.dataresources;

import oose.dea.resources.dto.PlaylistRequestDto;
import oose.dea.resources.dto.PlaylistResponseDto;
import oose.dea.resources.exceptions.PlaylistException;
import oose.dea.resources.models.PlaylistModel;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistDAO {

    private Connection connection;

    @Inject
    public PlaylistDAO(DatabaseConnection databaseConnection){
        connection = databaseConnection.getConnection();
    }

    public PlaylistResponseDto getPlaylists() throws PlaylistException {

        String sql = "SELECT DISTINCT P.PLAYLIST_ID, P.NAME, P.OWNER FROM PLAYLIST P " +
                "LEFT OUTER JOIN TRACK_IN_PLAYLIST TIP ON P.PLAYLIST_ID = TIP.PLAYLIST_ID";
        try{
            return leesPlaylists(sql);
        } catch(SQLException e){
            throw new PlaylistException(400);
        }
    }

    public PlaylistResponseDto editPlaylist(PlaylistRequestDto playlistRequestDto, int id) throws SQLException, PlaylistException {
        PreparedStatement st = null;
        java.sql.Connection cnEmps = connection;

        String deleteSql = "UPDATE PLAYLIST SET NAME = ? WHERE PLAYLIST_ID = " + id;
        try {
            st = cnEmps.prepareStatement(deleteSql);
            st.setString(1, playlistRequestDto.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        st.execute();

        String sql = "SELECT DISTINCT P.PLAYLIST_ID, P.NAME, P.OWNER FROM PLAYLIST P " +
                "LEFT OUTER JOIN TRACK_IN_PLAYLIST TIP ON P.PLAYLIST_ID = TIP.PLAYLIST_ID";

        try{
            return leesPlaylists(sql);
        } catch(SQLException e){
            throw new PlaylistException(400);
        }
    }

    public PlaylistResponseDto deletePlaylistFromDatabase(int id) throws SQLException, PlaylistException {
        ResultSet resultSet = null;
        PreparedStatement st = null;
        java.sql.Connection cnEmps = connection;

        String deleteSql = "DELETE FROM TRACK_IN_PLAYLIST WHERE PLAYLIST_ID = " + id;
        try {
            st = cnEmps.prepareStatement(deleteSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        st.execute();

        String deleteSql2 = "DELETE FROM PLAYLIST WHERE PLAYLIST_ID = " + id;
        try {
            st = cnEmps.prepareStatement(deleteSql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        st.execute();

        String sql = "SELECT DISTINCT P.PLAYLIST_ID, P.NAME, P.OWNER FROM PLAYLIST P " +
        "LEFT OUTER JOIN TRACK_IN_PLAYLIST TIP ON P.PLAYLIST_ID = TIP.PLAYLIST_ID";

        try{
            return leesPlaylists(sql);
        } catch(SQLException e){
            throw new PlaylistException(400);
        }
    }
    public PlaylistResponseDto addPlaylistToDatabase(PlaylistRequestDto playlistRequestDto, String token) throws SQLException, PlaylistException {
        String user = geefUserVanToken(token);
        String playlistName = playlistRequestDto.getName();

        ResultSet resultSet = null;
        PreparedStatement st = null;
        java.sql.Connection cnEmps = connection;

        String addSql = "INSERT INTO PLAYLIST VALUES(?, ?, 1)";

        try {
            st = cnEmps.prepareStatement(addSql);
            st.setString(1, user);
            st.setString(2, playlistName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        st.execute();

        String sql = "SELECT DISTINCT P.PLAYLIST_ID, P.NAME, P.OWNER FROM PLAYLIST P" +
                " LEFT OUTER JOIN TRACK_IN_PLAYLIST TIP ON P.PLAYLIST_ID = TIP.PLAYLIST_ID";
        try{
            return leesPlaylists(sql);
        } catch(SQLException e){
            throw new PlaylistException(400);
        }    }

    public PlaylistResponseDto leesPlaylists(String sql) throws SQLException {
        ResultSet resultSet = null;
        PreparedStatement st = null;
        java.sql.Connection cnEmps = connection;

        PlaylistResponseDto playlistResponseDto = new PlaylistResponseDto();
        ArrayList<PlaylistModel> playlistArray = playlistResponseDto.getPlaylists();

        try {
            st = cnEmps.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resultSet = st.executeQuery();
        while (resultSet.next())
        {
            PlaylistModel playlist = new PlaylistModel();
            playlist.setId(resultSet.getInt("PLAYLIST_ID"));
            playlist.setName(resultSet.getString("NAME"));
            if(resultSet.getBoolean("OWNER")){
                playlist.setOwner(true);
            }
            else {
                playlist.setOwner(false);
            }
            playlist.setTracks(new ArrayList<>());
            playlistArray.add(playlist);

        }
        playlistResponseDto.setLength(6445);
        playlistResponseDto.setPlaylists(playlistArray);

        return playlistResponseDto;
    }
    public String geefUserVanToken(String token) throws SQLException {
        ResultSet resultSet = null;
        PreparedStatement st = null;
        java.sql.Connection cnEmps = connection;
        String user = "";
        String sql = "SELECT * FROM [USER] WHERE TOKEN = ?";
        try {
            st = cnEmps.prepareStatement(sql);
            st.setString(1, token);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resultSet = st.executeQuery();
        while (resultSet.next())
        {
            user = resultSet.getString("USERNAME");
        }

        return user;
    }


}
