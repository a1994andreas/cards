package com.logicea.cards.controller;

import com.logicea.cards.dto.CardDto;
import com.logicea.cards.dto.CardStatus;
import com.logicea.cards.entity.Card;
import com.logicea.cards.security.JWTUtility;
import com.logicea.cards.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController{
	private final CardService cardService;
	private final JWTUtility jwtUtility;

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public Card createCard(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid CardDto cardDto) {
		return cardService.create(userDetails.getUsername(), jwtUtility.getRole(userDetails), cardDto);
	}

	@GetMapping
	@SneakyThrows
	public Card readCard(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long id) {
		return cardService.read(userDetails.getUsername(), jwtUtility.getRole(userDetails), id);
	}

	@PutMapping("{id}")
	@SneakyThrows
	public Card updateCard(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @RequestBody @Valid CardDto cardDto) {
		return cardService.update(userDetails.getUsername(), jwtUtility.getRole(userDetails), id, cardDto);
	}

	@DeleteMapping
	@SneakyThrows
	public void deleteCard(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long id) {
		cardService.deleteCard(userDetails.getUsername(), jwtUtility.getRole(userDetails), id);
	}

	@GetMapping("/search")
	public Page<Card> searchCards(@AuthenticationPrincipal UserDetails userDetails,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String color,
			@RequestParam(required = false) CardStatus cardStatus,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createDate,
	  		@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String order) {
		return cardService.searchCards(userDetails.getUsername(), jwtUtility.getRole(userDetails),
				name, color, cardStatus, createDate, page, size, sortBy, order);
	}
}
