package com.logicea.cards.service.impl;

import com.logicea.cards.dto.JWTRequest;
import com.logicea.cards.dto.JWTResponse;
import com.logicea.cards.security.JWTUtility;
import com.logicea.cards.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
	private final AuthenticationManager authenticationManager;
	private final JWTUtility jwtUtility;
	private final UserDetailsService userDetailsService;

	@Override
	public JWTResponse authenticate(JWTRequest jwtRequest) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						jwtRequest.getEmail(),
						jwtRequest.getPassword()
				)
		);


		final UserDetails userDetails
				= userDetailsService.loadUserByUsername(jwtRequest.getEmail());

		final String token =
				jwtUtility.generateToken(userDetails);

		return new JWTResponse(token);
	}
}
