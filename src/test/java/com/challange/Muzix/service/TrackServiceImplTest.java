package com.challange.Muzix.service;

import com.challange.Muzix.customException.TrackAlreadyExistException;
import com.challange.Muzix.customException.TrackNotFoundException;
import com.challange.Muzix.domain.Artist;
import com.challange.Muzix.domain.Track;
import com.challange.Muzix.repository.TrackRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrackServiceImplTest {

    @Mock
    private TrackRepository trackRepository;

    @InjectMocks
    private TrackServiceImpl trackService;

    private Track track1, track2;
    List<Track> trackList;
    Artist artist1,artist2;

    @BeforeEach
    void setUp() {
        artist1 = new Artist(77,"Justin Bieber");
        track1 = new Track(177,"Let me down",7,artist1);
        artist2 = new Artist(87,"Justin Bieber");
        track2 = new Track(187,"Peaches",9,artist2);
        trackList = Arrays.asList(track1, track2);
    }

    @AfterEach
    void tearDown() {
        track1=null;
        track2 = null;
    }
    @Test
    public void givenCustomerToSaveReturnSavedCustomerSuccess() throws TrackAlreadyExistException {
        when(trackRepository.findById(track1.getTrackId())).thenReturn(Optional.ofNullable(null));
        when(trackRepository.save(any())).thenReturn(track1);
        assertEquals(track1,trackService.saveTrackDetail(track1));
        verify(trackRepository,times(1)).save(any());
        verify(trackRepository,times(1)).findById(any());
    }

    @Test
    public void givenCustomerToSaveReturnCustomerFailure(){
        when(trackRepository.findById(track1.getTrackId())).thenReturn(Optional.ofNullable(track1));
        assertThrows(TrackAlreadyExistException.class,()->trackService.saveTrackDetail(track1));
        verify(trackRepository,times(0)).save(any());
        verify(trackRepository,times(1)).findById(any());
    }

    @Test
    public void givenCustomerToDeleteShouldDeleteSuccess() throws TrackNotFoundException {
        when(trackRepository.findById(track1.getTrackId())).thenReturn(Optional.ofNullable(track1));
        boolean flag = trackService.deleteTrack(track1.getTrackId());
        assertEquals(true,flag);

        verify(trackRepository,times(1)).deleteById(any());
        verify(trackRepository,times(1)).findById(any());
    }



}
