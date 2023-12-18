package com.logicea.cards.controller;

import com.logicea.cards.dto.JWTRequest;
import com.logicea.cards.dto.JWTResponse;
import com.logicea.cards.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	@PostMapping(value = "/authenticate", consumes = { APPLICATION_JSON_VALUE }, produces = { APPLICATION_JSON_VALUE })
	public JWTResponse authenticate(@RequestBody JWTRequest jwtRequest){
		return authenticationService.authenticate(jwtRequest);
	}

}
