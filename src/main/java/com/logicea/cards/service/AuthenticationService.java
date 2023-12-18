package com.logicea.cards.service;

import com.logicea.cards.dto.JWTRequest;
import com.logicea.cards.dto.JWTResponse;

public interface AuthenticationService {
	JWTResponse authenticate(JWTRequest jwtRequest);

}
