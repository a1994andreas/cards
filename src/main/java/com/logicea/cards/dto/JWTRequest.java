package com.logicea.cards.dto;

import lombok.Data;

@Data
public class JWTRequest {
	private String email;
	private String password;
}
