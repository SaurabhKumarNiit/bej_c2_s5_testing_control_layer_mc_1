package com.challange.Muzix.service;

import com.challange.Muzix.customException.TrackAlreadyExistException;
import com.challange.Muzix.customException.TrackNotFoundException;
import com.challange.Muzix.domain.Track;

import java.util.List;

public interface TrackService {

    Track saveTrackDetail(Track track) throws TrackAlreadyExistException;
    boolean deleteTrack(int id) throws TrackNotFoundException;
    List<Track> getAllTrack() throws Exception;
    List<Track> getAllTracksByArtistName(String artistName) throws TrackNotFoundException;
    List<Track> getAllTrackRatingGreaterThan4() throws TrackNotFoundException;
}
