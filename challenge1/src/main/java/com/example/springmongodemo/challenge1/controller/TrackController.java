package com.example.springmongodemo.challenge1.controller;


import com.example.springmongodemo.challenge1.domain.Track;
import com.example.springmongodemo.challenge1.exception.TrackAlreadyExistsException;
import com.example.springmongodemo.challenge1.exception.TrackNotFoundException;
import com.example.springmongodemo.challenge1.service.TrackServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/track/api")
public class TrackController {
    private TrackServiceImpl trackServiceImpl;

    public TrackController(TrackServiceImpl trackServiceImpl) {
        this.trackServiceImpl = trackServiceImpl;
    }

    @PostMapping("/tracks")
    public ResponseEntity<?> insertTrack(@RequestBody Track track) throws TrackAlreadyExistsException {
        ResponseEntity responseEntity = null;
        try {
            trackServiceImpl.saveTrack(track);
            responseEntity = new ResponseEntity<>("Successfully Added",HttpStatus.CREATED);
        }catch (TrackAlreadyExistsException e){
            throw new TrackAlreadyExistsException();
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/alltrack")
    public ResponseEntity<?> fetchAllTrack() throws TrackNotFoundException {
        ResponseEntity responseEntity = null;
        try {
            responseEntity = new ResponseEntity<>(trackServiceImpl.getAllTrack(),HttpStatus.FOUND);
        } catch (Exception e){
            responseEntity = new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @DeleteMapping("/deletetrack/{trackId}")
    public ResponseEntity<?> deleteTrackById(@PathVariable("trackId") int trackId) throws TrackNotFoundException {
        ResponseEntity responseEntity = null;
        try {
            trackServiceImpl.deleteTrackById(trackId);
            responseEntity = new ResponseEntity<>("Succesfully Deleted",HttpStatus.OK);
        } catch (TrackNotFoundException e) {
            throw new TrackNotFoundException();
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/trackget/{trackRating}")
    public ResponseEntity<?> fetchTrackByTrackRating(@PathVariable ("trackRating") int trackRating) throws TrackNotFoundException {
        ResponseEntity responseEntity = null;
        try{
            responseEntity = new ResponseEntity<>(trackServiceImpl.getTrackGreaterThan4(trackRating),HttpStatus.FOUND);
        }catch (TrackNotFoundException e){
            throw new TrackNotFoundException();
        }catch (Exception ex){
            responseEntity = new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/trackget1/{artistName}")
    public ResponseEntity<?> fetchTrackByArtistName(@PathVariable ("artistName") String artistName) throws TrackNotFoundException {
        ResponseEntity responseEntity = null;
        try{
            responseEntity = new ResponseEntity<>(trackServiceImpl.getTrackByArtistName(artistName),HttpStatus.FOUND);
        }catch (TrackNotFoundException e){
            throw new TrackNotFoundException();
        }catch (Exception ex){
            responseEntity = new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
