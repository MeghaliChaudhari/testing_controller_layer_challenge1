package com.example.springmongodemo.challenge1.service;

import com.example.springmongodemo.challenge1.domain.Track;
import com.example.springmongodemo.challenge1.exception.TrackAlreadyExistsException;
import com.example.springmongodemo.challenge1.exception.TrackNotFoundException;

import java.util.List;

public interface TrackService {
    Track saveTrack(Track track) throws TrackAlreadyExistsException;

    List<Track> getAllTrack() throws Exception;

    boolean deleteTrackById(int trackId) throws TrackNotFoundException;

    List<Track> getTrackGreaterThan4(int trackRating) throws TrackNotFoundException;

    List<Track> getTrackByArtistName(String artistName) throws  TrackNotFoundException;
}
