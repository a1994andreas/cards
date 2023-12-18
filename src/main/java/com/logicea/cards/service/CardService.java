package com.logicea.cards.service;

import com.logicea.cards.dto.CardDto;
import com.logicea.cards.dto.CardStatus;
import com.logicea.cards.entity.Card;
import com.logicea.cards.exception.CardNotFoundException;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface CardService {

	Card create(String user, String role, CardDto cardDto);

	Card read(String user, String role, Long id) throws CardNotFoundException;

	Card update(String user, String role, Long id, CardDto cardDto) throws CardNotFoundException;

	void deleteCard(String user, String role, Long id) throws CardNotFoundException;

	Page<Card> searchCards(String user, String role, String name, String color, CardStatus cardStatus,
			LocalDate createDate, int page, int size, String sortBy, String order);

}
