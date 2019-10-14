package oose.dea.resources.dto;

import oose.dea.resources.models.TrackModel;

import java.util.ArrayList;

public class TrackResponseDto {

    private ArrayList<TrackModel> tracks;

    public ArrayList<TrackModel> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<TrackModel> tracks) {
        this.tracks = tracks;
    }
}
