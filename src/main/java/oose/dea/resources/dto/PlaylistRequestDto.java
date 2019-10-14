package oose.dea.resources.dto;

import oose.dea.resources.models.TrackModel;

import java.util.ArrayList;

public class PlaylistRequestDto {

    private int id;

    private String name;
    private boolean owner;
    private ArrayList<TrackModel> tracks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public ArrayList<TrackModel> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<TrackModel> tracks) {
        this.tracks = tracks;
    }
}
