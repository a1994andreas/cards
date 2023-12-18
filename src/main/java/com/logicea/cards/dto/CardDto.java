package com.logicea.cards.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CardDto {
	@NotBlank(message = "Name is mandatory")
	private String name;
	private String description;
	@Pattern(regexp="^$|^#[a-zA-Z0-9]{6}",message="color should start with # and has length of 6 or be empty")
	private String color;
	private CardStatus cardStatus = CardStatus.TO_DO;
}
