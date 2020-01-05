package oose.dea.resources.services;

import oose.dea.resources.dataresources.PlaylistDAO;
import oose.dea.resources.dto.PlaylistRequestDto;
import oose.dea.resources.dto.PlaylistResponseDto;

import javax.inject.Inject;
import java.sql.SQLException;

public class PlaylistService {
    
    @Inject
    public void setLoginDAO(PlaylistDAO playlistDAO){ this.playlistDAO = playlistDAO; }

    private PlaylistDAO playlistDAO;

    public PlaylistResponseDto performGetPlaylists() throws SQLException {
        return playlistDAO.getPlaylists();
    }

    public PlaylistResponseDto performDeletePlaylistFromDatabase(int id) throws SQLException {
        return playlistDAO.deletePlaylistFromDatabase(id);
    }

    public PlaylistResponseDto performEditPlaylist(PlaylistRequestDto playlistRequestDto, int id) throws SQLException {
        return playlistDAO.editPlaylist(playlistRequestDto, id);
    }

    public PlaylistResponseDto performAddPlaylistToDatabase(PlaylistRequestDto playlistRequestDto, String token) throws SQLException{
        return playlistDAO.addPlaylistToDatabase(playlistRequestDto, token);
    }

    }
