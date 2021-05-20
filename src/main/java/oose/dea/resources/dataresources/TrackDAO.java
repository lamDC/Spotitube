package oose.dea.resources.dataresources;

import oose.dea.resources.dto.TrackRequestDto;
import oose.dea.resources.dto.TrackResponseDto;
import oose.dea.resources.exceptions.TrackException;
import oose.dea.resources.models.TrackModel;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrackDAO {
    private Connection connection;

    @Inject
    public void TrackDAO(DatabaseConnection databaseConnection){
        connection = databaseConnection.getConnection();
    }

    public TrackResponseDto geefBeschikbareTracks(int id) throws TrackException {
        TrackResponseDto trackResponseDto = new TrackResponseDto();

        String sql = "SELECT DISTINCT TRACK_ID, TITLE, PERFORMER, DURATION, ALBUM, PLAYCOUNT, PUBLICATIONDATE, " +
        "DESCRIPTION, OFFLINEAVAILABLE FROM TRACK " +
        "WHERE TRACK_ID NOT IN(SELECT TRACK_ID FROM TRACK_IN_PLAYLIST " +
        "WHERE PLAYLIST_ID = " + id + ")";

        trackResponseDto.setTracks(leesTracks(sql));
        return trackResponseDto;
    }

    public ArrayList<TrackModel> tracksVanPlaylist(int playlist_id) throws TrackException {

        String sql = "SELECT * FROM TRACK T INNER JOIN TRACK_IN_PLAYLIST TIP ON T.TRACK_ID = TIP.TRACK_ID WHERE PLAYLIST_ID = " + playlist_id;
        return leesTracks(sql);
    }

    public TrackResponseDto getTrackVanPlaylist(int playlist_id) throws TrackException {
        ArrayList<TrackModel> tracks = tracksVanPlaylist(playlist_id);
        TrackResponseDto trackResponseDto = new TrackResponseDto();
        trackResponseDto.setTracks(tracks);
        return trackResponseDto;
    }


    public ArrayList<TrackModel> leesTracks(String sql) throws TrackException {
        ResultSet resultSet = null;
        PreparedStatement st = null;
        java.sql.Connection cnEmps = connection;
        ArrayList<TrackModel> trackModels = new ArrayList<>();

        try {
            st = cnEmps.prepareStatement(sql);
            resultSet = st.executeQuery();
            while (resultSet.next())
            {
                TrackModel trackModel = new TrackModel();
                trackModel.setId(resultSet.getInt("TRACK_ID"));
                trackModel.setTitle(resultSet.getString("TITLE"));
                trackModel.setPerformer(resultSet.getString("PERFORMER"));
                trackModel.setDuration(resultSet.getInt("DURATION"));
                trackModel.setAlbum(resultSet.getString("ALBUM"));
                if(resultSet.getInt("PLAYCOUNT") == 0) {
                    trackModel.setPlaycount(resultSet.getInt("PLAYCOUNT"));
                }
                if(resultSet.getString("DESCRIPTION") != null){
                    trackModel.setDescription(resultSet.getString("DESCRIPTION"));
                }
                if(resultSet.getString("PUBLICATIONDATE") != null){
                    trackModel.setPublicationDate(resultSet.getString("PUBLICATIONDATE"));
                }
                trackModel.setDuration(resultSet.getInt("DURATION"));
                if(resultSet.getBoolean("OFFLINEAVAILABLE")){
                    trackModel.setOfflineAvailable(true);
                }
                else {
                    trackModel.setOfflineAvailable(false);
                }
                trackModels.add(trackModel);
            }
            return trackModels;
        } catch (SQLException e) {
            throw new TrackException(e.getErrorCode());
        }
    }

    public TrackModel leesTrack(String title, String performer) throws TrackException {
        ResultSet resultSet = null;
        PreparedStatement st = null;
        java.sql.Connection cnEmps = connection;
        TrackModel trackModel = new TrackModel();
        String sql = "SELECT * FROM TRACK WHERE TITLE = ? AND PERFORMER = ?";

        try {
            st = cnEmps.prepareStatement(sql);
            st.setString(1, title);
            st.setString(2, performer);

            resultSet = st.executeQuery();
            while (resultSet.next())
            {
                trackModel.setId(resultSet.getInt("TRACK_ID"));
                trackModel.setTitle(resultSet.getString("TITLE"));
                trackModel.setPerformer(resultSet.getString("PERFORMER"));
                trackModel.setDuration(resultSet.getInt("DURATION"));
                trackModel.setAlbum(resultSet.getString("ALBUM"));
                if(resultSet.getInt("PLAYCOUNT") == 0) {
                    trackModel.setPlaycount(resultSet.getInt("PLAYCOUNT"));
                }
                if(resultSet.getString("DESCRIPTION") != null){
                    trackModel.setDescription(resultSet.getString("DESCRIPTION"));
                }
                if(resultSet.getString("PUBLICATIONDATE") != null){
                    trackModel.setPublicationDate(resultSet.getString("PUBLICATIONDATE"));
                }
                trackModel.setDuration(resultSet.getInt("DURATION"));
                if(resultSet.getBoolean("OFFLINEAVAILABLE")){
                    trackModel.setOfflineAvailable(true);
                }
                else {
                    trackModel.setOfflineAvailable(false);
                }
            }
            return trackModel;
        } catch (SQLException e) {
            throw new TrackException(e.getErrorCode());
        }
    }

    public TrackResponseDto addTrackToPlaylist(TrackRequestDto trackRequestDto, int id) throws TrackException{

        ResultSet resultSet = null;
        PreparedStatement st = null;
        java.sql.Connection cnEmps = connection;
        TrackResponseDto trackResponseDto = new TrackResponseDto();
        TrackModel trackModel = leesTrack(trackRequestDto.getTitle(), trackRequestDto.getPerformer());

        String addSql = "UPDATE TRACK SET OFFLINEAVAILABLE = ? WHERE TRACK_ID = " + trackModel.getId();

        try {
            st = cnEmps.prepareStatement(addSql);
            st.setBoolean(1, trackRequestDto.isOfflineAvailable());
            st.execute();
        } catch (SQLException e) {
            throw new TrackException(e.getErrorCode());
        }

        String addSql2 = "INSERT INTO TRACK_IN_PLAYLIST VALUES(?, ?)";

        try {
            st = cnEmps.prepareStatement(addSql2);
            st.setInt(1, trackModel.getId());
            st.setInt(2, id);
            st.execute();
        } catch (SQLException e) {
            throw new TrackException(e.getErrorCode());
        }

        String sql = "SELECT * FROM TRACK T INNER JOIN TRACK_IN_PLAYLIST TIP ON T.TRACK_ID = TIP.TRACK_ID WHERE PLAYLIST_ID = " + id;
        trackResponseDto.setTracks(leesTracks(sql));
        return trackResponseDto;
    }

    public TrackResponseDto deleteTrackVanPlaylist(int playlist_id, int track_id) throws TrackException {
        ResultSet resultSet = null;
        PreparedStatement st = null;
        java.sql.Connection cnEmps = connection;
        TrackResponseDto trackResponseDto = new TrackResponseDto();

        String deleteSql = "DELETE FROM TRACK_IN_PLAYLIST WHERE PLAYLIST_ID = " + playlist_id + " AND TRACK_ID = " + track_id;
        try {
            st = cnEmps.prepareStatement(deleteSql);
            st.execute();
        } catch (SQLException e) {
            throw new TrackException(e.getErrorCode());
        }

        String sql = "SELECT * FROM TRACK T INNER JOIN TRACK_IN_PLAYLIST TIP ON T.TRACK_ID = TIP.TRACK_ID WHERE PLAYLIST_ID = " + playlist_id;

        trackResponseDto.setTracks(leesTracks(sql));
        return trackResponseDto;
    }

}
