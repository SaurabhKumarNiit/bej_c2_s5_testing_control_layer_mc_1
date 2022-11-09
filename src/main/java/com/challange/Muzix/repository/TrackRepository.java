package com.challange.Muzix.repository;

import com.challange.Muzix.domain.Track;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TrackRepository extends MongoRepository<Track,Integer> {

    @Query("{'trackArtist.artistName' : {$in : [?0]}}")
    List<Track> findAllTracksFromArtistName(String artistName);

    @Query("{'trackRating' : {$gt:4}}")
    List<Track> findAllTracksFromRatingGreaterThan4();


}
