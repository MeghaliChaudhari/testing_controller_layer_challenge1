package com.example.springmongodemo.challenge1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "Track Is Not Found")
public class TrackNotFoundException extends Exception {
}
