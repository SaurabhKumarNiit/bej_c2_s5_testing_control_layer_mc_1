package com.challange.Muzix.service;

import com.challange.Muzix.customException.TrackAlreadyExistException;
import com.challange.Muzix.customException.TrackNotFoundException;
import com.challange.Muzix.domain.Track;
import com.challange.Muzix.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackServiceImpl implements TrackService{

    private TrackRepository trackRepository;

    @Autowired
    public TrackServiceImpl (TrackRepository trackRepository){
        this.trackRepository=trackRepository;
    }

    @Override
    public Track saveTrackDetail(Track track) throws TrackAlreadyExistException {
        if(trackRepository.findById(track.getTrackId()).isPresent()){
            throw new TrackAlreadyExistException();
        }
        return trackRepository.save(track);
    }

    @Override
    public boolean deleteTrack(int id) throws TrackNotFoundException {
        boolean flag = false;
        if(trackRepository.findById(id).isEmpty())
        {
            throw new TrackNotFoundException();
        }
        else {
            trackRepository.deleteById(id);
            flag = true;
        }
        return flag;
    }

    @Override
    public List<Track> getAllTrack() throws Exception {
        return trackRepository.findAll();
    }

    @Override
    public List<Track> getAllTracksByArtistName(String artistName) throws TrackNotFoundException {
        if(trackRepository.findAllTracksFromArtistName(artistName).isEmpty())
        {
            throw new TrackNotFoundException();
        }
        return trackRepository.findAllTracksFromArtistName(artistName);
    }

    @Override
    public List<Track> getAllTrackRatingGreaterThan4() throws TrackNotFoundException {
        if(trackRepository.findAllTracksFromRatingGreaterThan4().isEmpty())
        {
            throw new TrackNotFoundException();
        }
        return trackRepository.findAllTracksFromRatingGreaterThan4();
    }
}
