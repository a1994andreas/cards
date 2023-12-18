package com.logicea.cards.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CardStatus {
	TO_DO("To Do"),
	IN_PROGRESS("In Progress"),
	DONE("Done");

	private final String description;
	CardStatus(String description){
		this.description = description;
	}

	@JsonValue
	public String getValue() {
		return description;
	}

	@Override
	public String toString() {
		return this.getValue();
	}

}
