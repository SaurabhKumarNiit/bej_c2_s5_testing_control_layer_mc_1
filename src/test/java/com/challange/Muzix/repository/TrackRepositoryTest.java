package com.challange.Muzix.repository;

import com.challange.Muzix.domain.Artist;
import com.challange.Muzix.domain.Track;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class TrackRepositoryTest {

    @Autowired
    private TrackRepository trackRepository;
    private Artist artist;
    private Track track;

    @BeforeEach
    public void setUp()
    {
        artist=new Artist(12,"Justin Bieber");
        track =new Track(101,"Peaches",7,artist);
    }

    @Test
    @DisplayName("Test Case For Saving Tracks")
    public void saveTrackInData(){

        trackRepository.save(track);
        Track track1=trackRepository.findById(track.getTrackId()).get();
        assertNotNull(track1);
        assertEquals(track.getTrackId(),track1.getTrackId());

    }

    @Test
    @DisplayName("Test case for deleting customer object")
    public void givenCustomerToDeleteShouldDeleteCustomer() {
        trackRepository.insert(track);
        Track track1 = trackRepository.findById(track.getTrackId()).get();
        trackRepository.delete(track1);
        assertEquals(Optional.empty(), trackRepository.findById(track.getTrackId()));

    }

    @Test
    @DisplayName("Test case for retrieving all the  customer object")
    public void givenCustomerReturnAllCustomerDetails() {

        trackRepository.insert(track);
        Artist artist=new Artist(77,"Justin Bieber");
        Track track1 =new Track(177,"Let me down",7,artist);
        trackRepository.insert(track1);

        List<Track> list = trackRepository.findAll();
        assertEquals(2, list.size());
        assertEquals("Justin Bieber", list.get(1).getTrackArtist().getArtistName());

    }


    @AfterEach
    public void tearDown(){
        artist=null;
        track=null;
        trackRepository.deleteAll();
    }

}
