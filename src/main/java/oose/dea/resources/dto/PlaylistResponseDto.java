package oose.dea.resources.dto;

import oose.dea.resources.models.PlaylistModel;

import java.util.ArrayList;

public class PlaylistResponseDto {

    private ArrayList<PlaylistModel> playlists = new ArrayList<PlaylistModel>();
    private int length;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public ArrayList<PlaylistModel> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<PlaylistModel> playlists) {
        this.playlists = playlists;
    }
}
