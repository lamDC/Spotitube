package oose.dea.resources.services;

import oose.dea.resources.dataresources.TrackDAO;
import oose.dea.resources.dto.TrackRequestDto;
import oose.dea.resources.dto.TrackResponseDto;

import javax.inject.Inject;
import java.sql.SQLException;

public class TrackService {

    @Inject
    public void setLoginDAO(TrackDAO trackDAO){ this.trackDAO = trackDAO; }

    private TrackDAO trackDAO;

    public TrackResponseDto performGeefBeschikbareTracks(int id) throws SQLException {
        return trackDAO.geefBeschikbareTracks(id);
    }

    public TrackResponseDto performGetTrackVanPlaylist(int playlist_id) throws SQLException {
        return trackDAO.getTrackVanPlaylist(playlist_id);
    }

    public TrackResponseDto performDeleteTrackVanPlaylist(int playlist_id, int track_id) throws SQLException {
        return trackDAO.deleteTrackVanPlaylist(playlist_id, track_id);
    }

    public TrackResponseDto performAddTrackToPlaylist(TrackRequestDto trackRequestDto, int id) throws SQLException {
        return trackDAO.addTrackToPlaylist(trackRequestDto, id);
    }

    }
