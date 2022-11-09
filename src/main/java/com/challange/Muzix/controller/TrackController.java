package com.challange.Muzix.controller;

import com.challange.Muzix.customException.TrackAlreadyExistException;
import com.challange.Muzix.customException.TrackNotFoundException;
import com.challange.Muzix.domain.Track;
import com.challange.Muzix.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trackData/v1/")
public class TrackController {

    private  ResponseEntity responseEntity = null;
    private TrackService trackService;

    @Autowired
    public TrackController (TrackService trackService){
        this.trackService=trackService;
    }

    @PostMapping("/track/")
    public ResponseEntity<?> saveTrack(@RequestBody Track track) throws TrackAlreadyExistException {
        try {
            trackService.saveTrackDetail(track);
            responseEntity = new ResponseEntity(track , HttpStatus.CREATED);
        } catch (TrackAlreadyExistException e) {
            throw new TrackAlreadyExistException();
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
    @GetMapping("/tracks")
    public ResponseEntity<?> getAllTrack(){
        try {
            responseEntity= new ResponseEntity<>(trackService.getAllTrack(),HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
        }
        return responseEntity;
    }

    @GetMapping("/track/{artistName}")
    public ResponseEntity<?> getAllTracksByArtistName(@PathVariable String artistName) throws TrackNotFoundException {
        try {
            responseEntity = new ResponseEntity(trackService.getAllTracksByArtistName(artistName), HttpStatus.CREATED);
        } catch (TrackNotFoundException e) {

            throw new TrackNotFoundException();
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @GetMapping("/track/rating4")
    public ResponseEntity<?> getAllTrackRatingGreaterThan4() throws TrackNotFoundException {
        try {
            responseEntity = new ResponseEntity(trackService.getAllTrackRatingGreaterThan4() , HttpStatus.CREATED);
        } catch (TrackNotFoundException e) {

            throw new TrackNotFoundException();
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
    @DeleteMapping("track/{trackId}")
    public ResponseEntity<?> deleteTrack(@PathVariable int trackId) throws TrackNotFoundException {
        try {
            trackService.deleteTrack(trackId);
            responseEntity = new ResponseEntity("Successfully deleted !!!" , HttpStatus.OK);
        } catch (TrackNotFoundException e) {

            throw new TrackNotFoundException();
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

}
