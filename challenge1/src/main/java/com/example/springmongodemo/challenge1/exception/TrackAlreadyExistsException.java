package com.example.springmongodemo.challenge1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT,reason = "Track Already Exists")
public class TrackAlreadyExistsException extends Exception{
}
