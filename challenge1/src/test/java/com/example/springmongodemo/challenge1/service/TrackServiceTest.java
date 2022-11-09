package com.example.springmongodemo.challenge1.service;

import com.example.springmongodemo.challenge1.domain.Artist;
import com.example.springmongodemo.challenge1.domain.Track;
import com.example.springmongodemo.challenge1.exception.TrackAlreadyExistsException;
import com.example.springmongodemo.challenge1.exception.TrackNotFoundException;
import com.example.springmongodemo.challenge1.repository.TrackRepository;
import com.example.springmongodemo.challenge1.service.TrackServiceImpl;
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
public class TrackServiceTest {

    @Mock
    private TrackRepository trackRepository;

    @InjectMocks
    private TrackServiceImpl trackService;

    private Track track1,track2;

    List<Track> trackList;

    Artist artist1,artist2;

    @BeforeEach
    public void setUp(){
        artist1 = new Artist(80,"Neha kakkar");
        track1 = new Track(21,"track6",5,artist1);
        trackList = Arrays.asList(track1);
    }
    @AfterEach
    public void tearDown(){
        artist1 = null;
        track1 = null;
    }
    @Test
    public void saveTrackTest() throws TrackAlreadyExistsException{
        when(trackRepository.findById(track1.getTrackId())).thenReturn(Optional.ofNullable(null));
        when(trackRepository.save(any())).thenReturn(track1);
        assertEquals(track1,trackService.saveTrack(track1));
        verify(trackRepository,times(1)).save(any());
        verify(trackRepository,times(1)).findById(any());
    }

    @Test
    public void saveTrackFailureTest(){
        when(trackRepository.findById(track1.getTrackId())).thenReturn(Optional.ofNullable(track1));
        assertThrows(TrackAlreadyExistsException.class,()-> trackService.saveTrack(track1));
        verify(trackRepository,times(0)).save(any());
        verify(trackRepository,times(1)).findById(any());
    }

    @Test
    public void deleteTrackTest() throws TrackNotFoundException {
        when(trackRepository.findById(track1.getTrackId())).thenReturn(Optional.ofNullable(track1));
        boolean flag = trackService.deleteTrackById(track1.getTrackId());
        assertEquals(true,flag);
        verify(trackRepository,times(1)).deleteById(any());
        verify(trackRepository,times(1)).findById(any());
    }

    @Test
    public void deleteTrackFailureTest(){
        when(trackRepository.findById(track1.getTrackId())).thenReturn(Optional.ofNullable(track1));
        assertThrows(TrackNotFoundException.class,()-> trackService.deleteTrackById(track1.getTrackId()));

        verify(trackRepository,times(0)).deleteById(any());
        verify(trackRepository,times(1)).findById(any());
    }
}
