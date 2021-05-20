package oose.dea.resources.services;

import oose.dea.resources.dataresources.PlaylistDAO;
import oose.dea.resources.dto.PlaylistRequestDto;
import oose.dea.resources.dto.PlaylistResponseDto;
import oose.dea.resources.exceptions.PlaylistException;

import javax.inject.Inject;
import java.sql.SQLException;

public class PlaylistService {

    @Inject
    public void setLoginDAO(PlaylistDAO playlistDAO){ this.playlistDAO = playlistDAO; }

    private PlaylistDAO playlistDAO;

    public PlaylistResponseDto performGetPlaylists() throws PlaylistException {
        return playlistDAO.getPlaylists();
    }

    public PlaylistResponseDto performDeletePlaylistFromDatabase(int id) throws PlaylistException {
        return playlistDAO.deletePlaylistFromDatabase(id);
    }

    public PlaylistResponseDto performEditPlaylist(PlaylistRequestDto playlistRequestDto, int id) throws PlaylistException {
        return playlistDAO.editPlaylist(playlistRequestDto, id);
    }

    public PlaylistResponseDto performAddPlaylistToDatabase(PlaylistRequestDto playlistRequestDto, String token) throws PlaylistException {
        return playlistDAO.addPlaylistToDatabase(playlistRequestDto, token);
    }

}
