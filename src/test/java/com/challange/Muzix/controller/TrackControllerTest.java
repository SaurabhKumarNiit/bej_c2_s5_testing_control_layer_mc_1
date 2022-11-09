package com.challange.Muzix.controller;

import com.challange.Muzix.customException.TrackAlreadyExistException;
import com.challange.Muzix.domain.Artist;
import com.challange.Muzix.domain.Track;
import com.challange.Muzix.service.TrackServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TrackControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TrackServiceImpl trackService;

    @InjectMocks
    private TrackController trackController;
    private Track track1, track2;
    private Artist artist1, artist2;
    List<Track> customerList;

    @BeforeEach
    void setUp() {
        artist1=new Artist(77,"Justin Bieber");
        track1 =new Track(177,"Let me down",7,artist1);
        artist2=new Artist(12,"Justin Bieber");
        track2 =new Track(101,"Peaches",7,artist2);
        customerList = Arrays.asList(track1, track2);

        mockMvc = MockMvcBuilders.standaloneSetup(trackController).build();
    }

    @AfterEach
    void tearDown() {
        track1 = null;
        track2 = null;
    }

    @Test
    public void givenCustomerToSaveReturnSavedCustomer() throws Exception {
        when(trackService.saveTrackDetail(any())).thenReturn(track1);
        mockMvc.perform(post("/trackData/v1/track/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(track1)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(trackService, times(1)).saveTrackDetail(any());

    }

    @Test
    public void givenCustomerToSaveReturnSavedCustomerFailure() throws Exception {
        when(trackService.saveTrackDetail(any())).thenThrow(TrackAlreadyExistException.class);
        mockMvc.perform(post("/trackData/v1/track/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(track1)))
                .andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(trackService,times(1)).saveTrackDetail(any());

    }
    @Test
    public void givenCustomerIdDeleteCustomer() throws Exception {
        when(trackService.deleteTrack(anyInt())).thenReturn(true);
        mockMvc.perform(delete("/trackData/v1/track/101")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(trackService,times(1)).deleteTrack(anyInt());

    }


    private static String jsonToString(final Object obj) throws JsonProcessingException {
        String result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(obj);
            result = jsonContent;
        } catch(JsonProcessingException e) {
            result = "JSON processing error";
        }

        return result;
    }


}
