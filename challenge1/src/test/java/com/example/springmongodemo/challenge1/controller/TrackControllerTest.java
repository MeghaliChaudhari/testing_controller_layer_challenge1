package com.example.springmongodemo.challenge1.controller;

import com.example.springmongodemo.challenge1.domain.Artist;
import com.example.springmongodemo.challenge1.domain.Track;
import com.example.springmongodemo.challenge1.exception.TrackAlreadyExistsException;
import com.example.springmongodemo.challenge1.service.TrackServiceImpl;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
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

    Artist artist = null;
    Track track = null;

    @BeforeEach
    public void setUp(){
        artist = new Artist(2,"Mukti");
        track = new Track(203,"track12",5,artist);
        mockMvc = MockMvcBuilders.standaloneSetup(trackController).build();
    }

    @AfterEach
    public void tearDown(){
        artist = null;
        track = null;
    }

    private static String jsonToString(final Object ob) throws JsonProcessingException {
        String result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(ob);
            result = jsonContent;
        } catch(JsonProcessingException e) {
            result = "JSON processing error";
        }

        return result;
    }


    @Test
    public void saveCustomerTest() throws Exception {
        when(trackService.saveTrack(any())).thenReturn(track);
        mockMvc.perform(
                post("/track/api/tracks").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(track))).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(trackService,times(1)).saveTrack(any());
    }

    @Test
    public void saveCustomerFailureTest() throws Exception {
        when(trackService.saveTrack(any())).thenThrow(TrackAlreadyExistsException.class);
        mockMvc.perform(
                post("/track/api/tracks").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(track))).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(trackService,times(1)).saveTrack(any());
    }

    @Test
    public void deleteCustomerTest() throws Exception {
        when(trackService.deleteTrackById(anyInt())).thenReturn(true);
        mockMvc.perform(delete("/track/api/deletetrack/203")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(trackService,times(1)).deleteTrackById(anyInt());
    }
}
