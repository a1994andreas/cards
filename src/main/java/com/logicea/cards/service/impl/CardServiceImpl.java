package com.logicea.cards.service.impl;

import com.logicea.cards.dto.CardDto;
import com.logicea.cards.dto.CardStatus;
import com.logicea.cards.entity.Card;
import com.logicea.cards.exception.CardNotFoundException;
import com.logicea.cards.repository.CardRepository;
import com.logicea.cards.repository.CardSpecifications;
import com.logicea.cards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CardServiceImpl implements CardService {
	private final CardRepository cardRepository;
	private final ModelMapper modelMapper;

	public Card create(String user, String role, CardDto cardDto) {
		Card card = modelMapper.map(cardDto, Card.class);
		card.setCreatedBy(user);
		return cardRepository.save(card);
	}

	public Card read(String user, String role, Long id) throws CardNotFoundException {
		Optional<Card> card = cardRepository.findById(id);
		if(cardExistsAndBelongsToUser(card, user, role)) {
			return card.get();
		} else {
			throw new CardNotFoundException();
		}
	}

	public Card update(String user, String role, Long id, CardDto cardDto) throws CardNotFoundException {
		Optional<Card> optionalCard = cardRepository.findById(id);
		if(cardExistsAndBelongsToUser(optionalCard, user, role)) {
			Card card = optionalCard.get();
			if (cardDto.getName() != null && !cardDto.getName().isEmpty()) {
				card.setName(cardDto.getName());
			}
			if (cardDto.getDescription() != null) {
				card.setDescription(cardDto.getDescription());
			}
			if (cardDto.getColor() != null) {
				card.setColor(cardDto.getColor());
			}
			if (cardDto.getCardStatus() != null) {
				card.setCardStatus(cardDto.getCardStatus());
			}

			return cardRepository.save(card);
		} else {
			throw new CardNotFoundException();
		}
	}

	public Page<Card> searchCards(String user, String role, String name, String color, CardStatus cardStatus, LocalDate createdDate
			,int page, int size, String sortBy, String order) {
		Specification<Card> spec = Specification.where(null);

		if(!userIsAdmin(role)) {
			spec = spec.and(CardSpecifications.isCreatedBy(user));
		}

		if (name != null && !name.isEmpty()) {
			spec = spec.and(CardSpecifications.nameLike(name));
		}

		if (color != null && !color.isEmpty()) {
			spec = spec.and(CardSpecifications.colorLike(color));
		}

		if (cardStatus != null) {
			spec = spec.and(CardSpecifications.hasStatus(cardStatus));
		}

		if (createdDate != null) {
			spec = spec.and(CardSpecifications.hasCreatedDate(createdDate));
		}

		Sort.Order sortOrder = new Sort.Order(order.equalsIgnoreCase("desc")
				? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);

		return cardRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortOrder)));
	}

	public void deleteCard(String user, String role, Long id) throws CardNotFoundException {
		Optional<Card> card = cardRepository.findById(id);
		if(cardExistsAndBelongsToUser(card, user, role)) {
			cardRepository.deleteById(id);
		} else {
			throw new CardNotFoundException();
		}
	}

	private Boolean cardExistsAndBelongsToUser(Optional<Card> card, String user, String role) {
		return card.isPresent() && (userIsAdmin(role) || card.get().getCreatedBy().equals(user));
	}

	private Boolean userIsAdmin(String role) {
		return role.equals("ROLE_ADMIN");
	}
}
