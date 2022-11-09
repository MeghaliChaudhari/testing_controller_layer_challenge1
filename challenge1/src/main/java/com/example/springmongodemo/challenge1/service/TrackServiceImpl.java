package com.example.springmongodemo.challenge1.service;

import com.example.springmongodemo.challenge1.domain.Track;
import com.example.springmongodemo.challenge1.exception.TrackAlreadyExistsException;
import com.example.springmongodemo.challenge1.exception.TrackNotFoundException;
import com.example.springmongodemo.challenge1.repository.TrackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackServiceImpl implements TrackService{

    private TrackRepository trackRepository;

    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public Track saveTrack(Track track) throws TrackAlreadyExistsException {
        if (trackRepository.findById(track.getTrackId()).isPresent()){
            throw new TrackAlreadyExistsException();
        }
        return trackRepository.save(track);
    }

    @Override
    public List<Track> getAllTrack() {
        return trackRepository.findAll();
    }

    @Override
    public boolean deleteTrackById(int trackId) throws TrackNotFoundException {

        boolean res = false;
        if (trackRepository.findById(trackId).isPresent()){
            throw new TrackNotFoundException();
        }else {
            trackRepository.deleteById(trackId);
            res = true;
        }
        return res;
    }

    @Override
    public List<Track> getTrackGreaterThan4(int trackRating) throws TrackNotFoundException {
        if (trackRepository.findByTrackRating(trackRating).isEmpty()){
            throw new TrackNotFoundException();
        }
        return trackRepository.findByTrackRating(trackRating);
    }

    @Override
    public List<Track> getTrackByArtistName(String artistName) throws TrackNotFoundException {
        if (trackRepository.findAllTrackByArtistName(artistName).isEmpty()){
            throw new TrackNotFoundException();
        }
        return trackRepository.findAllTrackByArtistName(artistName);
    }
}
